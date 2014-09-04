package com.baoyuan.controller.admin.weixin;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baoyuan.bean.Pager;
import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.admin.BaseAdminController;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.weixin.api.WeixinApi;
import com.baoyuan.weixin.bean.AccessToken;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxConfigController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/config")
public class WxConfigController extends BaseAdminController {

	@Resource
	private WxConfigService wxConfigService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list() {
		setLogInfo("微信配置列表页面");
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_config_list";
	}

	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(Pager pager) {
		setLogInfo("获取微信配置信息数据页面");

		Criteria criteria = new Criteria();

		if (StringUtils.isNotEmpty(pager.getProperty())
				&& StringUtils.isNotEmpty(pager.getKeyword())) {
			criteria.add(Restrictions.like(pager.getProperty(),
					"%" + pager.getKeyword() + "%"));
		}

		if (StringUtils.isNotEmpty(pager.getOrderBy())
				&& StringUtils.isNotEmpty(pager.getOrderType())) {
			criteria.add(Restrictions.order(pager.getOrderBy(), pager
					.getOrderType().toUpperCase()));
		}

		pager = wxConfigService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}
	
	@RequestMapping(value = "/add")
	public String add(Model model) {
		setLogInfo("添加微信配置页面");
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_config_input";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxConfig wxConfig, HttpServletRequest request) {
		try {
			setLogInfo("新添加微信配置信息");

			wxConfig.setTenantId(getCurrentUser().getTenantId());

			wxConfig.setCreateUser(getCurrentUser().getUserName());
			wxConfig.setCreateDate(new Date());

			//保存
			WxConfig _wxConfig = wxConfigService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), wxConfig);

			//更新Token和URL
			_wxConfig.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
			_wxConfig.setUrl(System.getProperty("weixin.auth.url")+wxConfig.getId()+_wxConfig.getId());
			
			wxConfigService.update(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), _wxConfig);
			
			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存微信配置信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存微信配置时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存微信配置时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑微信配置信息(" + id + ")");
		WxConfig wxConfig = wxConfigService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("wxConfig", wxConfig);
		model.addAttribute("id", id);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_config_input";
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxConfig wxConfig) {
		try {
			setLogInfo("保存修改后的微信配置信息(" + id + ")");
			wxConfig.setModifyUser(getCurrentUser().getUserName());
			wxConfig.setModifyDate(new Date());
			wxConfigService.update(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), wxConfig);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信配置信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信配置时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信配置时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信配置信息(" + ids + ")");
		try {
			wxConfigService.delete(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(),
					Arrays.asList(StringUtils.split(ids, ",")));
			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除微信配置信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信配置信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除微信配置信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除微信配置信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除微信配置信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}
	
	@RequestMapping(value = "/valid/{id}")
	public String validate(@PathVariable String id, Model model) {
		setLogInfo("校验微信配置信息(" + id + ")");
		WxConfig wxConfig = wxConfigService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		
		if(wxConfig.getValid()==0){
			wxConfig.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
			
			wxConfig.setUrl(System.getProperty("weixin.auth.url")+wxConfig.getId());
		}
		
		model.addAttribute("wxConfig", wxConfig);
		model.addAttribute("id", id);
		
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_config_valid";
	}
	
	@RequestMapping(value = "/update/valid/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object updateValid(@PathVariable("id") String id, WxConfig wxConfig) {
		try {
			setLogInfo("更新校验后的微信配置信息(" + id + ")");
			wxConfig.setModifyUser(getCurrentUser().getUserName());
			wxConfig.setModifyDate(new Date());
			
			//校验微信是否正常
			WeixinApi weixinApi = new WeixinApi(wxConfig.getId(), wxConfig.getAppId(), wxConfig.getAppSecret(),wxConfig.getToken(), wxConfig.getRedirectUri());
			AccessToken accessToken = weixinApi.getAccessToken();
			if(accessToken!=null){
				//校验通过
				wxConfig.setValid(1);
				
				wxConfigService.update(WxGlobal.DATASOURCE_WEIXIN,
						getCurrentUser().getTenantId(), wxConfig);
				return this.ajaxJsonSuccessMessage("");
			}else{
				return this.ajaxJsonErrorMessage("微信校验失败");
			}
		} catch (DataSourceDescriptorException ex) {
			logger.error("校验微信配置信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("校验失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("校验微信配置时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("校验失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("校验微信配置时，发生异常", ex);
			return this.ajaxJsonErrorMessage("校验失败,请联系管理员");
		}
	}
}
