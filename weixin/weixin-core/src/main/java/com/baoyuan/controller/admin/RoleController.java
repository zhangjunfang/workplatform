package com.baoyuan.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.baoyuan.bean.Grant;
import com.baoyuan.bean.LigerGridGrantTreeNode;
import com.baoyuan.bean.Pager;
import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.Functions;
import com.baoyuan.entity.Permissions;
import com.baoyuan.entity.Role;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.ApplicationService;
import com.baoyuan.service.FunctionsService;
import com.baoyuan.service.PermissionsService;
import com.baoyuan.service.RoleService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN+"RoleController")
@RequestMapping(WxGlobal.ADMIN_PATH + "/role")
public class RoleController extends BaseAdminController {

	@Resource(name="sharedRoleService")
	private RoleService roleService;

	@Resource(name="sharedApplicationService")
	private ApplicationService applicationService;

	@Resource(name="sharedFunctionsService")
	private FunctionsService functionsService;

	@Resource(name="sharedPermissionsService")
	private PermissionsService permissionsService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(String sign, Model model) {
		setLogInfo("角色列表页面");
		model.addAttribute("sign", sign);
		return "admin/role_list";
	}

	@RequestMapping(value = "/ajax", method = { RequestMethod.POST })
	public @ResponseBody
	Object ajax(Pager pager) {

		setLogInfo("获取角色信息数据页面");

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

		pager = roleService.getPager(WxGlobal.DATASOURCE_SHARED,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}

	@RequestMapping(value = "/add")
	public String add(Model model) {
		setLogInfo("添加角色页面");
		return "admin/role_input";
	}

	@RequestMapping(value = "/grant/{roleId}")
	public String grant(@PathVariable String roleId, Model model) {
		setLogInfo("角色授权页面");
		model.addAttribute("roleId", roleId);
		return "admin/role_grant";
	}

	@RequestMapping(value = "/{roleId}/permissions", method = { RequestMethod.POST })
	public @ResponseBody
	Object permissions(@PathVariable String roleId) {
		List<LigerGridGrantTreeNode> treeList = new ArrayList<LigerGridGrantTreeNode>();

		String tenantId = getCurrentUser().getTenantId();

		// allFunctionsMap
		Map<String, Functions> allFunctionsMap = getAllFunctionsMap(tenantId);

		// allPermissionsMap
		Map<String, String> allPermissionsMap = getAllPermissionsMap(tenantId,
				roleId);

		Map<String, LigerGridGrantTreeNode> availableTreeNode = new HashMap<String, LigerGridGrantTreeNode>();

		// 获取该租户订购的应用模块信息
		List<Map<String, Object>> treeData = applicationService.getTreeData(
				WxGlobal.DATASOURCE_SHARED, tenantId);

		for (Map<String, Object> data : treeData) {
			String applicationId = data.get("applicationId").toString();
			String applicationName = data.get("applicationName").toString();
			String applicationSign = data.get("applicationSign").toString();

			String moduleId = data.get("moduleId").toString();
			String moduleName = data.get("moduleName").toString();
			String moduleSign = data.get("moduleSign").toString();
			String moduleParentId = data.get("moduleParentId")==null?"":data.get("moduleParentId").toString();
			Object functionsId = data.get("functionsId");

			// 创建以应用为根节点
			if (availableTreeNode.get(applicationId) == null) {
				LigerGridGrantTreeNode root = new LigerGridGrantTreeNode();
				root.setId(applicationId);
				root.setName(applicationName);
				root.setSign(applicationSign);

				availableTreeNode.put(applicationId, root);

				treeList.add(root);
			}

			// 应用节点
			LigerGridGrantTreeNode module = new LigerGridGrantTreeNode();
			module.setId(moduleId);
			module.setName(moduleName);
			module.setSign(moduleSign);
			module.setCode(moduleSign + "_" + applicationId + "_"
					+ (moduleParentId == null ? "" : moduleParentId) + "_"
					+ moduleId);

			if (functionsId != null && !"".equals(functionsId)) {
				String[] functionIds = functionsId.toString().split(",");
				List<Grant> grants = new ArrayList<Grant>();
				for (String functionId : functionIds) {

					Functions function = allFunctionsMap.get(functionId);

					Grant grant = new Grant();
					grant.setId(function.getId());
					grant.setName(function.getName());
					grant.setSign(function.getSign());

					String permissions = allPermissionsMap.get(applicationId
							+ "_" + moduleId);

					if (StringUtils.isNotEmpty(permissions)
							&& permissions.contains(function.getId())) {
						grant.setChecked("checked");
					} else {
						grant.setChecked("");
					}

					grants.add(grant);
				}

				if (grants.size() > 0) {
					module.setGrant(grants);
				}
			}

			if (StringUtils.isEmpty(moduleParentId)) {
				if(availableTreeNode.get(applicationId).getChildren()==null){
					availableTreeNode.get(applicationId).setChildren(
							new ArrayList<LigerGridGrantTreeNode>());
				}
				availableTreeNode.get(applicationId).getChildren().add(module);
								
				availableTreeNode.put(applicationId + "_" + moduleId, module);
				availableTreeNode.get(applicationId + "_" + moduleId)
						.setChildren(new ArrayList<LigerGridGrantTreeNode>());
			} else {
				availableTreeNode.get(applicationId + "_" + moduleParentId)
						.getChildren().add(module);
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", treeList);

		return result;
	}

	private Map<String, Functions> getAllFunctionsMap(String tenantId) {
		Map<String, Functions> allFunctionsMap = new HashMap<String, Functions>();
		List<Functions> list = functionsService.getAll(
				WxGlobal.DATASOURCE_SHARED, tenantId);
		for (Functions functions : list) {
			allFunctionsMap.put(functions.getId(), functions);
		}
		return allFunctionsMap;
	}

	private Map<String, String> getAllPermissionsMap(String tenantId,
			String roleId) {
		Map<String, String> allPermissionsMap = new HashMap<String, String>();

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("roleId", roleId));
		List<Permissions> list = permissionsService.getList(
				WxGlobal.DATASOURCE_SHARED, tenantId, criteria);
		for (Permissions permissions : list) {
			allPermissionsMap.put(permissions.getApplicationId() + "_"
					+ permissions.getModuleId(), permissions.getFunctionIds());
		}
		return allPermissionsMap;
	}

	@RequestMapping(value = "/grant/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object saveGrant(String roleId, String[] grants, Model model) {
		try {
			setLogInfo("保存角色权限信息");

			Map<String, Permissions> datas = new HashMap<String, Permissions>();

			for (String str : grants) {
				String[] grant = StringUtils.split(str, "_");

				String applicationId = grant[1];
				String moduleParentId = grant[2];
				String moduleId = grant[3];
				String functionId = grant[4] + ",";
				String functionSign = grant[0] + ":" + grant[5] + ":*" + ",";

				// 父模块
				String parentKey = applicationId + "_" + moduleParentId;
				if (datas.get(parentKey) == null) {
					datas.put(parentKey, new Permissions());

					datas.get(parentKey).setFunctionIds("");
					datas.get(parentKey).setPermissions("");
					datas.get(parentKey).setCreateUser(
							getCurrentUser().getUserName());
					datas.get(parentKey).setCreateDate(new Date());
				}

				datas.get(parentKey).setApplicationId(applicationId);
				datas.get(parentKey).setModuleId(moduleParentId);
				datas.get(parentKey).setRoleId(roleId);

				// 子模块
				String childKey = applicationId + "_" + moduleId;
				if (datas.get(childKey) == null) {
					datas.put(childKey, new Permissions());

					datas.get(childKey).setFunctionIds("");
					datas.get(childKey).setPermissions("");
					datas.get(childKey).setCreateUser(
							getCurrentUser().getUserName());
					datas.get(childKey).setCreateDate(new Date());
				}

				datas.get(childKey).setApplicationId(applicationId);
				datas.get(childKey).setModuleId(moduleId);
				datas.get(childKey).setRoleId(roleId);
				datas.get(childKey).setFunctionIds(
						datas.get(childKey).getFunctionIds() + functionId);
				datas.get(childKey).setPermissions(
						datas.get(childKey).getPermissions() + functionSign);

			}

			List<Permissions> permissions = new ArrayList<Permissions>();
			for (String key : datas.keySet()) {
				permissions.add(datas.get(key));
			}
		
			permissionsService.saveRelationship(WxGlobal.DATASOURCE_SHARED,
					getCurrentUser().getTenantId(), roleId, permissions);

			return this.ajaxJsonSuccessMessage("");
		} catch (DataSourceDescriptorException ex) {
			logger.error("保存角色权限信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存权限失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存角色权限信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存权限失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存角色权限信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存权限失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(Role role) {
		try {
			setLogInfo("保存新添加的角色信息");

			role.setTenantId(getCurrentUser().getTenantId());
			role.setCreateUser(getCurrentUser().getUserName());
			role.setCreateDate(new Date());

			roleService.save(WxGlobal.DATASOURCE_SHARED, getCurrentUser()
					.getTenantId(), role);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存角色信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存角色信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存角色信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("修改角色信息(" + id + ")");
		Role role = roleService.get(WxGlobal.DATASOURCE_SHARED,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("role", role);
		model.addAttribute("id", id);
		return "admin/role_input";
	}

	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, final Role role) {
		try {
			setLogInfo("保存修改后的角色信息(" + id + ")");
			role.setModifyUser(getCurrentUser().getUserName());
			role.setModifyDate(new Date());
			role.setCreateDate(null);// 不修改添加时间和添加人
			role.setCreateUser(null);

			roleService.update(WxGlobal.DATASOURCE_SHARED, getCurrentUser()
					.getTenantId(), role);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改角色信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改角色信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改角色信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
	}

	@RequestMapping(value = "delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids) {
		setLogInfo("删除角色信息(" + ids + ")");
		try {
			roleService.delete(WxGlobal.DATASOURCE_SHARED, getCurrentUser()
					.getTenantId(), Arrays.asList(StringUtils.split(ids, ",")));

			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除角色信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("角色信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除角色信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除角色信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除角色信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}

}
