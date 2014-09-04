package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.validation.annotation.Size;

@Alias(WxGlobal.SIGN + "WxUnionMerchant")
public class WxUnionMerchant extends BaseEntity {

	private static final long serialVersionUID = 154021168306648682L;

	/* 微信ID标识 */
	private String wid;
	/* 商户名称 */
	private String name;
	/* 介绍 */
	private String introduction;
	/* 类型 */
	private String typeId;
	/* 类型名 */
	private String typeName;

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	/**
	 * 字段名称 :商户名称   数据类型 :varchar(32) 是否主键 :false 是否必填 :true
	 */
	@Size(max = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 字段名称 :商户介绍   数据类型 :text 是否主键 :false 是否必填 :false
	 */
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	/**
	 * 字段名称 :商户类型   数据类型 :bigint 是否主键 :false 是否必填 :true
	 */
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
