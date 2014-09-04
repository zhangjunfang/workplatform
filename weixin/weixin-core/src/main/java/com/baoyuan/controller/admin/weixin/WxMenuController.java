package com.baoyuan.controller.admin.weixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.admin.BaseAdminController;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxMenu;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.exceptions.ValidationException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxMenuService;
import com.baoyuan.weixin.api.WeixinApi;
import com.baoyuan.weixin.bean.Menu;
import com.baoyuan.weixin.bean.MenuType;
import com.baoyuan.weixin.factory.WeixinApiFactory;
import com.baoyuan.weixin.parse.ErrorParser;
import com.baoyuan.weixin.parse.ResultParser;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxMenuController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/menu")
public class WxMenuController extends BaseAdminController {

	@Resource
	private WxConfigService wxConfigService;

	@Resource
	private WxMenuService wxMenuService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
		setLogInfo("微信菜单列表页面");

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

		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_menu_list";
	}

	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String wid) {
		setLogInfo("获取微信菜单信息数据页面");

		List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", wid));
		criteria.add(Restrictions.order("orderNo", "asc"));

		List<WxMenu> lists = wxMenuService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);

		for (WxMenu menu : lists) {

			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", menu.getId());
			data.put("pid", menu.getParentId());
			data.put("name", menu.getName());
			data.put("type", menu.getType());
			data.put("key", menu.getKey());
			data.put("url", menu.getUrl());
			data.put("orderNo", menu.getOrderNo());

			treeList.add(data);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", treeList);

		return result;
	}

	@RequestMapping(value = "/add/{tenantId}/{wid}")
	public String add(@PathVariable String tenantId, @PathVariable String wid,
			Model model) {
		setLogInfo("添加微信菜单页面");

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.isNull("parentId"));
		criteria.add(Restrictions.eq("wid",wid));
		
		List<WxMenu> parentMenus = wxMenuService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		model.addAttribute("parentMenus", parentMenus);

		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);

		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_menu_input";
	}

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("修改微信菜单信息(" + id + ")");

		WxMenu menu = wxMenuService.get(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
				.getTenantId(), id);
		model.addAttribute("menu", menu);
		model.addAttribute("id", id);
		model.addAttribute("tenantId", menu.getTenantId());
		model.addAttribute("wid", menu.getWid());

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.isNull("parentId"));
		criteria.add(Restrictions.eq("wid",menu.getWid()));
		
		List<WxMenu> parentMenus = wxMenuService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);

		model.addAttribute("parentMenus", parentMenus);

		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_menu_input";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxMenu wxMenu) {
		try {
			setLogInfo("新添加模块信息");

			if (StringUtils.isEmpty(wxMenu.getParentId())) {
				wxMenu.setParentId(null);
			}

			wxMenu.setCreateUser(getCurrentUser().getUserName());
			wxMenu.setCreateDate(new Date());

			wxMenuService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),
					wxMenu);

			return this.ajaxJsonSuccessMessage("");
		} catch (DataSourceDescriptorException ex) {
			logger.error("保存菜单信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存菜单信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ValidationException ex) {
			logger.error("保存菜单信息时，发生(ValidationException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存模块信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxMenu wxMenu) {
		try {
			setLogInfo("保存修改后的微信菜单信息(" + id + ")");

			if (StringUtils.isEmpty(wxMenu.getParentId())) {
				wxMenu.setParentId(null);
			}

			wxMenu.setModifyUser(getCurrentUser().getUserName());
			wxMenu.setModifyDate(new Date());

			wxMenuService.update(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),
					wxMenu);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信菜单信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信菜单信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信菜单信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信菜单信息(" + ids + ")");
		try {
			wxMenuService.delete(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), Arrays.asList(StringUtils.split(ids, ",")));

			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除微信菜单信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信菜单信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除微信菜单信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除微信菜单信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除微信菜单信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/refresh", method = { RequestMethod.POST })
	public @ResponseBody
	Object refresh(String wid) {
		setLogInfo("更新微信菜单信息(" + wid + ")");
		try {

			List<Menu> menus = new ArrayList<Menu>();
			Map<String, Menu> availableMenu = new HashMap<String, Menu>();

			Criteria criteria = new Criteria();
			criteria.add(Restrictions.eq("wid", wid));
			criteria.add(Restrictions.order("orderNo", "asc"));

			List<WxMenu> lists = wxMenuService.getList(
					WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),
					criteria);

			for (WxMenu menu : lists) {

				if (availableMenu.get(menu.getParentId()) == null) {

					Menu menuRoot = new Menu();
					menuRoot.setName(menu.getName());
					if ("click".equals(menu.getType())) {
						menuRoot.setType(MenuType.CLICK);
						menuRoot.setKey(menu.getKey());
						menuRoot.setUrl(null);
					} else {
						menuRoot.setType(MenuType.VIEW);
						menuRoot.setKey(null);
						menuRoot.setUrl(menu.getUrl());
					}

					menuRoot.setSubs(new ArrayList<Menu>());

					if (StringUtils.isEmpty(menu.getParentId())) {
						availableMenu.put(menu.getId(), menuRoot);
					} else {
						availableMenu.put(menu.getParentId(), menuRoot);
					}

					menus.add(menuRoot);
				} else {
					Menu menuChild = new Menu();
					menuChild.setName(menu.getName());
					if ("click".equals(menu.getType())) {
						menuChild.setType(MenuType.CLICK);
						menuChild.setKey(menu.getKey());
						menuChild.setUrl(null);
					} else {
						menuChild.setType(MenuType.VIEW);
						menuChild.setKey(null);
						menuChild.setUrl(menu.getUrl());
					}

					availableMenu.get(menu.getParentId()).getSubs()
							.add(menuChild);
				}
			}

			for (String key : availableMenu.keySet()) {
				if (availableMenu.get(key).getSubs().size() > 0) {
					availableMenu.get(key).setKey(null);
					availableMenu.get(key).setType(null);
					availableMenu.get(key).setUrl(null);
				} else {
					availableMenu.get(key).setSubs(null);
				}
			}

			WeixinApi weixinApi = WeixinApiFactory.getWeixinApi(wid);

			ResultParser<ErrorParser> result = null;

			if (menus.size() == 0) {/* 删除菜单 */
				result = weixinApi.getMenuApi().delete();
			} else {/* 更新菜单 */
				result = weixinApi.getMenuApi().create(menus);
			}

			if (result.success()) {
				System.out.println(weixinApi.getOAuthApi().authorize());

				return this.ajaxJsonSuccessMessage("更新成功");
			} else {
				return this.ajaxJsonErrorMessage(result.getError().getError()
						+ "(" + result.getError().getErrorCode() + ")");
			}

		} catch (DataSourceDescriptorException ex) {
			logger.error("更新微信菜单信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("更新失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("更新微信菜单信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("更新失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("更新微信菜单信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("更新失败,请联系管理员");
		}
	}
}
