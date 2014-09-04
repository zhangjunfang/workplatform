package com.baoyuan.controller.admin.weixin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baoyuan.bean.Pager;
import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.admin.BaseAdminController;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxFollowerGroup;
import com.baoyuan.entity.weixin.WxFollowers;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxFollowerGroupService;
import com.baoyuan.service.weixin.WxFollowersService;
import com.baoyuan.weixin.api.WeixinApi;
import com.baoyuan.weixin.bean.User;
import com.baoyuan.weixin.factory.WeixinApiFactory;
import com.baoyuan.weixin.parse.ErrorParser;
import com.baoyuan.weixin.parse.ResultParser;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxFollowersController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/followers")
public class WxFollowersController extends BaseAdminController {

	@Resource
	private WxConfigService wxConfigService;

	@Resource
	private WxFollowersService wxFollowersService;

	@Resource
	private WxFollowerGroupService wxFollowerGroupService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		setLogInfo("微信关注者分组列表页面");

		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", "1");
		data.put("pid", "0");
		data.put("text", "所有微信");

		tree.add(data);

		List<WxConfig> configs = wxConfigService.getAll(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId());

		String tenantId = "";
		String wid = "";

		for (WxConfig config : configs) {
			data = new HashMap<String, Object>();
			data.put("id", config.getId());
			data.put("pid", "1");
			data.put("text", config.getName());

			if (StringUtils.isEmpty(tenantId)) {
				tenantId = config.getTenantId();
				wid = config.getId();
			}

			tree.add(data);
		}

		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);

		model.addAttribute("tree", JSON.toJSONString(tree));

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid",wid));
		
		List<WxFollowerGroup> groupList = wxFollowerGroupService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		
		Map<String,String> groupMap=new HashMap<String,String>();
		for(WxFollowerGroup group:groupList){
			groupMap.put(group.getGroupId(), group.getName());
		}
		
		model.addAttribute("group", JSON.toJSONString(groupMap));
		
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_followers_list";
	}

	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String wid, Pager pager) {
		setLogInfo("获取微信关注者分组信息数据页面");

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", wid));

		if (StringUtils.isNotEmpty(pager.getProperty())
				&& StringUtils.isNotEmpty(pager.getKeyword())) {
			criteria.add(Restrictions.like(pager.getProperty(),
					"%" + pager.getKeyword() + "%"));
		}

		if (StringUtils.isNotEmpty(pager.getOrderBy())
				&& StringUtils.isNotEmpty(pager.getOrderType())) {
			criteria.add(Restrictions.order(pager.getOrderBy(), pager
					.getOrderType().toUpperCase()));
		}else{
			criteria.add(Restrictions.order("subscribeTime", "desc".toUpperCase()));
		}

		pager = wxFollowersService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("修改关注者信息(" + id + ")");

		WxFollowers follower = wxFollowersService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("follower", follower);
		model.addAttribute("id", id);
		model.addAttribute("tenantId", follower.getTenantId());
		model.addAttribute("wid", follower.getWid());

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", follower.getWid()));
		
		List<WxFollowerGroup> groupList = wxFollowerGroupService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		
		model.addAttribute("groupList", groupList);
		
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_followers_input";
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxFollowers follower) {
		try {
			setLogInfo("保存修改后的关注者信息(" + id + ")");

			WeixinApi weixinApi = WeixinApiFactory.getWeixinApi(follower.getWid());
			
			ResultParser<ErrorParser> groupResult = weixinApi.getGroupApi().move(follower.getOpenId(), follower.getGroupId());
			if (groupResult.success()) {
				follower.setModifyUser(getCurrentUser().getUserName());
				follower.setModifyDate(new Date());

				wxFollowersService.update(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
						.getTenantId(), follower);
				
				return this.ajaxJsonSuccessMessage("");
			} else {
				return this.ajaxJsonErrorMessage(groupResult.getError()
						.getError()
						+ "("
						+ groupResult.getError().getErrorCode() + ")");
			}

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改关注者信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改关注者信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改关注者信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
	}
	
	@RequestMapping(value = "/download", method = { RequestMethod.POST })
	public @ResponseBody
	Object download(String wid) {
		setLogInfo("下载关注者信息");

		WxConfig wxConfig = wxConfigService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), wid);

		// 校验微信是否正常
		WeixinApi weixinApi = new WeixinApi(wxConfig.getId(),
				wxConfig.getAppId(), wxConfig.getAppSecret(),
				wxConfig.getToken(), wxConfig.getRedirectUri());

		ResultParser<List<User>> result = weixinApi.getUserApi()
				.getFollowUsers();

		if (result.success()) {
			List<User> users = result.getResult();
			List<WxFollowers> followers = new ArrayList<WxFollowers>();
			for (User user : users) {
				WxFollowers wxFollowers = new WxFollowers();

				wxFollowers.setOpenId(user.getOpenId());
				wxFollowers.setCity(user.getCity());
				wxFollowers.setCountry(user.getCountry());
				wxFollowers.setGender(user.getGender().value());
				wxFollowers.setHeadImgUrl(user.getHeadImgUrl());
				wxFollowers.setLanguage(user.getLanguage());
				wxFollowers.setNickname(user.getNickname());
				wxFollowers.setPrivilege(user.getPrivilege().toString());
				wxFollowers.setProvince(user.getProvince());
				wxFollowers.setSubscribe(user.getSubscribe());
				wxFollowers.setSubscribeTime(user.getSubscribeTime());

				wxFollowers.setWid(wxConfig.getId());
				wxFollowers.setTenantId(wxConfig.getTenantId());

				wxFollowers.setCreateUser(getCurrentUser().getUserName());
				wxFollowers.setCreateDate(new Date());

				followers.add(wxFollowers);
			}

			wxFollowersService.save(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), wid,followers);

			return this.ajaxJsonSuccessMessage("下载成功");
		} else {
			return this.ajaxJsonErrorMessage(result.getError().getError() + "("
					+ result.getError().getErrorCode() + ")");
		}

	}

}
