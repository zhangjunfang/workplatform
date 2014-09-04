package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxArticle")
public class WxArticle extends BaseEntity {

	private static final long serialVersionUID = 684308984057039508L;
	private String shopId;//门店ID
	private String categoryId;//文章分类ID
	private String categoryName;//文章分类名称
	
	private String title;// 标题
	private String author;// 作者
	private String logoPath;//图片
	private String content;// 内容
	private Integer isPublication;// 是否发布
	private Integer hits;// 点击数
	private String source;// 来源
	
	public String getShopId() {
		return shopId;
	}
	
	public void setShopId(String shopId) {
		this.shopId = shopId;
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

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getIsPublication() {
		return isPublication;
	}
	
	public void setIsPublication(Integer isPublication) {
		this.isPublication = isPublication;
	}
	
	public Integer getHits() {
		return hits;
	}
	
	public void setHits(Integer hits) {
		this.hits = hits;
	}
	
	public String getSource() {
		return source;
	}
	
	public void setSource(String source) {
		this.source = source;
	}
	
}
