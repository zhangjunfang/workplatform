package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxGoodsCategory")
public class WxGoodsCategory extends BaseEntity {

	private static final long serialVersionUID = 1526257821834246023L;
	
	public static final String PATH_SEPARATOR = ",";// 树路径分隔符
	/* 微信ID标识 */
	private String wid;

	/* 分类名称 */
	private String name;
	/* 上级分类Id */
	private String parentId;
	/* 上级分类名称  */
	private String parentName;
	/* 该分类的唯一标识，用于分类路径和模板标识 */
	private String sign;
	/* 排序 */
	private Integer orderList;
	/*树路径*/
	private String path;
	
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
	
	public String getParentId() {
		return parentId;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
}
