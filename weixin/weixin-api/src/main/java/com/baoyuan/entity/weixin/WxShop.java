package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.validation.annotation.Size;

@Alias(WxGlobal.SIGN + "WxShop")
public class WxShop extends BaseEntity{

	private static final long serialVersionUID = -4186941236657344213L;
	/* 微信ID标识 */
	private String wid;
	
	/* 门店名称 */
	private String name;
	/* 门店地址 */
	private String address;
	/* 联系电话 */
	private String phone;
	/* 门店简介 */
	private String description;
	/* 交通指南 */
	private String traffic;
	/* 营业时间 */
	private String shophors;
	/* 地理位置 */
	private String gps;
	/* 备注 */
	private String memo;
	/* 门店类型Id */
	private String categoryId;
	/* 门店类型名称 */
	private String categoryName;
	/* 门店图片路径 */
	private String logoPath;
	
	
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	/**
	* 字段名称 :门店名称
	* 数据类型 :varchar(64)
	* 是否主键 :false
	* 是否必填 :true
	*/
	@Size(max=64)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	* 字段名称 :门店地址
	* 数据类型 :varchar(100)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=100)
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	* 字段名称 :门店电话
	* 数据类型 :varchar(32)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=32)
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	/**
	* 字段名称 :门店简介
	* 数据类型 :text
	* 是否主键 :false
	* 是否必填 :false
	*/
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	* 字段名称 :交通指南
	* 数据类型 :varchar(200)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=200)
	public String getTraffic() {
		return traffic;
	}
	
	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}
	
	/**
	* 字段名称 :营业时间
	* 数据类型 :varchar(60)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=60)
	public String getShophors() {
		return shophors;
	}
	
	public void setShophors(String shophors) {
		this.shophors = shophors;
	}
	
	/**
	* 字段名称 :地理位置
	* 数据类型 :varchar(200)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=200)
	public String getGps() {
		return gps;
	}
	
	public void setGps(String gps) {
		this.gps = gps;
	}
	
	/**
	* 字段名称 :门店备注
	* 数据类型 :varchar(200)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=200)
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}

	/**
	* 字段名称 :门店类型Id
	* 数据类型 :varchar(20)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=20)
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	/**
	* 字段名称 :门店类型名称
	* 数据类型 :varchar(32)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=32)
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	/**
	* 字段名称 :门店图片路径
	* 数据类型 :varchar
	* 是否主键 :false
	* 是否必填 :false
	*/
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	
}
