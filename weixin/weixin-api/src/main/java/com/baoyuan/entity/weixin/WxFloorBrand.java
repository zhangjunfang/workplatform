package com.baoyuan.entity.weixin;

import java.math.BigDecimal;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxFloorBrand")
public class WxFloorBrand extends BaseEntity {
 
	private static final long serialVersionUID = 8552729756056205993L;
	/* 品牌分类ID */
	private String categoryId;
	/* 品牌分类名称 */
	private String categoryName;
	/* 品牌名称 */
	private String name;
	/* 品牌logo */
	private String logoPath;
	/* 品牌图片 */
	private String storePath;
	/* 品牌描述 */
	private String description;
	/* 楼层ID */
	private String floorId;
	/* 门店ID */
	private String shopId;
	/* 是否参与特惠品牌 */
	private Integer isSales;
	/* 会员是否折扣 */
	private Integer isDiscount;
	/* 折扣 */
	private BigDecimal discount;
	
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
	public String getStorePath() {
		return storePath;
	}
	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFloorId() {
		return floorId;
	}
	public void setFloorId(String floorId) {
		this.floorId = floorId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public Integer getIsSales() {
		return isSales;
	}
	public void setIsSales(Integer isSales) {
		this.isSales = isSales;
	}
	public Integer getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(Integer isDiscount) {
		this.isDiscount = isDiscount;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	
}
