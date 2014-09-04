package com.baoyuan.controller.web.weixin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.weixin.WxCardBind;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.http.DefaultOpenApi;
import com.baoyuan.service.weixin.WxCardBindService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxUserService;
import com.baoyuan.weixin.utils.DesUtils;

@Controller(WxGlobal.SIGN + WxGlobal.WEB + "CardController")
@RequestMapping(WxGlobal.WEIXIN_WEB_PATH + "/card")
public class CardController extends BaseWebController {

	@Resource(name = WxGlobal.SIGN + "WxConfigService")
	private WxConfigService wxConfigService;

	@Resource(name = WxGlobal.SIGN + "WxCardBindService")
	private WxCardBindService wxCardBindService;

	@Resource(name = WxGlobal.SIGN + "WxUserService")
	private WxUserService wxUserService;

	@Resource
	private DefaultOpenApi defaultOpenApi;

	@RequestMapping(value = "/login/{wid}", method = RequestMethod.GET)
	public String login(@PathVariable String wid,
			@RequestParam("user") String user,
			@RequestParam("card") String card,
			@RequestParam("passwd") String passwd, Model model) {

		WxConfig wxConfig = wxConfigService.getWxConfig(
				WxGlobal.DATASOURCE_WEIXIN, wid);

		WxCardBind bind = wxCardBindService.find(WxGlobal.DATASOURCE_WEIXIN,
				wxConfig.getTenantId(), "fromUserName", user);
		if (bind != null) {
			model.addAttribute("user", user);
			model.addAttribute("wid", wid);
			model.addAttribute("bind", bind);
			return WxGlobal.WEIXIN_WEB_TEMPLATE
					+ "/wsite/default/card/wechat_unbind";
		} else {
			model.addAttribute("user", user);
			model.addAttribute("wid", wid);
			return WxGlobal.WEIXIN_WEB_TEMPLATE
					+ "/wsite/default/card/wechat_bind";
		}
	}

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String success() {
		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_success";
	}

	@RequestMapping(value = "/get", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getNewCardNo(@RequestParam("wid") String wid,
			@RequestParam("user") String user, @RequestParam("bin") String bin, @RequestParam("card") String card,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();

		// 微信帐号不存在
		if (StringUtils.isEmpty(user)) {
			result.put("status", "accountNotExist");
			return result;
		}

		try {
			String token = defaultOpenApi.getToken(bin);
			
			Map<String,Object> res = defaultOpenApi.getNewCardNo(card, bin, token);
			
			result.put("status", "ok");
			
		} catch (Exception ex) {
			logger.error("card bind exception", ex);

			result.put("status", "bindExcetion");
		}

		return result;
	}

	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> bind(@RequestParam("wid") String wid,
			@RequestParam("user") String user,
			@RequestParam("card") String card, @RequestParam("bin") String bin,
			@RequestParam("passwd") String passwd, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();

		// 微信帐号不存在
		if (StringUtils.isEmpty(user)) {
			result.put("status", "accountNotExist");
			return result;
		}

		
		try {
			
			boolean flag = false;
			
			String uniqueCode = null;
			//如果卡号长度不足19位，而且开头不是996611，视为老卡号
			if(card.length()!=19 && !card.startsWith("996611")){
				 uniqueCode = bin;
				 
				 String token = defaultOpenApi.getToken(bin);
				 
				 Map<String,Object> map = defaultOpenApi.getNewCardNo(card, bin, token);
				 
				 if("true".equals(map.get("result").toString())){
					 
					 logger.info(card+"<---------->"+map.get("newCardNo").toString());
					 
					 card = map.get("newCardNo").toString();
					 flag = true;
				 }

				if(!flag){
					result.put("status", "bindExcetion");
						
					return result;
				}
			}

			
			uniqueCode = card.substring(6, 11);

			WxConfig wxConfig = wxConfigService.getWxConfig(
					WxGlobal.DATASOURCE_WEIXIN, wid);

			// 是否已经被其他用户绑定，一张卡绑定一个微信号
			boolean isNotBind = (wxCardBindService.find(
					WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(),
					"cardNo", card) == null);
			if (isNotBind) {
				String token = defaultOpenApi.getToken(uniqueCode);
				boolean isValidCard = defaultOpenApi.validCard(card, passwd,
						token);
				if (isValidCard) {
					WxCardBind bind = new WxCardBind();
					bind.setWid(wid);
					bind.setTenantId(wxConfig.getTenantId());
					bind.setFromUserName(user);
					bind.setCardNo(card);
					bind.setCardPasswd(DesUtils.encrypt(passwd));
					bind.setCreateDate(new Date());
					bind.setCreateUser(DEFAULT_CREATE_USER);

					wxCardBindService.save(WxGlobal.DATASOURCE_WEIXIN,
							wxConfig.getTenantId(), bind);

					result.put("status", "ok");
				} else {
					result.put("status", "accountNotMatch");
				}
			} else {
				result.put("status", "alreadyBind");
			}
		} catch (Exception ex) {
			logger.error("card bind exception", ex);

			result.put("status", "bindExcetion");
		}

		return result;
	}

	@RequestMapping(value = "/unbind", method = RequestMethod.POST)
	public String unbind(@RequestParam("wid") String wid,
			@RequestParam("user") String user, @RequestParam("card") String card) {
		try {
			WxConfig wxConfig = wxConfigService.getWxConfig(
					WxGlobal.DATASOURCE_WEIXIN, wid);

			WxCardBind bind = wxCardBindService.findByFromUserNameAndCardNo(
					WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), user,
					card);
			if (bind != null) {
				wxCardBindService.delete(WxGlobal.DATASOURCE_WEIXIN,
						wxConfig.getTenantId(), bind.getId());
			}

		} catch (Exception ex) {
			logger.error("card unbind exception", ex);
		}
		return "redirect:/web/wx/card/login/" + wid + "?user=" + user
				+ "&card=&passwd=";
	}

	@RequestMapping(value = "/change/{wid}", method = RequestMethod.GET)
	public String change(@PathVariable String wid,
			@RequestParam("user") String user, Model model) {

		WxConfig wxConfig = wxConfigService.getWxConfig(
				WxGlobal.DATASOURCE_WEIXIN, wid);
		WxCardBind bind = wxCardBindService.find(WxGlobal.DATASOURCE_WEIXIN,
				wxConfig.getTenantId(), "fromUserName", user);

		model.addAttribute("user", user);
		model.addAttribute("bind", bind);
		model.addAttribute("wid", wid);

		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_passwd";
	}

	@RequestMapping(value = "/passwd", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> passwd(@RequestParam("wid") String wid,
			@RequestParam("user") String user,
			@RequestParam("card") String card,
			@RequestParam("oldpasswd") String oldpasswd,
			@RequestParam("newpasswd") String newpasswd) {

		Map<String, Object> result = new HashMap<String, Object>();

		// 微信帐号不存在
		if (StringUtils.isEmpty(user)) {
			result.put("status", "accountNotExist");
			return result;
		}

		try {
			String uniqueCode = card.substring(6, 11);
			WxConfig wxConfig = wxConfigService.getWxConfig(
					WxGlobal.DATASOURCE_WEIXIN, wid);
			String token = defaultOpenApi.getToken(uniqueCode);
			boolean isValidCard = defaultOpenApi.validCard(card, oldpasswd,
					token);
			if (isValidCard) {
				if (defaultOpenApi.changePasswd(card, oldpasswd, newpasswd,
						token)) {

					WxCardBind bind = wxCardBindService.find(
							WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(),
							"fromUserName", user);
					bind.setCardPasswd(DesUtils.encrypt(newpasswd));
					wxCardBindService.update(WxGlobal.DATASOURCE_WEIXIN,
							wxConfig.getTenantId(), bind);

					result.put("status", "ok");
				} else {
					result.put("status", "changePasswdFail");
				}
			} else {
				result.put("status", "accountNotMatch");
			}
		} catch (Exception ex) {
			logger.error("change passwd exception", ex);

			result.put("status", "changeExcetion");
		}

		return result;
	}

	@RequestMapping(value = "/change_passwd_success", method = RequestMethod.GET)
	public String change_passwd_success() {

		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_change_passwd_success";
	}

	@RequestMapping(value = "/loss/{wid}", method = RequestMethod.GET)
	public String loss(@PathVariable String wid,
			@RequestParam("user") String user, Model model) {

		WxConfig wxConfig = wxConfigService.getWxConfig(
				WxGlobal.DATASOURCE_WEIXIN, wid);
		WxCardBind bind = wxCardBindService.find(WxGlobal.DATASOURCE_WEIXIN,
				wxConfig.getTenantId(), "fromUserName", user);
		model.addAttribute("user", user);
		model.addAttribute("bind", bind);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE + "/wsite/default/card/wechat_loss";
	}

	@RequestMapping(value = "/lossed", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> lossed(@RequestParam("wid") String wid,
			@RequestParam("user") String user,
			@RequestParam("card") String card,
			@RequestParam("passwd") String passwd) {

		Map<String, Object> result = new HashMap<String, Object>();

		// 微信帐号不存在
		if (StringUtils.isEmpty(user)) {
			result.put("status", "accountNotExist");
			return result;
		}

		try {
			String uniqueCode = card.substring(6, 11);

			String token = defaultOpenApi.getToken(uniqueCode);
			boolean isValidCard = defaultOpenApi.validCard(card, passwd, token);
			if (isValidCard) {
				if (defaultOpenApi.cardLoss(card, passwd, token)) {
					result.put("status", "ok");
				} else {
					result.put("status", "lossFail");
				}
			} else {
				result.put("status", "accountNotMatch");
			}
		} catch (Exception ex) {
			logger.error("card loss exception", ex);

			result.put("status", "lossExcetion");
		}

		return result;
	}

	@RequestMapping(value = "/loss_success", method = RequestMethod.GET)
	public String loss_success() {

		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_loss_success";
	}

	@RequestMapping(value = "/found/{wid}", method = RequestMethod.GET)
	public String found(@PathVariable String wid,
			@RequestParam("user") String user, Model model) {
		WxConfig wxConfig = wxConfigService.getWxConfig(
				WxGlobal.DATASOURCE_WEIXIN, wid);
		WxCardBind bind = wxCardBindService.find(WxGlobal.DATASOURCE_WEIXIN,
				wxConfig.getTenantId(), "fromUserName", user);
		model.addAttribute("user", user);
		model.addAttribute("bind", bind);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_found";
	}

	@RequestMapping(value = "/founded", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> founded(@RequestParam("wid") String wid,
			@RequestParam("user") String user,
			@RequestParam("card") String card,
			@RequestParam("passwd") String passwd) {

		Map<String, Object> result = new HashMap<String, Object>();

		// 微信帐号不存在
		if (StringUtils.isEmpty(user)) {
			result.put("status", "accountNotExist");
			return result;
		}

		try {
			String uniqueCode = card.substring(6, 11);

			String token = defaultOpenApi.getToken(uniqueCode);
			boolean isValidCard = defaultOpenApi
					.validCard1(card, passwd, token);
			if (isValidCard) {
				if (defaultOpenApi.cardFound(card, passwd, token)) {
					result.put("status", "ok");
				} else {
					result.put("status", "foundFail");
				}
			} else {
				result.put("status", "accountNotMatch");
			}
		} catch (Exception ex) {
			logger.error("card found exception", ex);

			result.put("status", "foundExcetion");
		}

		return result;
	}

	@RequestMapping(value = "/found_success", method = RequestMethod.GET)
	public String found_success() {
		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_found_success";

	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public String contact() {
		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_contact";
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public String about() {
		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_about";
	}

	@RequestMapping(value = "/history/{wid}", method = RequestMethod.GET)
	public String history(@PathVariable String wid,
			@RequestParam("user") String user,
			@RequestParam("card") String card,
			@RequestParam("pageSize") String pageSize, Model model) {

		try {
			WxConfig wxConfig = wxConfigService.getWxConfig(
					WxGlobal.DATASOURCE_WEIXIN, wid);
			WxCardBind bind = wxCardBindService.find(
					WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(),
					"fromUserName", user);

			model.addAttribute("bind", bind);
			model.addAttribute("user", user);
			model.addAttribute("wid", wid);

			String uniqueCode = card.substring(6, 11);
			String token = defaultOpenApi.getToken(uniqueCode);
			Map<String, Object> history = defaultOpenApi.consumeMx(card, token,
					pageSize);

			model.addAttribute("history", history.get("dataList"));
		} catch (Exception ex) {
			logger.error("card history exception", ex);
		}
		return WxGlobal.WEIXIN_WEB_TEMPLATE
				+ "/wsite/default/card/wechat_history";
	}

}