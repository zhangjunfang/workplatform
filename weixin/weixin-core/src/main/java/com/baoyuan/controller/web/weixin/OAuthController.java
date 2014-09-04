package com.baoyuan.controller.web.weixin;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.weixin.WxAccessToken;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxUser;
import com.baoyuan.service.weixin.WxAccessTokenService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxUserService;
import com.baoyuan.weixin.api.WeixinApi;
import com.baoyuan.weixin.bean.AccessToken;
import com.baoyuan.weixin.bean.User;
import com.baoyuan.weixin.factory.WeixinApiFactory;
import com.baoyuan.weixin.parse.ResultParser;

@Controller(WxGlobal.SIGN + WxGlobal.WEB + "OAuthController")
@RequestMapping(WxGlobal.WEIXIN_WEB_PATH + "/oauth")
public class OAuthController extends BaseWebController {

	@Resource(name = WxGlobal.SIGN + "WxConfigService")
	private WxConfigService wxConfigService;

	@Resource(name = WxGlobal.SIGN + "WxAccessTokenService")
	private WxAccessTokenService wxAccessTokenService;
	
	@Resource(name = WxGlobal.SIGN + "WxUserService")
	private WxUserService wxUserService;
	
	@RequestMapping(value = "/authorization/{wid}", method = { RequestMethod.GET })
	public String authorization(@PathVariable String wid,@RequestParam(value = "code") String code,@RequestParam(value = "state") String state, Model model) {
		
		WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.DATASOURCE_WEIXIN, wid);
		
		WeixinApi weixinApi = WeixinApiFactory.getWeixinApi(wid);
		
		ResultParser<AccessToken> result = weixinApi.getOAuthApi().accessToken(code);
		
		if(result.success()){
			
			AccessToken accessToken = result.getResult();
			
			WxAccessToken wxAccessToken = new WxAccessToken();
			wxAccessToken.setOpenId(accessToken.getOpenId());
			wxAccessToken.setExpiresIn(accessToken.getExpiresIn());
			wxAccessToken.setRefreshToken(accessToken.getRefreshToken());
			wxAccessToken.setScope(accessToken.getScope()==null?"":accessToken.getScope().value());
			wxAccessToken.setToken(accessToken.getToken());
			
			wxAccessToken.setWid(wid);
			wxAccessToken.setTenantId(wxConfig.getTenantId());
			
			wxAccessToken.setCreateUser(DEFAULT_CREATE_USER);
			wxAccessToken.setCreateDate(new Date());
			
			wxAccessTokenService.save(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), wxAccessToken);
			
			String token = accessToken.getToken();
			
			String openId = accessToken.getOpenId();
			logger.error("OAuthController openId ------- " + openId);
			
			//查看已授权的用户
			WxUser wxUserOld = wxUserService.find(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), "openId", openId);
			if(wxUserOld != null ){
				int del = wxUserService.delete(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), "openId", openId);
			}
			//该用户未曾授权，保存至本地
			WxUser wxUser = new WxUser();
			ResultParser<User> snsapiUser = weixinApi.getUserApi().snsapiUserInfo(token, openId);
			if(snsapiUser.success()){
				User user = snsapiUser.getResult();
			
				wxUser.setOpenId(user.getOpenId());
				wxUser.setCity(user.getCity());
				wxUser.setCountry(user.getCountry());
				wxUser.setGender(user.getGender().value());
				wxUser.setHeadImgUrl(user.getHeadImgUrl());
				wxUser.setLanguage(user.getLanguage());
				wxUser.setNickname(user.getNickname());
				wxUser.setPrivilege(user.getPrivilege().toString());
				wxUser.setProvince(user.getProvince());
				
				wxUser.setSubscribe(user.getSubscribe());
				wxUser.setSubscribeTime(user.getSubscribeTime());
				
				wxUser.setWid(wid);
				wxUser.setTenantId(wxConfig.getTenantId());
				
				wxUser.setCreateUser(DEFAULT_CREATE_USER);
				wxUser.setCreateDate(new Date());
				
				wxUserService.save(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), wxUser);
			}
			//当前登录微信用户，放在Session中
			logger.error("OauthController wxUser : "+wxUser);
			if(wxUser.getOpenId().equals(openId)){
				getSession().setAttribute(DEFAULT_CREATE_USER, wxUser);
				model.addAttribute("tencent", wxUser);
			}
			logger.error("OauthController session "+getSession().getAttribute(DEFAULT_CREATE_USER));
		}
		model.addAttribute("code", code);
		
		return "redirect:../../"+state+"/"+wid;
		
	}
}
