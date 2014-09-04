package com.baoyuan.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public abstract class BaseController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	protected MessageSource messageSource;

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	private String logInfo;// 日志记录信息
	private String redirectionUrl;// 操作提示后的跳转URL,为null则返回前一页
	
	@InitBinder
	protected void initBinder(HttpServletRequest request,  
	                              ServletRequestDataBinder binder) throws Exception {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd"); 
	    CustomDateEditor dateEditor = new CustomDateEditor(fmt, true); 
	    binder.registerCustomEditor(Date.class, dateEditor);//对于需要转换为Date类型的属性，使用DateEditor进行处理  
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public String getLogInfo() {
		return logInfo;
	}

	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}

	public String getRedirectionUrl() {
		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}

	/**
	 * i18n操作
	 * 
	 * @param key
	 * @param args
	 * @param request
	 * @return
	 */
	public String getMessage(String key, Object[] args,
			HttpServletRequest request) {
		String message = null;
		try {
			String lang = request.getParameter("locale");
			if (StringUtils.isNotEmpty(lang) && lang.indexOf("_") > 0) {
				java.util.StringTokenizer token = new java.util.StringTokenizer(
						lang, "_");
				String _lang = token.nextToken();
				String _country = token.nextToken();
				java.util.Locale local = new java.util.Locale(_lang, _country);
				message = messageSource.getMessage(key, args, local);
			} else {
				message = messageSource.getMessage(key, args, Locale.CHINA);
			}
		} catch (Exception ex) {
			message = messageSource.getMessage(key, args, Locale.CHINA);
		}
		return message;
	}

	// 获取Parameter
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// 获取Parameter数组
	public String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}
	
	// AJAX输出，返回null
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = this.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			logger.error("BaseController ajax(String content, String type)方法，发生异常:" + e);
			e.printStackTrace();
		}
		return null;
	}
	
	public String getIpAddress() {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 *获取当前路径 的方法
	 */
	public ServletContext getServletContext() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  
		 ServletContext servletContext=request.getSession().getServletContext();
		return servletContext;
	}
	
	public String urlDecoder(String encodeStr){
		String targetStr = encodeStr;
		try {
			if (StringUtils.isNotEmpty(targetStr)) {
				targetStr = URLDecoder.decode(encodeStr, "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return targetStr;
	}
}
