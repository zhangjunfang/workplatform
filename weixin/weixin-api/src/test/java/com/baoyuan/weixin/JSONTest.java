package com.baoyuan.weixin;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import com.baoyuan.weixin.bean.Article;

public class JSONTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Article> articles = new ArrayList<Article>();

		Article item = new Article();
		item.setTitle("这里是Title");
		item.setPicUrl("http://www.shop8188.com/shop/template/web/img/graph.png");
		item.setUrl("http://www.wp99.cn/vipeng/");
		item.setDescription("备注说明消息");
		articles.add(item);

		item = new Article();
		item.setTitle("这里是Title");
		item.setPicUrl("http://www.shop8188.com/shop/template/web/img/graph.png");
		item.setUrl("http://www.wp99.cn/vipeng/");
		item.setDescription("备注说明消息");

		articles.add(item);

		JSONObject obj = new JSONObject();
		
		JSONArray array = new JSONArray();
	      for (Article article : articles) {
	        
	        JSONObject _item = new JSONObject();
	        _item.put("Title", article.getTitle());
	        _item.put("Description", article.getDescription());
	        _item.put("PicUrl", article.getPicUrl());
	        _item.put("Url", article.getUrl());
	        
	        array.put(_item);
	      }
	      
	      obj.put("Articles", new JSONObject().put("item", array));
		
		System.out.println(XML.toString(obj,"xml"));
	}
}
