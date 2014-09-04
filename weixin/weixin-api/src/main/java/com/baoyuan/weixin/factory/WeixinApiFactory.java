package com.baoyuan.weixin.factory;

import java.util.HashMap;
import java.util.Map;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.util.SpringUtils;
import com.baoyuan.weixin.api.WeixinApi;

public class WeixinApiFactory {

	private static Map<String, WeixinApi> weixinApi = new HashMap<String, WeixinApi>();

	private static WxConfigService wxConfigService = null;

	public static WeixinApi getWeixinApi(String wid) {
		if (weixinApi.get(wid) == null) {
			if (wxConfigService == null) {
				wxConfigService = SpringUtils.getApplicationContext().getBean(
						WxConfigService.class);
			}

			WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.SIGN, wid);

			weixinApi.put(wid, new WeixinApi(wid, wxConfig.getAppId(), wxConfig.getAppSecret(),wxConfig.getToken(), wxConfig.getRedirectUri()));
		}
		return weixinApi.get(wid);
	}

}
