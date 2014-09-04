package com.baoyuan.entity.weixin;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxFollowers")
public class WxFollowers extends BaseEntity {

	private static final long serialVersionUID = -9202189120403847443L;

	/* 微信ID标识 */
	private String wid;

	/* 用户的唯一标识 */
	private String openId;

	/* 用户所属分组 */
	private String groupId;
	
	/* 用户昵称 */
	private String nickname;

	/* 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 */
	private Integer gender;

	/* 用户个人资料填写的省份 */
	private String province;

	/* 普通用户个人资料填写的城市 */
	private String city;

	/* 国家，如中国为CN */
	private String country;

	/* 用户特权信息，json 数组，如微信沃卡用户为（chinaunicom） */
	private String privilege;

	/* 用户的语言，简体中文为zh_CN */
	private String language;

	/* 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空 */
	private String headImgUrl;

	/* 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。 */
	private Boolean subscribe;

	/* 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 */
	private Date subscribeTime;

	/*备注说明*/
	private String description;
	
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public Boolean getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(Boolean subscribe) {
		this.subscribe = subscribe;
	}

	public Date getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(Date subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
