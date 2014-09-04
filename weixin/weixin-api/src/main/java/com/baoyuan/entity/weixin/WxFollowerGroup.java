package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxFollowerGroup")
public class WxFollowerGroup extends BaseEntity {

	private static final long serialVersionUID = -3254549581568198867L;

	/* 微信ID标识 */
	private String wid;

	/* 分组id，由微信分配 */
	private String groupId;

	/* 分组名字，UTF8编码 */
	private String name;

	/* 分组内用户数量 */
	private Integer count;

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
