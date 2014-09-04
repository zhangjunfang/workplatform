package com.baoyuan.controller.admin;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
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
import com.baoyuan.entity.Role;
import com.baoyuan.entity.User;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.RoleService;
import com.baoyuan.service.UserService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "UserController")
@RequestMapping(WxGlobal.ADMIN_PATH + "/user")
public class UserController extends BaseAdminController {

	@Resource(name = "sharedUserService")
	private UserService userService;

	@Resource(name = "sharedRoleService")
	private RoleService roleService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list() {
		setLogInfo("用户列表页面");
		return "admin/user_list";
	}

	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(Pager pager) {
		setLogInfo("用户信息数据页面");

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

		pager = userService.getPager(WxGlobal.DATASOURCE_SHARED,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}

	@RequestMapping(value = "/add")
	public String add(Model model) {
		setLogInfo("添加用户页面");
		return "admin/user_input";
	}

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("修改用户信息(" + id + ")");
		User user = userService.get(WxGlobal.DATASOURCE_SHARED,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("user", user);
		model.addAttribute("id", id);
		return "admin/user_input";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(final User user) {
		setLogInfo("保存新添加的用户信息");
		user.setTenantId(getCurrentUser().getTenantId());
		if(user.getPasswd() != null && !"".equals(user.getPasswd())){
			user.setPasswd(new Md5Hash(user.getPasswd()).toHex());
		}else{
			user.setPasswd(new Md5Hash("123456").toHex());// 初始密码
		}
		user.setCreateUser(getCurrentUser().getUserName());
		user.setCreateDate(new Date());

		userService.save(WxGlobal.DATASOURCE_SHARED, getCurrentUser()
				.getTenantId(), user);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put(STATUS, SUCCESS);
		result.put(MESSAGE, "");
		return result;
	}

	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, final User user) {
		setLogInfo("保存修改后的用户信息(" + id + ")");
		user.setModifyUser(getCurrentUser().getUserName());
		user.setModifyDate(new Date());
		if(user.getPasswd()==null || "".equals(user.getPasswd())){
			user.setPasswd(null);
		}else{
			user.setPasswd(new Md5Hash(user.getPasswd()).toHex());
		}
		userService.update(WxGlobal.DATASOURCE_SHARED, getCurrentUser()
				.getTenantId(), user);

		return this.ajaxJsonSuccessMessage("");
	}

	@RequestMapping(value = "delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(final String ids) {
		try {
			setLogInfo("删除用户信息(" + ids + ")");

			userService.delete(WxGlobal.DATASOURCE_SHARED, getCurrentUser()
					.getTenantId(), Arrays.asList(StringUtils.split(ids, ",")));

			return this.ajaxJsonSuccessMessage("");

		} catch (ForeignKeyException ex) {
			logger.error("删除用户信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("用户信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除用户信息时，发生(DataSourceDescriptorException)异常", ex);

			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除用户信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除用户信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}

	@RequestMapping(value = "{userId}/roles", method = { RequestMethod.GET })
	public String setRoles(@PathVariable String userId, Model model) {
		setLogInfo("用户设置角色页面");
		String tenantId = getCurrentUser().getTenantId();
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("tenantId", tenantId));
		criteria.add(Restrictions.order("createDate", "asc"));
		List<Role> allRoles = roleService.getList(WxGlobal.DATASOURCE_SHARED,
				tenantId, criteria);
		model.addAttribute("userId", userId);
		model.addAttribute("user",
				userService.get(WxGlobal.DATASOURCE_SHARED, tenantId, userId));
		model.addAttribute("allRoles", allRoles);
		model.addAttribute("userRoleIds", roleService.getRoleIdByUserId(
				WxGlobal.DATASOURCE_SHARED, tenantId, userId));
		return "admin/user_role";
	}

	@RequestMapping(value = "{userId}/roles/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object saveRoles(@PathVariable final String userId, final String[] roleId,
			Model model) {
		try {
			String operation = "保存用户(" + userId + ")角色信息";
			setLogInfo(operation);

			userService.saveRelationship(
					WxGlobal.DATASOURCE_SHARED,
					getCurrentUser().getTenantId(),
					userId,
					(roleId == null || roleId.length == 0) ? null : Arrays
							.asList(roleId));

			return this.ajaxJsonSuccessMessage("");
		} catch (DataSourceDescriptorException ex) {
			logger.error("保存用户角色信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存权限失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存用户角色信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存权限失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存用户角色信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存用户角色信息失败,请联系管理员");
		}

	}
}
