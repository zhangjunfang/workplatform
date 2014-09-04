package com.baoyuan.weixin.api;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;

import com.baoyuan.weixin.API;
import com.baoyuan.weixin.bean.GetFollowersResult;
import com.baoyuan.weixin.bean.User;
import com.baoyuan.weixin.parse.ResultParser;

public final class UserApi extends API {

	protected UserApi(WeixinApi weixin) {
		super(weixin);
	}

	/**
	   * 拉取用户信息(需scope为 snsapi_userinfo)。适用于网页授权的用户。
	   * 
	   * 如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了。
	   * 
	   * @param accessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	   * @param openId 用户的唯一标识
	   */
	  public ResultParser<User> snsapiUserInfo(String accessToken, String openId) {
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    weixin.addParameter(params, "access_token", accessToken);
	    weixin.addParameter(params, "openid", openId);
	    weixin.addParameter(params, "lang", "zh_CN");
	    
	    String json = weixin.get("https://api.weixin.qq.com/sns/userinfo", params);
	    try {
			json = new String (json.getBytes(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// 拉取用户信息时重新编码为 UTF-8
			e.printStackTrace();
		}
	    return ResultParser.parse(json, User.class);
	  }

	  /**
	   * 获取用户基本信息，适用于已关注公众帐号的用户。
	   * 
	   * 在关注者与公众号产生消息交互后，公众号可获得关注者的OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的。对于不同公众号，同一用户的openid不同）。
	   * 公众号可通过本接口来根据OpenID获取用户基本信息，包括昵称、头像、性别、所在城市、语言和关注时间。
	   * 
	   * @param accessToken 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
	   * @param openId 用户的唯一标识
	   */
	  public ResultParser<User> userInfo(String accessToken, String openId) {
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    weixin.addParameter(params, "access_token", accessToken);
	    weixin.addParameter(params, "openid", openId);
	    weixin.addParameter(params, "lang", "zh_CN");
	    String json = weixin.get("https://api.weixin.qq.com/cgi-bin/user/info", params);
	    return ResultParser.parse(json, User.class);
	  }

	  /**
	   * 获取所欲关注者列表，包含用户详细信息，该接口采用循环多次获取用户详细信息的方式。如果关注者太多可能会很慢。还会超过微信API调用次数限制。请谨慎调用。建议只在第一次同步关注着信息时调用。
	   */
	  public ResultParser<List<User>> getFollowUsers() {
	    return getFollowUsers(weixin.getAccessToken().getToken());
	  }

	  /**
	   * 获取所欲关注者列表，包含用户详细信息，该接口采用循环多次获取用户详细信息的方式。如果关注者太多可能会很慢。还会超过微信API调用次数限制。请谨慎调用。建议只在第一次同步关注着信息时调用。
	   * 
	   * @param accessToken 调用接口凭证
	   */
	  public ResultParser<List<User>> getFollowUsers(String accessToken) {
	    List<User> users =
	        new ArrayList<User>();
	    ResultParser<GetFollowersResult> followersResult = getFollowers(accessToken);
	    if (followersResult.success()) {
	      for (String openId : followersResult.getResult().getOpenIds()) {
	        ResultParser<User> userResult = userInfo(accessToken, openId);
	        if (userResult.success()) {
	          users.add(userResult.getResult());
	        } else {
	          return new ResultParser<List<User>>(userResult.getError());
	        }
	      }

	      return new ResultParser<List<User>>(users);
	    }
	    return new ResultParser<List<User>>(followersResult.getError());
	  }

	  /**
	   * 获取所欲关注者列表
	   */
	  public ResultParser<GetFollowersResult> getFollowers() {
	    return getFollowers(weixin.getAccessToken().getToken());
	  }

	  /**
	   * 获取所欲关注者列表
	   * 
	   * @param accessToken 调用接口凭证
	   */
	  public ResultParser<GetFollowersResult> getFollowers(String accessToken) {
	    GetFollowersResult result = new GetFollowersResult();
	    List<String> openIds = new ArrayList<String>();
	    ResultParser<GetFollowersResult> followers = getFollowers(accessToken, null);
	    while (followers.success()) {
	      for (String openId : followers.getResult().getOpenIds()) {
	        openIds.add(openId);
	      }
	      String nextOpenid = followers.getResult().getNextOpenid();
	      if (StringUtils.isBlank(nextOpenid) || followers.getResult().getTotal() == openIds.size()) {
	        break;
	      }
	      followers = getFollowers(accessToken, nextOpenid);
	    }
	    if (!followers.success()) {
	      return new ResultParser<GetFollowersResult>(followers.getError());
	    }
	    result.setTotal(openIds.size());
	    result.setCount(openIds.size());
	    result.setOpenIds(openIds);
	    return new ResultParser<GetFollowersResult>(result);
	  }

	  /**
	   * 获取关注者列表
	   * 
	   * 公众号可通过本接口来获取帐号的关注者列表，关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。一次拉取调用最多拉取10000个关注者的OpenID
	   * ，可以通过多次拉取的方式来满足需求。
	   * 
	   * 文档地址：http://mp.weixin.qq.com/wiki/index.php?title=获取关注者列表
	   * 
	   * @param accessToken 调用接口凭证
	   * @param openId 第一个拉取的OPENID，不填默认从头开始拉取
	   */
	  public ResultParser<GetFollowersResult> getFollowers(String accessToken, String openId) {
	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    weixin.addParameter(params, "access_token", accessToken);
	    weixin.addNotNullParameter(params, "next_openid", openId);
	    String json = weixin.get("https://api.weixin.qq.com/cgi-bin/user/get", params);
	    return ResultParser.parse(json, GetFollowersResult.class);
	  }
}
