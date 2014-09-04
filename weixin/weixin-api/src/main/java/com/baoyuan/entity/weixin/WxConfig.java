package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxConfig")
public class WxConfig extends BaseEntity {

	private static final long serialVersionUID = 6269968005240706994L;

	private String name;

	private Integer type;

	private Integer valid;

	private Integer auth;

	private String token;

	private String url;

	private String appId;

	private String appSecret;

	private String redirectUri;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getAuth() {
		return auth;
	}

	public void setAuth(Integer auth) {
		this.auth = auth;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getRedirectUri() {
		return redirectUri;
	}

	public void setRedirectUri(String redirectUri) {
		this.redirectUri = redirectUri;
	}

}
