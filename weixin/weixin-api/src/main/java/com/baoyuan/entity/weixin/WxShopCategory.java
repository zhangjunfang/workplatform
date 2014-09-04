package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxShopCategory")
public class WxShopCategory extends BaseEntity {

	private static final long serialVersionUID = -8161806563406931273L;

	/* 微信ID标识 */
	private String wid;
	/* 分类名称 */
	private String name;
	/*是否启用*/
	private Integer enabled;
	/*备注说明*/
	private String description;
	
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
