package com.baoyuan.constant.weixin;

import java.io.Serializable;

public class WxGlobal implements Serializable {

	private static final long serialVersionUID = 7965948805675371332L;

	public static final String ADMIN = "admin";

	public static final String WEB = "web";
	
	public static final String OPEN="open";
	
	public static final String SIGN = "weixin";
	
	public static final String NAME = "微信公众平台管理系统";
	
	public static final String URL_LOGIN = "/admin/account/login";

	public static final String URL_LOGOUT = "/admin/account/logout";

	/**
	 * 设置管理端基础路径
	 */
	public static final String ADMIN_PATH = "/" + ADMIN;
	
	/**
	 * 设置网站前端基础路径
	 */
	public static final String WEB_PATH = "/" + WEB;
	
	/**
	 * 开放前端基础路径
	 */
	public static final String OPEN_PATH = "/" + OPEN;
	
	
	/**
	 * 微信后端管理访问路径
	 */
	public static final String WEIXIN_ADMIN_PATH = ADMIN_PATH+"/wx";
	
	/**
	 * 微信后端模版访问路径
	 */
	public static final String WEIXIN_ADMIN_TEMPLATE =  ADMIN + "/wx";
	
	/**
	 * Shared数据源标识
	 */
	public static final String DATASOURCE_SHARED = "ucenter";
	
	/**
	 * 当前用户的Session key
	 */
	public static final String CURRENT_USER = "CURRENT_USER";
	
	/**
	 * 当前用户是否超级管理员的Session key
	 */
	public static final String CURRENT_USER_IS_SUPPER = "CURRENT_USER_IS_SUPPER";

	/**
	 * 微信数据源标识
	 */
	public static final String DATASOURCE_WEIXIN = "weixin";
	
	/**
	 * 微信开放路径
	 */
	public static final String WEIXIN_OPEN_PATH = OPEN_PATH+"/wx";
	
	/**
	 * 微信Web访问路径
	 */
	public static final String WEIXIN_WEB_PATH = WEB_PATH+"/wx";

	/**
	 * 微信Web模版访问路径
	 */
	public static final String WEIXIN_WEB_TEMPLATE =  WEB + "/wx";
	
	/**
	 * 微信会员卡数据源标识
	 */
	public static final String DATASOURCE_WEIXIN_CARD = "wxhyk";
}
