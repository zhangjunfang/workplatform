package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.validation.annotation.Size;

@Alias(WxGlobal.SIGN + "WxUnionMerchantType")
public class WxUnionMerchantType extends BaseEntity {

	private static final long serialVersionUID = -1166302027531595622L;
	
	/* 微信ID标识 */
	private String wid;
	/* 商户类型名称 */
	private String name;
	/* 商户类型图片*/
	private String imgPath;
	/* 是否启用 */
	private Integer enabled;
	/* 备注 */
	private String memo;
	
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	/**
	 * 字段名称 :商户名称 数据类型 :varchar(32) 是否主键 :false 是否必填 :true
	 */
	@Size(max = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    /**
     * 字段名称：商户类型图片  数据类型:varchar(100)  是否主键 :false 是否必填 :false
     */
	@Size(max=100)
	public String getImgPath()
	{
		return imgPath;
	}
	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	/**
	 * 字段名称 :是否启用 数据类型 :int 是否主键 :false 是否必填 :true
	 */
	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	/**
	 * 字段名称 :备注说明 数据类型 :varchar(200) 是否主键 :false 是否必填 :false
	 */
	@Size(max = 200)
	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
