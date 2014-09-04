package com.baoyuan.weixin.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;

import com.baoyuan.weixin.API;
import com.baoyuan.weixin.bean.Group;
import com.baoyuan.weixin.exception.WeixinException;
import com.baoyuan.weixin.parse.ErrorParser;
import com.baoyuan.weixin.parse.ResultParser;

/**
 * 分组管理接口
 * 
 * 文档地址：http://mp.weixin.qq.com/wiki/index.php?title=分组管理接口
 */
public final class GroupApi extends API{

	protected GroupApi(WeixinApi weixin) {
		super(weixin);
	}

	/**
	   * 查询分组
	   */
	  public ResultParser<List<Group>> get() {
	    return get(weixin.getAccessToken().getToken());
	  }

	  /**
	   * 查询分组
	   * 
	   * @param accessToken 调用接口凭证
	   */
	  public ResultParser<List<Group>> get(String accessToken) {
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    weixin.addParameter(params, "access_token", accessToken);
	    String json = weixin.get("https://api.weixin.qq.com/cgi-bin/groups/get", params);
	    JSONObject jsonObject = new JSONObject(json);
	    ErrorParser error = ErrorParser.parse(jsonObject);
	    if (error == null) {
	      List<Group> groups =
	          ResultParser.parse(jsonObject.getJSONArray("groups"), Group.class);
	      return new ResultParser<List<Group>>(groups);
	    }
	    return new ResultParser<List<Group>>(error);
	  }

	  /**
	   * 创建分组
	   * 
	   * @param name 分组名字（30个字符以内）
	   */
	  public ResultParser<Group> create(String name) {
	    return create(weixin.getAccessToken().getToken(), name);
	  }

	  /**
	   * 创建分组
	   * 
	   * 一个公众账号，最多支持创建500个分组。
	   * 
	   * @param accessToken 调用接口凭证
	   * @param name 分组名字（30个字符以内）
	   */
	  public ResultParser<Group> create(String accessToken, String name) {
	    JSONObject request = new JSONObject();
	    JSONObject group = new JSONObject();
	    group.put("name", name);
	    request.put("group", group);
	    try {
	      String json =
	          weixin.post(
	              "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=" + accessToken,
	              new StringEntity(request.toString(),"UTF-8"));
	      JSONObject jsonObject = new JSONObject(json);
	      ErrorParser error = ErrorParser.parse(jsonObject);
	      if (error != null) {
	        return new ResultParser<Group>(error);
	      }
	      return new ResultParser<Group>(
	          Group.parse(jsonObject.getJSONObject("group")));
	    } catch (UnsupportedEncodingException e) {
	      throw new WeixinException(e);
	    }
	  }

	  /**
	   * 修改分组名
	   * 
	   * @param id 分组id，由微信分配
	   * @param name 分组名字（30个字符以内）
	   */
	  public ResultParser<ErrorParser> update(String id, String name) {
	    return update(weixin.getAccessToken().getToken(), id, name);
	  }

	  /**
	   * 修改分组名
	   * 
	   * @param accessToken 调用接口凭证
	   * @param id 分组id，由微信分配
	   * @param name 分组名字（30个字符以内）
	   */
	  public ResultParser<ErrorParser> update(String accessToken, String id, String name) {
	    JSONObject request = new JSONObject();
	    JSONObject group = new JSONObject();
	    group.put("id", id);
	    group.put("name", name);
	    request.put("group", group);
	    try {
	      String json =
	          weixin.post(
	              "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=" + accessToken,
	              new StringEntity(request.toString(),"UTF-8"));
	      return ResultParser.parse(json, ErrorParser.class);
	    } catch (UnsupportedEncodingException e) {
	      throw new WeixinException(e);
	    }
	  }

	  /**
	   * 移动用户分组
	   * 
	   * @param openId 用户唯一标识符
	   * @param groupId 分组id
	   */
	  public ResultParser<ErrorParser> move(String openId, String groupId) {
	    return move(weixin.getAccessToken().getToken(), openId, groupId);
	  }

	  /**
	   * 移动用户分组
	   * 
	   * @param accessToken 调用接口凭证
	   * @param openId 用户唯一标识符
	   * @param groupId 分组id
	   */
	  public ResultParser<ErrorParser> move(String accessToken, String openId, String groupId) {
	    JSONObject request = new JSONObject();
	    request.put("openid", openId);
	    request.put("to_groupid", groupId);
	    try {
	      String json =
	          weixin.post("https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token="
	              + accessToken, new StringEntity(request.toString(),"UTF-8"));
	      return ResultParser.parse(json, ErrorParser.class);
	    } catch (UnsupportedEncodingException e) {
	      throw new WeixinException(e);
	    }
	  }
}
