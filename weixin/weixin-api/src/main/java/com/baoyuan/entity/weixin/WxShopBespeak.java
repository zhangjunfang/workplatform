package com.baoyuan.entity.weixin;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxShopBespeak")
public class WxShopBespeak extends BaseEntity {

	private static final long serialVersionUID = -7775608498280780670L;
	
	/* 门店ID */
	private String shopId;
	/* 预约服务名称 */
	private String name;
	/* 预约服务描述 */
	private String description;
	/* 是否发布 */
	private Integer isPublication;
	/* 可预约数量 */
	private Integer store;
	/* 服务剩余可预约量*/
	private Integer surplusStore;
	/* 服务图片路径存储 */
	private String logoPath;
	/* 有效期开始时间 */
	private Date startDate;
	/* 有效期结束时间 */
	private Date endDate;
	/* 地址 */
	private String address;
	/* 电话 */
	private String phone;
	
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getIsPublication() {
		return isPublication;
	}
	public void setIsPublication(Integer isPublication) {
		this.isPublication = isPublication;
	}
	public Integer getStore() {
		return store;
	}
	public void setStore(Integer store) {
		this.store = store;
	}
	
	public Integer getSurplusStore() {
		return surplusStore;
	}
	public void setSurplusStore(Integer surplusStore) {
		this.surplusStore = surplusStore;
	}
	public String getLogoPath() {
		return logoPath;
	}
	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
