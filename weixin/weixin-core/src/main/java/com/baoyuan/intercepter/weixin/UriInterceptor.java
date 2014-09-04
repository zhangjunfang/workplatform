package com.baoyuan.intercepter.weixin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baoyuan.controller.BaseController;

@Component
public class UriInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.debug("====== UriInterceptor preHandle ====== IP:"
				+ request.getRemoteAddr());
		logger.debug("====== UriInterceptor preHandle ====== handler:"
				+ handler);
		if(handler instanceof BaseController){
			BaseController controller = (BaseController) handler;
			controller.setRequest(request);
			controller.setResponse(response);
			controller.setSession(request.getSession());
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("====== UriInterceptor postHandle ====== URI:"
				+ request.getRequestURI());
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("====== UriInterceptor afterCompletion ====== ");
		
	}

}
