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
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.exceptions.ValidationException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxFollowerGroupService;
import com.baoyuan.weixin.api.WeixinApi;
import com.baoyuan.weixin.bean.Group;
import com.baoyuan.weixin.factory.WeixinApiFactory;
import com.baoyuan.weixin.parse.ErrorParser;
import com.baoyuan.weixin.parse.ResultParser;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxFollowerGroupController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/followergroup")
public class WxFollowerGroupController extends BaseAdminController {

	@Resource
	private WxConfigService wxConfigService;

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

		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_follower_group_list";
	}

	@RequestMapping(value = "/add/{tenantId}/{wid}")
	public String add(@PathVariable String tenantId, @PathVariable String wid,
			Model model) {
		setLogInfo("添加关注者分组页面");

		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);

		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_follower_group_input";
	}

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("修改关注者分组信息(" + id + ")");

		WxFollowerGroup group = wxFollowerGroupService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("group", group);
		model.addAttribute("id", id);
		model.addAttribute("tenantId", group.getTenantId());
		model.addAttribute("wid", group.getWid());

		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_follower_group_input";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxFollowerGroup group) {
		try {
			setLogInfo("新添加关注者分组信息");

			WeixinApi weixinApi = WeixinApiFactory.getWeixinApi(group.getWid());

			ResultParser<Group> groupResult = weixinApi.getGroupApi().create(
					group.getName());
			if (groupResult.success()) {

				Group g = groupResult.getResult();

				group.setCreateUser(getCurrentUser().getUserName());
				group.setCreateDate(new Date());

				group.setGroupId(g.getId());
				group.setCount(0);

				wxFollowerGroupService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
						.getTenantId(), group);

				return this.ajaxJsonSuccessMessage("");
			} else {
				return this.ajaxJsonErrorMessage(groupResult.getError()
						.getError()
						+ "("
						+ groupResult.getError().getErrorCode() + ")");
			}

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存分组信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存分组信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ValidationException ex) {
			logger.error("保存菜单信息时，发生(ValidationException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存分组信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxFollowerGroup group) {
		try {
			setLogInfo("保存修改后的分组信息(" + id + ")");

			WeixinApi weixinApi = WeixinApiFactory.getWeixinApi(group.getWid());

			ResultParser<ErrorParser> groupResult = weixinApi.getGroupApi()
					.update(group.getGroupId(), group.getName());
			if (groupResult.success()) {

				group.setModifyUser(getCurrentUser().getUserName());
				group.setModifyDate(new Date());

				wxFollowerGroupService.update(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
						.getTenantId(), group);

				return this.ajaxJsonSuccessMessage("");
			} else {
				return this.ajaxJsonErrorMessage(groupResult.getError()
						.getError()
						+ "("
						+ groupResult.getError().getErrorCode() + ")");
			}

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改分组信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改分组信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改分组信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
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
		}

		pager = wxFollowerGroupService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}

	@RequestMapping(value = "/download", method = { RequestMethod.POST })
	public @ResponseBody
	Object download(String wid) {
		setLogInfo("下载分组信息(" + wid + ")");
		try {

			WxConfig wxConfig = wxConfigService.get(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), wid);
			
			// 校验微信是否正常
			WeixinApi weixinApi = new WeixinApi(wxConfig.getId(),
					wxConfig.getAppId(), wxConfig.getAppSecret(),
					wxConfig.getToken(), wxConfig.getRedirectUri());

			ResultParser<List<Group>> result = weixinApi.getGroupApi().get();

			if (result.success()) {
				List<Group> groups = result.getResult();

				List<WxFollowerGroup> followerGroups = new ArrayList<WxFollowerGroup>();

				for (Group group : groups) {
					WxFollowerGroup g = new WxFollowerGroup();

					g.setGroupId(group.getId());
					g.setName(group.getName());
					g.setCount(group.getCount());

					g.setWid(wxConfig.getId());
					g.setTenantId(wxConfig.getTenantId());

					g.setCreateUser(getCurrentUser().getUserName());
					g.setCreateDate(new Date());

					followerGroups.add(g);
				}

				wxFollowerGroupService.save(WxGlobal.DATASOURCE_WEIXIN,
						getCurrentUser().getTenantId(),wid, followerGroups);
				
				return this.ajaxJsonSuccessMessage("下载成功");
			}else{
				return this.ajaxJsonErrorMessage(result.getError().getError()+ "("+ result.getError().getErrorCode() + ")");
			}
		} catch (DataSourceDescriptorException ex) {
			logger.error("更新分组信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("更新失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("更新分组信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("更新失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("更新分组信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("更新失败,请联系管理员");
		}

	}
}
