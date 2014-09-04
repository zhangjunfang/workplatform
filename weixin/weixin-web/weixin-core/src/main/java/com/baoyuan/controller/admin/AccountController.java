package com.baoyuan.controller.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baoyuan.constant.weixin.WxGlobal;

/**
 * 后台登录入口
 */
@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "AccountController")
@RequestMapping(WxGlobal.ADMIN_PATH + "/account")
public class AccountController extends BaseAdminController {

	/**
	 * 用户登录
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(Model model) {
		setLogInfo("用户登录页面");
		return "admin/login";
	}

	/**
	 * 用户退出登录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		setLogInfo("用户退出登录");
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			subject.logout();
		}
		return "redirect:" + WxGlobal.URL_LOGIN;
	};

	/**
	 * 登录校验
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/check", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, String> check(@RequestParam(value = "user") String username,
			@RequestParam(value = "pwd") String password,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> object = null;
		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username,
					password);
			SecurityUtils.getSubject().login(token);

			object = this.ajaxJsonSuccessMessage("");
			object.put("url", WxGlobal.ADMIN_PATH + "/index");

		} catch (AuthenticationException e) {
			logger.error("登录校验时,发生异常 AuthenticationException:" + e.getMessage());

			object = this.ajaxJsonErrorMessage(getMessage("login_verify_error",
					null, request));
			object.put("url", WxGlobal.ADMIN_PATH + "/login");

		} catch (Exception ex) {
			logger.error("登录校验时,发生异常 Exception:" + ex.getMessage());

			object = this.ajaxJsonErrorMessage(getMessage("login_verify_error",
					null, request));
			object.put("url", WxGlobal.ADMIN_PATH + "/login");

		}

		return object;

	}

}
