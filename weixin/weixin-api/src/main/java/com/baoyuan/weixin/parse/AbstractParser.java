package com.baoyuan.weixin.parse;

import org.json.JSONObject;

public abstract class AbstractParser {

	private JSONObject jsonObject;

	protected AbstractParser() {
	}

	protected AbstractParser(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

}
