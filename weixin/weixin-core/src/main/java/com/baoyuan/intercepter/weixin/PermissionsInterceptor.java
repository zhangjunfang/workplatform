package com.baoyuan.intercepter.weixin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.BaseController;
import com.baoyuan.shiro.weixin.ShiroUser;
import com.baoyuan.weixin.ModuleUtils;

/**
 * 权限拦截器
 * 
 * @author 张书祥
 * 
 */
@Component(WxGlobal.SIGN + "PermissionsInterceptor")
public class PermissionsInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(PermissionsInterceptor.class);
	
	public static final ThreadLocal<Map<String, Object>> parametersLocal = new ThreadLocal<Map<String, Object>>();
	
	public PermissionsInterceptor() {
		super();
	}
	
	/**
	 * 处理请求之前被调用 (Before handling the request)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//account/login
		//account/check
		//index
		//index/welcome
		if (request.getRequestURI().contains("account")
				|| request.getRequestURI().contains("index")) {
			//logger.warn("该URI未进行权限拦截:" + request.getRequestURI());
		} else {
			String moduleId = request.getParameter("mid");
			
			if (StringUtils.isEmpty(moduleId)) {
				logger.error("*************   RequestURI:"
						+ request.getRequestURI() + "  moduleId:" + moduleId);
			}
			
			String moduleSign = ModuleUtils.getSign(moduleId);
			
			Map<String, Object> parametersMap = new HashMap<String, Object>();
			parametersMap.put("moduleId", moduleId);
			parametersMap.put("moduleSign", moduleSign);
			parametersLocal.set(parametersMap);
			
			//权限校验
			String functionsSign = getFunctionsSign(request.getRequestURI());
			if (handler instanceof BaseController
					&& StringUtils.isNotEmpty(functionsSign)
					&& StringUtils.isNotEmpty(moduleSign)) {
				String perm = moduleSign + ":" + functionsSign;
				
				if (!SecurityUtils.getSubject().isPermitted(perm)) {
					ShiroUser currentUser = (ShiroUser) SecurityUtils
							.getSubject().getPrincipal();
					logger.error("No 没有权限---perm:" + perm + ",URI:"
							+ request.getRequestURI() + ",currentUser["
							+ currentUser.getUserName() + ","
							+ currentUser.getId() + ","
							+ currentUser.getNickName() + "]");
				}
			}
		}
		
		return super.preHandle(request, response, handler);
	}

	/**
	 * 处理请求之后调用 (After handling the request)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		if (request.getRequestURI().contains("account")
				|| request.getRequestURI().contains("index")) {

		} else {
			if (modelAndView != null) {
				Map<String, Object> parametersMap = parametersLocal.get();
				modelAndView.addObject("mid",
						parametersMap.get("moduleId"));
				modelAndView.addObject("msign",
						parametersMap.get("moduleSign"));
			}
		}
		
		if (modelAndView == null) {
			logger.error("PermissionsInterceptor === modelAndView==null === URI:" + request.getRequestURI());
		}
		
	}
	
	/**
	 * 在显示视图后被调用 (After rendering the view)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (ex != null) {
			logger.error("PermissionsInterceptor afterCompletion Exception:"
					+ ex);
			ex.printStackTrace();
		}
	}
	
	/**
	 * 根据uri，获取数据库中对应的功能标识
	 * 
	 * @param uri
	 * @return
	 */
	private String getFunctionsSign(String uri) {
		String sign = null;
		if (uri.contains("list") || uri.contains("ajax")) {
			sign = "list";
		} else if (uri.contains("add") || uri.contains("save")) {
			sign = "add";
		} else if (uri.contains("edit") || uri.contains("update")) {
			sign = "edit";
		} else if (uri.contains("delete")) {
			sign = "delete";
		}
		
		return sign;
	}
	
}

