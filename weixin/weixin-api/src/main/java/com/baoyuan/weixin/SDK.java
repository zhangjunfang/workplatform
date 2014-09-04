package com.baoyuan.weixin;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.baoyuan.weixin.exception.HttpException;
import com.baoyuan.weixin.exception.WeixinException;
import com.baoyuan.weixin.http.HttpUtils;
import com.baoyuan.weixin.parse.ErrorParser;
import com.baoyuan.weixin.parse.ResultParser;

public abstract class SDK {

	public String get(String url, List<NameValuePair> params) {
		try {
			return HttpUtils.get(url, params);
		} catch (HttpException e) {
			throw new WeixinException(e);
		}
	}

	public String get(String url) {
		return get(url, null);
	}

	public String post(String url, HttpEntity postBody) {
		try {
			return HttpUtils.post(url, postBody);
		} catch (HttpException e) {
			throw new WeixinException(e);
		}
	}

	public String post(String url, List<NameValuePair> params) {
		try {
			return HttpUtils.post(url, params, "UTF-8");
		} catch (HttpException e) {
			throw new WeixinException(e);
		}
	}

	public String post(String url) {
		return post(url, (HttpEntity) null);
	}

	public void addParameter(List<NameValuePair> params, String name,
			Object value) {
		if (value == null) {
			throw new WeixinException("Parameter " + name
					+ " must not be null.");
		}
		params.add(new BasicNameValuePair(name, value.toString()));
	}

	public void addNotNullParameter(List<NameValuePair> params, String name,
			Object value) {
		if (value != null) {
			params.add(new BasicNameValuePair(name, value.toString()));
		}
	}

	public void addTrueParameter(List<NameValuePair> params, String name,
			Boolean value) {
		if (Boolean.TRUE.equals(value)) {
			params.add(new BasicNameValuePair(name, value.toString()));
		}
	}

	/**
	   * 经纬度转换为地址
	   * 
	   * @param lon 经度
	   * @param lat 纬度
	   */
	  public ResultParser<String> lonLatToAddress(Double lon, Double lat) {
	    List<NameValuePair> params = new ArrayList<NameValuePair>();

	    addParameter(params, "ak", "B147f63a0e54e23031de27c637b02279");
	    addParameter(params, "output", "json");
	    addParameter(params, "location", lat + "," + lon);

	    String json = get("http://api.map.baidu.com/geocoder/v2/", params);

	    JSONObject jsonObject = new JSONObject(json);
	    if (!("0".equals(jsonObject.optString("status")))) {
	      ErrorParser error = new ErrorParser();
	      error.setErrorCode(jsonObject.getString("status"));
	      error.setError(jsonObject.getString("msg"));

	      return new ResultParser<String>(error);
	    }

	    JSONObject results = new JSONObject(jsonObject.optString("result"));

	    return new ResultParser<String>(results.getString("formatted_address"));
	  }
}
