package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxAccessToken")
public class WxAccessToken extends BaseEntity {

	private static final long serialVersionUID = 4938999009237706182L;

	/*微信ID标识*/
	private String wid;
	
	/* 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同 */
	private String token;

	/* access_token接口调用凭证超时时间，单位（秒） */
	private Long expiresIn;

	/* 用户刷新access_token */
	private String refreshToken;

	/* 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID */
	private String openId;

	/* 用户授权的作用域，使用逗号（,）分隔 */
	private String scope;

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

}
