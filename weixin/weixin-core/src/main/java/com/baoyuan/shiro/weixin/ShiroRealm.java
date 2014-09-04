package com.baoyuan.shiro.weixin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.Permissions;
import com.baoyuan.entity.Role;
import com.baoyuan.entity.User;
import com.baoyuan.service.PermissionsService;
import com.baoyuan.service.RoleService;
import com.baoyuan.service.UserService;
import com.google.common.collect.Maps;

@Component
public class ShiroRealm extends AuthorizingRealm {

	private static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);
	
	@Resource(name="sharedUserService")
	private UserService userService;
	@Resource(name="sharedRoleService")
	private RoleService roleService;
	@Resource(name="sharedPermissionsService")
	private PermissionsService permissionsService;
	
	public ShiroRealm() {
		setName(WxGlobal.SIGN + "ShiroRealm");
		// 设置认证token的实现类
		setAuthenticationTokenClass(UsernamePasswordToken.class);
		// 设置加密算法
		setCredentialsMatcher(new HashedCredentialsMatcher(Md5Hash.ALGORITHM_NAME));
	}

	
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		logger.info("授权检查.....");

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		User currentUser = (User) SecurityUtils.getSubject().getSession().getAttribute(WxGlobal.CURRENT_USER);
		String tenantId = currentUser.getTenantId();
		List<Role> roleList = roleService.getRolesByUserId(WxGlobal.DATASOURCE_SHARED,
				tenantId, ((ShiroUser) SecurityUtils.getSubject()
						.getPrincipal()).getId());
		for (int i = 0; i < roleList.size(); i++) {
			info.addRole(roleList.get(i).getName());
			Criteria criteria = new Criteria();
			criteria.add(Restrictions.eq("roleId", roleList.get(i).getId()));
			List<Permissions> permissionsList = permissionsService.getList(
					WxGlobal.DATASOURCE_SHARED, tenantId, criteria);
			for (int j = 0; j < permissionsList.size(); j++) {
				if (StringUtils.isNotEmpty(permissionsList.get(j)
						.getPermissions())) {
					info.addStringPermissions(Arrays.asList(permissionsList
							.get(j).getPermissions().split(",")));
				}
			}
		}		
		return info;
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		if (StringUtils.isNotEmpty(token.getUsername())) {
			User user = userService.getUserByName(WxGlobal.DATASOURCE_SHARED, token.getUsername());
			if (user != null) {
				boolean current_user_is_supper = false;
				if (user.getUserName().equals("administrator")) {
					current_user_is_supper = true;
				}
				
				SecurityUtils.getSubject().getSession()
						.setAttribute(WxGlobal.CURRENT_USER, user);
				
				SecurityUtils
						.getSubject()
						.getSession()
						.setAttribute(WxGlobal.CURRENT_USER_IS_SUPPER,
								current_user_is_supper);
				
				initOperationSign();
				
				return new SimpleAuthenticationInfo(new ShiroUser(user.getId(),
						user.getUserName(), user.getNickName(),
						user.getRealName()), user.getPasswd(), getName());
			} else {
				return null;
			}
		}
		return null;
	}

	/**
	 * Session中放入Shiro权限字符串中间部分的操作标识
	 */
	private void initOperationSign() {
		org.apache.shiro.session.Session session = SecurityUtils.getSubject()
				.getSession();
		Map<String, String> operationSignMap = Maps.newHashMap();
		operationSignMap.put("list", "list");
		operationSignMap.put("add", "add");
		operationSignMap.put("edit", "edit");
		operationSignMap.put("delete", "delete");
		operationSignMap.put("role", "role");
		operationSignMap.put("permission", "permission");
		operationSignMap.put("subscribe", "subscribe");
		operationSignMap.put("validate", "validate");
		operationSignMap.put("refresh", "refresh");
		
		operationSignMap.put("download", "download");
		operationSignMap.put("upload", "upload");
		operationSignMap.put("sync", "sync");
		
		session.setAttribute("oper", operationSignMap);
	}
	
	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}
	
	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
}
