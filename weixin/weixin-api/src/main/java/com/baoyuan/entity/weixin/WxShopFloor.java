package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxShopFloor")
public class WxShopFloor extends BaseEntity{

	private static final long serialVersionUID = 7992530145510948710L;
	
	/* 门店Id */
	private String shopId;
	
	/* 楼层编号 */
	private String no;
	/* 楼层名称 */
	private String name;
	/* 楼层图片  */
	private String logoPath;
	/* 楼层描述 */
	private String description;
	
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
