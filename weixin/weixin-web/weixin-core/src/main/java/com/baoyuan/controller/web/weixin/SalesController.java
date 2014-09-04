package com.baoyuan.controller.web.weixin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.weixin.WxArticle;
import com.baoyuan.entity.weixin.WxBespeakOrder;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxFloorBrand;
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.entity.weixin.WxShopBespeak;
import com.baoyuan.service.weixin.WxArticleService;
import com.baoyuan.service.weixin.WxBespeakOrderService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxFloorBrandService;
import com.baoyuan.service.weixin.WxShopBespeakService;
import com.baoyuan.service.weixin.WxShopService;

@Controller(WxGlobal.SIGN + WxGlobal.WEB + "SalesController")
@RequestMapping(WxGlobal.WEIXIN_WEB_PATH + "/sales")
public class SalesController extends BaseWebController {

	@Resource(name = WxGlobal.SIGN + "WxShopService")
	private WxShopService wxShopService;
	@Resource(name = WxGlobal.SIGN + "WxConfigService")
	private WxConfigService wxConfigService;
	@Resource(name = WxGlobal.SIGN + "WxArticleService")
	private WxArticleService wxArticleService;
	@Resource
	private WxFloorBrandService wxFloorBrandService;
	@Resource
	private WxShopBespeakService wxShopBespeakService;
	@Resource
	private WxBespeakOrderService wxBespeakOrderService;
	
	private String shopnav;

	@RequestMapping(value = "/{wid}", method = { RequestMethod.GET })
	public String index(@PathVariable String wid,Model model) {
		logger.error("SalesController index wid:"+wid);
		List<WxShop> shopList = getShopList(wid);
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("categoryName", "即时促销"));
		criteria.add(Restrictions.eq("isPublication",1));
		criteria.add(Restrictions.order("createDate", "asc"));
		List<WxArticle> list = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		if(list.size()>=1){
			WxArticle article = list.get(0);
			model.addAttribute("article", article);
		}
		model.addAttribute("shopList", shopList);
		shopnav = "sales";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/sales/sales";
	}
	
	/**
	 * 特惠品牌（喜盈门）
	 * @param wid
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "special/{wid}", method = { RequestMethod.GET })
	public String special(@PathVariable String wid,Model model) {
		logger.error("SalesController special wid:"+wid);
		List<WxShop> shopList = getShopList(wid);
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("categoryName", "特惠品牌"));
		criteria.add(Restrictions.eq("isPublication",1));
		criteria.add(Restrictions.order("createDate", "asc"));
		List<WxArticle> list = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		if(list.size()>=1){
			WxArticle article = list.get(0);
			model.addAttribute("article", article);
		}
		model.addAttribute("shopList", shopList);
		shopnav = "brandsales";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/sales/sales_brand";
	}
	
	@RequestMapping(value = "newdm")
	public String newdm(Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("categoryName", "DM分享"));
		List<WxArticle> articleList = wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		model.addAttribute("shopList", shopList);
		model.addAttribute("articleList", articleList);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/sales/article";
	}
	
	@RequestMapping(value = "newdm/{id}")
	public String newdmContent(@PathVariable String id,Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxArticle article = wxArticleService.get(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), id);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		model.addAttribute("shopList", shopList);
		model.addAttribute("article", article);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/sales/article_content";
	}
	
	@RequestMapping(value = "brandsales")
	public String brandsales(Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("isSales", 1));
		criteria.add(Restrictions.notEq("storePath", null));
		List<WxFloorBrand> brandList = wxFloorBrandService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		model.addAttribute("shopList", shopList);
		model.addAttribute("brandList", brandList);
		shopnav = "brandsales";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/sales/sales_brand";
	}
	
	@RequestMapping(value = "bespeak")
	public String bespeak(Model model){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shop.getId()));
		criteria.add(Restrictions.eq("isPublication", 1));
		List<WxShopBespeak> bespeakList = wxShopBespeakService.getList(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), criteria);
		shopnav = "bespeak";
		model.addAttribute("shopnav", shopnav);
		model.addAttribute("bespeakList", bespeakList);
		model.addAttribute("shopList", shopList);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/sales/shop_bespeak";
	}
	
	@RequestMapping(value = "bespeakOrder")
	public String bespeakOrder(Model model, HttpServletRequest request){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		String bespeakId = request.getParameter("bespeakId");
		WxShopBespeak bespeak = wxShopBespeakService.get(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), bespeakId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Date today = new Date();
		List<Date> orderDateList = new ArrayList<Date>();
		Calendar cd = Calendar.getInstance();   
		if((today.getTime()>=bespeak.getStartDate().getTime() && today.getTime()<=bespeak.getEndDate().getTime()) || today.getTime()<bespeak.getStartDate().getTime()){
			if(today.getTime()<bespeak.getStartDate().getTime()){
				today=bespeak.getStartDate();
			}
			while(today.getTime()<bespeak.getEndDate().getTime()){
				orderDateList.add(today);
				cd.setTime(today);
				cd.add(Calendar.DATE,1);
				today = cd.getTime();
				if(sdf.format(today).equals(sdf.format(bespeak.getEndDate()))){
					orderDateList.add(bespeak.getEndDate());
				}
			}
		}
		
		model.addAttribute("bespeak", bespeak);
		model.addAttribute("orderDateList", orderDateList);
		model.addAttribute("shopList", shopList);
		model.addAttribute("wid", wxConfig.getId());
		return WxGlobal.WEIXIN_WEB_TEMPLATE+"/wsite/default/sales/bespeak_order";
	}
	
	@RequestMapping(value = "operations")
	public @ResponseBody Object operations(Model model, HttpServletRequest request){
		WxConfig wxConfig = (WxConfig) getSession().getAttribute(DEFAULT_CREATE_CONFIG);
		WxShop shop = (WxShop)getSession().getAttribute(DEFAULT_CREATE_SHOP);
		List<WxShop> shopList = getShopList(wxConfig.getId());
		if(shop==null && shopList !=null){
			shop = shopList.get(0);
			getSession().setAttribute(DEFAULT_CREATE_SHOP, shop);
		}
		String bespeakId = request.getParameter("bespeakId");
		String name = request.getParameter("name");
		String tel = request.getParameter("tel");
		String number = request.getParameter("number");
		String orderDate = request.getParameter("order");
		String memo = request.getParameter("memo");
		String action = request.getParameter("action");
		//String token = request.getParameter("token");
		String aid = request.getParameter("aid");
		WxShopBespeak bespeak = wxShopBespeakService.get(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), bespeakId);
		if(action.equals("add")){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			WxBespeakOrder order = new WxBespeakOrder();
			order.setCreateUser(aid);
			order.setCreateDate(new Date());
			order.setTenantId(wxConfig.getTenantId());
			order.setBespeakId(bespeak.getId());
			order.setBespeakName(bespeak.getName());
			order.setShopId(shop.getId());
			order.setName(name);
			order.setTel(tel);
			order.setNumber(Integer.parseInt(number));
			if(orderDate != null){
				try {
					Date ssss =sdf.parse(orderDate);
					order.setOrderDate(ssss);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			order.setMemo(memo);
			
			wxBespeakOrderService.save(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), order);
			
			bespeak.setSurplusStore(bespeak.getSurplusStore()-order.getNumber());
			wxShopBespeakService.update(WxGlobal.DATASOURCE_WEIXIN, wxConfig.getTenantId(), bespeak);
			
		}else{
			
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "预约成功");
		return result;
	}
}
