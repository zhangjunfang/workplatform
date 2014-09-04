package com.baoyuan.controller.admin;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.baoyuan.bean.LigerTreeMenu;
import com.baoyuan.bean.Menu;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.service.UserService;
import com.baoyuan.weixin.ModuleUtils;

/**
 * 后台主页面
 */
@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "IndexController")
@RequestMapping(WxGlobal.ADMIN_PATH + "/index")
public class IndexController extends BaseAdminController {

	@Resource(name = "sharedUserService")
	private UserService userService;

	@RequestMapping(value = "")
	public String index(Model model) {
		setLogInfo("后台主页面");

		Map<String, LigerTreeMenu> availableTreeMenu = new LinkedHashMap<String, LigerTreeMenu>();

		List<Map<String, Object>> datas = userService.getMenuByUserId(
				WxGlobal.DATASOURCE_SHARED, getCurrentUser().getTenantId(),
				getCurrentUser().getId());

		logger.info(JSON.toJSONString(datas));

		for (Map<String, Object> data : datas) {
			String applicationId = data.get("applicationId").toString();
			String applicationName = data.get("applicationName").toString();

			String id = data.get("id").toString();
			String name = data.get("name").toString();
			Object parentId = data.get("parentId");
			String sign = data.get("sign").toString();

			String url = "#";
			if (data.get("url") != null
					&& !data.get("url").toString().equals("#")) {
				url = data.get("url").toString() + "?mid=" + id;
			}
			ModuleUtils.putSign(id, sign);

			// 创建以应用为根节点
			if (availableTreeMenu.get(applicationId) == null) {
				LigerTreeMenu root = new LigerTreeMenu();
				root.setId(applicationId);
				root.setName(applicationName);

				availableTreeMenu.put(applicationId, root);
			}

			// 应用节点
			Menu menu = new Menu();
			menu.setId(id);
			menu.setName(name);
			menu.setUrl(url);
			menu.setParentId(parentId == null ? "0" : parentId.toString());

			if (parentId == null
					&& availableTreeMenu.get(applicationId).getMenu() == null) {
				availableTreeMenu.get(applicationId).setMenu(
						new ArrayList<Menu>());
			}

			availableTreeMenu.get(applicationId).getMenu().add(menu);
		}

		model.addAttribute("menus", JSON.toJSONString(availableTreeMenu));

		return "admin/index";
	}

	@RequestMapping(value = "welcome")
	public String welcome(HttpServletRequest request,
			HttpServletResponse response) {
		return "admin/welcome";
	}
}
