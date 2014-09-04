package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxMenu")
public class WxMenu extends BaseEntity {

	private static final long serialVersionUID = -1539751275443416424L;

	/* 微信ID标识 */
	private String wid;
	/* 菜单的响应动作类型，目前有click、view两种类型 */
	private String type;
	/* click类型必须,菜单KEY值，用于消息接口推送，不超过128字节 */
	private String key;
	/* view类型必须,网页链接，用户点击菜单可打开链接，不超过256字节 */
	private String url;
	/* 菜单标题，不超过16个字节，子菜单不超过40个字节 */
	private String name;
	/* 父模块 */
	private String parentId;
	/* 显示序号 */
	private Integer orderNo;
	/* 备注说明 */
	private String description;

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
