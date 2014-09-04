package com.baoyuan.intercepter.weixin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.BaseController;
import com.baoyuan.entity.User;
import com.baoyuan.entity.weixin.Log;
import com.baoyuan.entity.weixin.LogConfig;
import com.baoyuan.service.weixin.LogConfigService;
import com.baoyuan.service.weixin.LogService;

@Component(WxGlobal.SIGN+"LogInterceptor")
public class LogInterceptor extends HandlerInterceptorAdapter {

	@Resource(name=WxGlobal.SIGN+"LogConfigService")
	private LogConfigService logConfigService;

	@Resource(name=WxGlobal.SIGN+"LogService")
	private LogService logService;

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);

		if (handler instanceof BaseController) {
			BaseController controller = (BaseController) handler;
			String controllerClassName = controller.getClass().getName();

			Object current = SecurityUtils.getSubject().getSession()
					.getAttribute(WxGlobal.CURRENT_USER);

			if (current != null) {
				Criteria criteria = new Criteria();
				criteria.add(Restrictions.eq("enabled", 1));
				criteria.add(Restrictions.eq("className", controllerClassName));
				
				User currentUser = (User) current;
				
				List<LogConfig> list = logConfigService.getList(
						WxGlobal.SIGN,
						currentUser.getTenantId(), criteria);

				if (list != null && list.size()>0){
					for(LogConfig logConfig : list){

						Log log = new Log();
						
						log.setTenantId(currentUser.getTenantId());
						
						log.setClassName(logConfig.getClassName());
						log.setMethodName(logConfig.getMethodName());
						log.setOperation(logConfig.getOperation());
						log.setAction(controller.getLogInfo());
						log.setIp(controller.getIpAddress());
						log.setCreateUser(currentUser.getUserName());
						log.setCreateDate(new Date());
						
						logService.save(WxGlobal.SIGN, currentUser.getTenantId(), log);
						
						break;
					}					
				}
			}
		}
	}

}
