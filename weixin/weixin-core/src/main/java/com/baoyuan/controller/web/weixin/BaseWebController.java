package com.baoyuan.controller.web.weixin;

import java.util.List;

import javax.annotation.Resource;

import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.BaseController;
import com.baoyuan.entity.weixin.WxArticle;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.entity.weixin.WxUser;
import com.baoyuan.service.weixin.WxArticleService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxShopService;
import com.baoyuan.service.weixin.WxUserService;

public abstract class BaseWebController extends BaseController {

	protected String DEFAULT_CREATE_USER = "tencent";
	protected String DEFAULT_CREATE_CONFIG = "CURRENT_CONFIG";
	protected String DEFAULT_CREATE_SHOP = "CURRENT_SHOP";
	
	@Resource(name = WxGlobal.SIGN + "WxShopService")
	private WxShopService wxShopService;
	@Resource(name = WxGlobal.SIGN + "WxConfigService")
	private WxConfigService wxConfigService;
	@Resource(name = WxGlobal.SIGN + "WxUserService")
	private WxUserService wxUserService;
	@Resource(name = WxGlobal.SIGN + "WxArticleService")
	private WxArticleService wxArticleService;
	
	/**
	 * 获取微信号下所有门店
	 * @param wid 微信号id
	 * @return
	 */
	public List<WxShop> getShopList(String wid){
		WxUser wxUser = null;
		WxConfig wxConfig = null;
		/* 本机测试 if(getSession().getAttribute(DEFAULT_CREATE_USER)==null){
			wxUser = wxUserService.get(WxGlobal.DATASOURCE_WEIXIN, "165617974705651712", "222896195528294400");
			wxConfig = wxConfigService.get(WxGlobal.DATASOURCE_WEIXIN, "165617974705651712", "206579160028545024");
			getSession().setAttribute(DEFAULT_CREATE_USER, wxUser);
		}else{
		}*/
		wxUser = (WxUser) getSession().getAttribute(DEFAULT_CREATE_USER);
		wxConfig = wxConfigService.getWxConfig(WxGlobal.DATASOURCE_WEIXIN, wid);
		
		if(wid!=null && getSession().getAttribute(DEFAULT_CREATE_CONFIG) == null){
			getSession().setAttribute(DEFAULT_CREATE_CONFIG, wxConfig);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", wid));
		List<WxShop> shopList = wxShopService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		if(shopList.size()>=1 && getSession().getAttribute(DEFAULT_CREATE_SHOP) == null){
			WxShop wxShop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, wxShop);
		}
		return shopList;
	}
	/**
	 * 获取微信号下所有即时促销的文章
	 * @param wid 微信号id
	 * @return
	 */
	public List<WxArticle> getArticleList(String wid){
	WxUser wxUser = null;
	WxConfig wxConfig = null;
	wxUser = (WxUser) getSession().getAttribute(DEFAULT_CREATE_USER);
	wxConfig = wxConfigService.getWxConfig(WxGlobal.DATASOURCE_WEIXIN, wid);
	if(wid!=null && getSession().getAttribute(DEFAULT_CREATE_CONFIG) == null){
		getSession().setAttribute(DEFAULT_CREATE_CONFIG, wxConfig);
	}
	Criteria criteria = new Criteria();
	criteria.add(Restrictions.eq("categoryName", "即时促销"));
	criteria.add(Restrictions.eq("isPublication",1));
	List<WxArticle> articleList = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
	return articleList;
	}
}
