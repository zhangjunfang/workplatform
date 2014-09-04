package com.baoyuan.weixin.parse;

import org.json.JSONObject;

public final class ErrorParser extends AbstractParser {

	private String request;
	private String errorCode;
	private String error;

	public ErrorParser() {
	}

	private ErrorParser(JSONObject jsonObject) {
		super(jsonObject);
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return errorCode + ":" + error + "(" + request + ")";
	}

	public static ErrorParser parse(JSONObject jsonObject) {

		Integer ret = ResultParser.parseInteger(jsonObject.opt("errcode"));
		if (ret != null && ret != 0) {// 微信
			String error = jsonObject.optString("errmsg");
			ErrorParser er = new ErrorParser(jsonObject);
			er.setErrorCode(ret.toString());
			er.setError(error);
			return er;
		}
		return null;
	}

}
