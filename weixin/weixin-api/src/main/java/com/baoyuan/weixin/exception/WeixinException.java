package com.baoyuan.weixin.exception;

public class WeixinException extends RuntimeException {

	private static final long serialVersionUID = 4176244327852451605L;

	public WeixinException(Exception exception) {
		super(exception);
	}

	public WeixinException(String message) {
		super(message);
	}

}
