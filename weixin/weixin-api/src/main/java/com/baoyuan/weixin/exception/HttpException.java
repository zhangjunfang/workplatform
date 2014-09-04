package com.baoyuan.weixin.exception;

public class HttpException extends Exception {

	private static final long serialVersionUID = 7352859697301441936L;

	public HttpException(Exception exception) {
		super(exception);
	}

	public HttpException(String message) {
		super(message);
	}

}
