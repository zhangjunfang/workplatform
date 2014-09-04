package com.baoyuan.weixin.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import com.baoyuan.weixin.API;
import com.baoyuan.weixin.bean.Menu;
import com.baoyuan.weixin.bean.MenuType;
import com.baoyuan.weixin.exception.WeixinException;
import com.baoyuan.weixin.parse.ErrorParser;
import com.baoyuan.weixin.parse.ResultParser;

/**
 * 自定义菜单接口
 * 
 * 文档地址：http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
 */
public final class MenuApi extends API {

	protected MenuApi(WeixinApi weixin) {
		super(weixin);
	}

	/**
	 * 自定义菜单创建接口
	 * 
	 * @param menus
	 *            菜单
	 */
	public ResultParser<ErrorParser> create(List<Menu> menus) {
		return create(weixin.getAccessToken().getToken(), menus);
	}

	/**
	 * 自定义菜单创建接口
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 * @param menus
	 *            菜单
	 */
	public ResultParser<ErrorParser> create(String accessToken, List<Menu> menus) {
		JSONArray menuArray = new JSONArray();
		for (Menu menu : menus) {
			JSONObject obj = new JSONObject();
			MenuType type = menu.getType();
			obj.put("name", menu.getName());
			if (type != null) {
				obj.put("type", type.value());
				if (type == MenuType.CLICK) {
					obj.put("key", menu.getKey());
				}
				if (type == MenuType.VIEW) {
					obj.put("url", menu.getUrl());
				}
			}
			List<Menu> subs = menu.getSubs();
			if (subs != null) {
				JSONArray _menuArray = new JSONArray();
				for (Menu _menu : subs) {
					JSONObject _obj = new JSONObject();
					MenuType _type = _menu.getType();
					_obj.put("name", _menu.getName());
					if (_type != null) {
						_obj.put("type", _type.value());
						if (_type == MenuType.CLICK) {
							_obj.put("key", _menu.getKey());
						}
						if (_type == MenuType.VIEW) {
							_obj.put("url", _menu.getUrl());
						}
					}
					_menuArray.put(_obj);
				}
				obj.put("sub_button", _menuArray);
			}
			menuArray.put(obj);
		}
		JSONObject request = new JSONObject();
		request.put("button", menuArray);
		
		try {
			String json = weixin.post(
					"https://api.weixin.qq.com/cgi-bin/menu/create?access_token="
							+ accessToken, new StringEntity(request.toString(),
							"UTF-8"));
			return ResultParser.parse(json, ErrorParser.class);
		} catch (UnsupportedEncodingException e) {
			throw new WeixinException(e);
		}
	}

	/**
	 * 自定义菜单查询接口
	 */
	public ResultParser<List<Menu>> get() {
		return get(weixin.getAccessToken().getToken());
	}

	/**
	 * 自定义菜单查询接口
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 */
	public ResultParser<List<Menu>> get(String accessToken) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		weixin.addParameter(params, "access_token", accessToken);
		String json = weixin.get("https://api.weixin.qq.com/cgi-bin/menu/get",
				params);
		JSONObject jsonObject = new JSONObject(json);
		ErrorParser error = ErrorParser.parse(jsonObject);
		if (error != null) {
			return new ResultParser<List<Menu>>(error);
		}
		List<Menu> menus = new ArrayList<Menu>();
		JSONObject menu = jsonObject.optJSONObject("menu");
		if (menu != null) {
			menus = ResultParser.parse(menu.optJSONArray("button"), Menu.class);
		}
		return new ResultParser<List<Menu>>(menus);
	}

	/**
	 * 自定义菜单删除接口
	 */
	public ResultParser<ErrorParser> delete() {
		return delete(weixin.getAccessToken().getToken());
	}

	/**
	 * 自定义菜单删除接口
	 * 
	 * @param accessToken
	 *            调用接口凭证
	 */
	public ResultParser<ErrorParser> delete(String accessToken) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		weixin.addParameter(params, "access_token", accessToken);
		String json = weixin.get(
				"https://api.weixin.qq.com/cgi-bin/menu/delete", params);
		return ResultParser.parse(json, ErrorParser.class);
	}
}
