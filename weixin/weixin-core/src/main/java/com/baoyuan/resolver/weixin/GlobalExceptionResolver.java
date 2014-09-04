package com.baoyuan.resolver.weixin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.baoyuan.controller.BaseController;
import com.baoyuan.exceptions.ValidationException;

/**
 * 全局异常处理类
 * 
 * @author 张书祥
 */
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		logger.error("GlobalExceptionResolver request" + request);
		logger.error("GlobalExceptionResolver response" + response);
		logger.error("GlobalExceptionResolver handler" + handler);
		logger.error("GlobalExceptionResolver", ex);

		String logInfo = null;
		if (handler instanceof BaseController) {
			BaseController controller = (BaseController) handler;
			logInfo = controller.getLogInfo();
			logger.error("GlobalExceptionResolver logInfo:" + logInfo);
		}
		
		StringBuffer exceptionInfo = new StringBuffer();
		if (ex instanceof ValidationException) {
			ValidationException validEx = (ValidationException) ex;
			String validMsg = validEx.getMessage();
			logger.error("GlobalExceptionResolver ValidationException validMsg:" + validMsg);
			exceptionInfo.append(validMsg+":");
			exceptionInfo.append("[");
			Map<String,String> validMap = validEx.getResult();
			for(String key : validMap.keySet()){
				String value = validMap.get(key);
				logger.error("GlobalExceptionResolver ValidationException key:" + key+",value:"+value);
				exceptionInfo.append("key:" + key+",value:"+value);
			}
			exceptionInfo.append("]");
		}
		
		ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
		modelAndView.addObject("errorMessage", logInfo + "时，发生异常！");
		modelAndView.addObject("exceptionMessage", exceptionInfo);
		return modelAndView;
	}

}
