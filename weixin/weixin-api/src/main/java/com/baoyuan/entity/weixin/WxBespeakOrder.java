package com.baoyuan.entity.weixin;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxBespeakOrder")
public class WxBespeakOrder extends BaseEntity{

	private static final long serialVersionUID = 3214802193761266153L;
	
	/* 门店ID */
	private String shopId;
	/* 门店预约服务Id */
	private String bespeakId;
	/* 门店预约名称 */
	private String bespeakName;
	/* 联系人 */
	private String name;
	/* 联系电话 */
	private String tel;
	/* 预订人数 */
	private Integer number;
	/* 预订日期 */
	private Date orderDate;
	/* 备注 */
	private String memo;
	
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getBespeakId() {
		return bespeakId;
	}
	public void setBespeakId(String bespeakId) {
		this.bespeakId = bespeakId;
	}
	public String getBespeakName() {
		return bespeakName;
	}
	public void setBespeakName(String bespeakName) {
		this.bespeakName = bespeakName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
