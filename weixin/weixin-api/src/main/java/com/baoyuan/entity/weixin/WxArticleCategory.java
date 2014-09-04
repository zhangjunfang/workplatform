package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.validation.annotation.Size;

@Alias(WxGlobal.SIGN + "WxArticleCategory")
public class WxArticleCategory extends BaseEntity{

	private static final long serialVersionUID = 5631948900373209592L;

	/* 微信ID标识 */
	private String wid;
	/* 分类名称 */
	private String name;
	/* 分类标识 */
	private String sign;
	/* 排序 */
	private Integer orderList;
	
	public String getWid() {
		return wid;
	}
	
	public void setWid(String wid) {
		this.wid = wid;
	}
	
	@Size(max=255)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSign() {
		return sign;
	}
	
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public Integer getOrderList() {
		return orderList;
	}
	
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	
}
