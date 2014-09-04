package com.baoyuan.controller.web.weixin;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.service.weixin.WxAccessTokenService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxUserService;

@Controller(WxGlobal.SIGN + WxGlobal.WEB + "AccountController")
@RequestMapping(WxGlobal.WEIXIN_WEB_PATH + "/account")
public class AccountController extends BaseWebController {

	@Resource(name = WxGlobal.SIGN + "WxConfigService")
	private WxConfigService wxConfigService;

	@Resource(name = WxGlobal.SIGN + "WxAccessTokenService")
	private WxAccessTokenService wxAccessTokenService;
	
	@Resource(name = WxGlobal.SIGN + "WxUserService")
	private WxUserService wxUserService;
	
	@RequestMapping(value = "/{wid}/login", method = { RequestMethod.GET })
	public String login(@PathVariable String wid, Model model) {

		return "redirect:";
	}

}
