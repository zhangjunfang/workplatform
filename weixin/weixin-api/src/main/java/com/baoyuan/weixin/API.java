package com.baoyuan.weixin;

import com.baoyuan.weixin.api.WeixinApi;

public abstract class API {

	protected WeixinApi weixin;

	protected API(WeixinApi weixin) {
		this.weixin = weixin;
	}

}
