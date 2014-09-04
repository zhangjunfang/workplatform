package com.baoyuan.controller.web.weixin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.weixin.WxArticle;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxFloorBrand;
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.entity.weixin.WxShopFloor;
import com.baoyuan.entity.weixin.WxUnionMerchant;
import com.baoyuan.entity.weixin.WxUnionMerchantType;
import com.baoyuan.service.weixin.WxArticleService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxFloorBrandService;
import com.baoyuan.service.weixin.WxShopFloorService;
import com.baoyuan.service.weixin.WxShopService;
import com.baoyuan.service.weixin.WxUnionMerchantService;
import com.baoyuan.service.weixin.WxUnionMerchantTypeService;

@Controller(WxGlobal.SIGN + WxGlobal.WEB + "CaseMentController")
@RequestMapping(WxGlobal.WEIXIN_WEB_PATH + "/casement")
public class CaseMentController  extends BaseWebController{
	
	@Resource(name = WxGlobal.SIGN + "WxShopService")
	private WxShopService wxShopService;
	@Resource(name = WxGlobal.SIGN + "WxConfigService")
	private WxConfigService wxConfigService;
	@Resource(name = WxGlobal.SIGN + "WxArticleService")
	private WxArticleService wxArticleService;
	@Resource(name = WxGlobal.SIGN + "WxShopFloorService")
	private WxShopFloorService wxShopFloorService;
	@Resource(name = WxGlobal.SIGN + "WxFloorBrandService")
	private WxFloorBrandService wxFloorBrandService;
	@Resource
	private WxUnionMerchantService wxUnionMerchantService;
    @Resource
    private WxUnionMerchantTypeService WxUnionMerchantTypeService;
	
	private String shopnav;

	@RequestMapping(value = "/{wid}", method = { RequestMethod.GET })
	public String index(@PathVariable String wid,Model model) {
		logger.error("CaseMentController index wid:"+wid);
		List<WxShop> shopList = getShopList(wid);
		model.addAttribute("shopList", shopList);
		shopnav = "casement";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/casement/shop_desc";
	}
	@RequestMapping(value = "/navigation/{wid}", method = { RequestMethod.GET })
	public String indexOne(@PathVariable String wid,Model model) {
		logger.error("CaseMentController index wid:"+wid);
		List<WxShop> shopList = getShopList(wid);
		model.addAttribute("shopList", shopList);
		shopnav = "casement";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/casement/shop_navigation";
	}
	
	@RequestMapping(value = "shop/{shopId}" , method = { RequestMethod.GET })
	public Object shop(@PathVariable String shopId,Model model, HttpServletRequest request,HttpServletResponse response){
		String requestURI = request.getParameter("redirectionUrl");
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), shopId);
		if(getSession().getAttribute(DEFAULT_CREATE_SHOP)==null){
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}else{
			if(!shopId.equals("favicon")){
				getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
			}
		}

		try {
			response.sendRedirect(requestURI);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "brandnav")
	public String brandnav(Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.order("no", "asc"));
		List<WxShopFloor> floorList = wxShopFloorService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		Map<String,List<WxFloorBrand>> brandMap = new HashMap<String, List<WxFloorBrand>>();
		for(WxShopFloor wxShopFloor :floorList){
			Criteria c = new Criteria();
			c.add(Restrictions.eq("floorId", wxShopFloor.getId().toString()));
			List<WxFloorBrand> brandList = wxFloorBrandService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), c);
			brandMap.put(wxShopFloor.getId(), brandList);
		}
		
		model.addAttribute("shopList", shopList);
		model.addAttribute("floorList", floorList);
		model.addAttribute("brandMap", brandMap);
		shopnav = "brandnav";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/casement/shop_brand";
	}
	
	@RequestMapping(value = "guide")
	public String guide(Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("categoryName", "服务指引"));
		List<WxArticle> articleList = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		model.addAttribute("shopList", shopList);
	    String id = null;
		if(articleList!=null && articleList.size()>0)
		{
			for(WxArticle article : articleList)
			{
				id = article.getId();
			}
		}
		WxArticle article = wxArticleService.get(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), id);
		
		model.addAttribute("article", article);
		
		shopnav = "guide";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/casement/article_content";
	}
	
//	@RequestMapping(value = "guide")
//	public String guide(Model model){
//		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
//		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
//		List<WxShop> shopList = getShopList(wxConfig.getId());
//		if(shop==null && shopList !=null){
//			shop = shopList.get(0);
//			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
//		}
//		Criteria criteria = new Criteria();
//		criteria.add(Restrictions.eq("shopId", shop.getId()));
//		criteria.add(Restrictions.eq("categoryName", "服务指引"));
//		List<WxArticle> articleList = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
//		model.addAttribute("shopList", shopList);
//		model.addAttribute("articleList", articleList);
//		shopnav = "guide";
//		model.addAttribute("shopnav", shopnav);
//		model.addAttribute("wid", wxConfig.getId());
//		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/casement/article";
//	}
//	
//	@RequestMapping(value = "guide/{id}")
//	public String guideContent(@PathVariable String id,Model model){
//		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
//		WxArticle article = wxArticleService.get(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), id);
//		List<WxShop> shopList = getShopList(wxConfig.getId());
//		model.addAttribute("shopList", shopList);
//		model.addAttribute("article", article);
//		shopnav = "guide";
//		model.addAttribute("shopnav", shopnav);
//		model.addAttribute("wid", wxConfig.getId());
//		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/casement/article_content";
//	}
	
	@RequestMapping(value = "groom")
	public String groom(Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("categoryName", "推荐信息"));
		List<WxArticle> articleList = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		model.addAttribute("shopList", shopList);
		model.addAttribute("articleList", articleList);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/casement/article";
	}
	
	@RequestMapping(value = "groom/{id}")
	public String groomContent(@PathVariable String id,Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxArticle article = wxArticleService.get(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), id);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		model.addAttribute("shopList", shopList);
		model.addAttribute("article", article);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/casement/article_content";
	}
	
	@RequestMapping(value = "/article/{wid}", method = { RequestMethod.GET })
	public String article(@PathVariable String wid,@RequestParam("title") String title,Model model) {
		 
		WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.DATASOURCE_WEIXIN, wid);
		//WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		 
		Criteria criteria = new Criteria();
		//criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("categoryName", title));
		criteria.add(Restrictions.eq("isPublication",1));
		List<WxArticle> list = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		if(list.size()>=1){
			WxArticle article = list.get(0);
			model.addAttribute("article", article);
		}
		model.addAttribute("title",title);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/article/enterprise";
	}
	
	@RequestMapping(value = "/merchant/{wid}", method = { RequestMethod.GET })
	public String merchant(@PathVariable String wid,Model model) {
		 
		WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.DATASOURCE_WEIXIN, wid);
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", wid));
		criteria.add(Restrictions.eq("tenantId",wxConfig.getTenantId()));
		
	    List<WxUnionMerchantType> allLIST=WxUnionMerchantTypeService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
	
	    model.addAttribute("allLIST",allLIST);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/article/merchantType";
	}
	
	
	@RequestMapping(value = "/merchantList/{wid}", method = { RequestMethod.GET })
	public String merchantList(@PathVariable String wid,@RequestParam("name") String name,Model model) {
		 
		WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.DATASOURCE_WEIXIN, wid);
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", wid));
		criteria.add(Restrictions.eq("tenantId",wxConfig.getTenantId()));
		criteria.add(Restrictions.eq("typeName",name));
		
	    List<WxUnionMerchant> allLIST=wxUnionMerchantService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
	
	    model.addAttribute("allLIST",allLIST);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/article/merchant";
	}
	
//	@RequestMapping(value = "/merchant/{wid}", method = { RequestMethod.GET })
//	public String merchant(@PathVariable String wid,Model model) {
//		 
//		WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.DATASOURCE_WEIXIN, wid);
//		Criteria criteria = new Criteria();
//		criteria.add(Restrictions.eq("wid", wid));
//		criteria.add(Restrictions.eq("tenantId",wxConfig.getTenantId()));
//	    List<WxUnionMerchant> allLIST=wxUnionMerchantService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
//	
//	    model.addAttribute("allLIST",allLIST);
//		model.addAttribute("wid", wid);
//		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/article/merchant";
//	}
	
	@RequestMapping(value = "/merchantInfo/{wid}", method = { RequestMethod.GET })
	public String merchantInfo(@PathVariable String wid,@RequestParam("id") String id,Model model) {
		 
		WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.DATASOURCE_WEIXIN, wid);
		 
	    WxUnionMerchant merchant=wxUnionMerchantService.get(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), id);
	
	    model.addAttribute("merchant",merchant);
	    
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/article/merchantInfo";
	}
	
}
