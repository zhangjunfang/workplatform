package com.baoyuan.controller.web.weixin;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.weixin.WxArticle;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxFloorBrand;
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.service.weixin.WxArticleService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxFloorBrandService;
import com.baoyuan.service.weixin.WxShopService;

@Controller(WxGlobal.SIGN + WxGlobal.WEB + "MemberController")
@RequestMapping(WxGlobal.WEIXIN_WEB_PATH + "/member")
public class MemberController extends BaseWebController{
	
	
	@Resource(name = WxGlobal.SIGN + "WxShopService")
	private WxShopService wxShopService;
	@Resource(name = WxGlobal.SIGN + "WxConfigService")
	private WxConfigService wxConfigService;
	@Resource(name = WxGlobal.SIGN + "WxArticleService")
	private WxArticleService wxArticleService;
	@Resource
	private WxFloorBrandService wxFloorBrandService;
	
	private String shopnav;
	
	@RequestMapping(value = "/{wid}", method = { RequestMethod.GET })
	public String index(@PathVariable String wid,Model model) {
		logger.error("MemberController index wid:"+wid);
		List<WxShop> shopList = getShopList(wid);
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("categoryName", "会员专享"));
		criteria.add(Restrictions.eq("isPublication",1));
		List<WxArticle> list = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		if(list.size()>=1){
			WxArticle article = list.get(0);
			model.addAttribute("article", article);
		}
		model.addAttribute("shopList", shopList);
		shopnav = "member";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/member/member";
	}
	
	@RequestMapping(value = "rights", method = { RequestMethod.GET })
	public String rights(Model model) {
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("categoryName", "会员权益"));
		criteria.add(Restrictions.eq("isPublication",1));
		List<WxArticle> list = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		if(list.size()>=1){
			WxArticle article = list.get(0);
			model.addAttribute("article", article);
		}
		model.addAttribute("shopList", shopList);
		shopnav = "rights";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/member/member";
	}
	
	@RequestMapping(value = "discount")
	public String discount(Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("isDiscount", 1));
		criteria.add(Restrictions.notEq("storePath", null));
		List<WxFloorBrand> brandList = wxFloorBrandService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		model.addAttribute("shopList", shopList);
		model.addAttribute("brandList", brandList);
		shopnav = "discount";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/member/member_brand";
	}
	
}
