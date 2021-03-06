package com.baoyuan.controller.admin.weixin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.baoyuan.bean.Pager;
import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.admin.BaseAdminController;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxFloorBrand;
import com.baoyuan.entity.weixin.WxGoodsCategory;
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxFloorBrandService;
import com.baoyuan.service.weixin.WxGoodsCategoryService;
import com.baoyuan.service.weixin.WxShopService;

@Controller(WxGlobal.SIGN+WxGlobal.ADMIN+"WxSalesBrandController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH+"/salesbrand")
public class WxSalesBrandController extends BaseAdminController{

	@Resource
	private WxConfigService wxConfigService;
	@Resource
	private WxShopService wxShopService;
	@Resource
	private WxFloorBrandService wxFloorBrandService;
	@Resource
	private WxGoodsCategoryService wxGoodsCategoryService;
	
	@RequestMapping(value="/list",method={RequestMethod.GET})
	public String list(Model model){
    	setLogInfo("微信特惠品牌列表页面");
    	
    	List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", "1");
		data.put("pid", "0");
		data.put("text", "所有微信");
		tree.add(data);

		String tenantId = "";
		String shopId = "";
		String treeType = "";
		 
		List<WxConfig> configs = wxConfigService.getAll(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId());
		for (WxConfig config : configs) {
			data = new HashMap<String, Object>();
			data.put("id", config.getId());
			data.put("pid", "1");
			data.put("text", config.getName());
			data.put("type", "CONFIG");
			data.put("icon", getServletContext().getContextPath()+"/static/icons/customers.gif");
			if (StringUtils.isEmpty(tenantId)) {
				tenantId = config.getTenantId();
			}
			tree.add(data);
			
			Criteria criteria = new Criteria();
			criteria.add(Restrictions.eq("wid", config.getId()));
			List<WxShop> allWxShopLists = wxShopService.
					getList(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),criteria);
			for (WxShop wxShop : allWxShopLists) {
				data = new HashMap<String, Object>();
				data.put("id", wxShop.getId());
				data.put("type", "WXSHOP");
				data.put("pid",wxShop.getWid());
				data.put("text", wxShop.getName());
				data.put("icon", getServletContext().getContextPath()+"/static/icons/home.gif");
				if (StringUtils.isEmpty(tenantId)) {
					tenantId = config.getTenantId();
				}
				if(StringUtils.isEmpty(shopId)){
					shopId = wxShop.getId();
					treeType = "WXSHOP"; 
				}
				tree.add(data);
				
			}
			
		}	
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("shopId", shopId);
		model.addAttribute("treeType", treeType);
		model.addAttribute("tree", JSON.toJSONString(tree));
    	return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_sales_brand_list";
	}
	
	 @RequestMapping(value = "/ajax")
		public @ResponseBody
		Object ajax(String shopId, Pager pager) {
			setLogInfo("获取微信特惠品牌页面");

			Criteria criteria = new Criteria();
			criteria.add(Restrictions.eq("shopId", shopId));
			criteria.add(Restrictions.or(Restrictions.notEq("isSales", 0), Restrictions.notEq("isDiscount",0)));
			if (StringUtils.isNotEmpty(pager.getProperty())
					&& StringUtils.isNotEmpty(pager.getKeyword())) {
				criteria.add(Restrictions.like(pager.getProperty(),
						"%" + pager.getKeyword() + "%"));
			}

			if (StringUtils.isNotEmpty(pager.getOrderBy())
					&& StringUtils.isNotEmpty(pager.getOrderType())) {
				criteria.add(Restrictions.order(pager.getOrderBy(), pager
						.getOrderType().toUpperCase()));
			}

			pager = wxFloorBrandService.getPager(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), criteria, pager);

			Map<String, Object> result = new HashMap<String, Object>();
			result.put("Rows", pager.getList());
			result.put("Total", pager.getTotalCount());

			return result;
		}
	 
	 @RequestMapping(value = "/add/{tenantId}/{shopId}")
		public String add(@PathVariable String tenantId, @PathVariable String shopId,
				Model model) {
			setLogInfo("添加微信特惠品牌页面");
			WxShop wxShop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), shopId);
			List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", "1");
			data.put("pid", "0");
			data.put("text", "顶级分类");
			tree.add(data);
			
			Criteria criteria2 = new Criteria();
			criteria2.add(Restrictions.eq("wid",wxShop.getWid()));
			List<WxGoodsCategory> allWxGoodsCategorys = wxGoodsCategoryService.getList(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), criteria2);
			for (WxGoodsCategory wxGoodsCategory : allWxGoodsCategorys) {
				data = new HashMap<String, Object>();
				data.put("id", wxGoodsCategory.getId()+",CATEGORY");
				if(StringUtils.isNotEmpty(wxGoodsCategory.getParentId()) && !wxGoodsCategory.getParentId().equals("1")){
					data.put("pid",wxGoodsCategory.getParentId()+",CATEGORY");
				}else{
					data.put("pid","1");
				}
				data.put("text", wxGoodsCategory.getName());
				tree.add(data);
			}
			
			Criteria criteria = new Criteria();
			criteria.add(Restrictions.eq("shopId", wxShop.getId()));
			criteria.add(Restrictions.eq("isSales", 0));
			criteria.add(Restrictions.eq("isDiscount", 0));
			List<WxFloorBrand> brandList = wxFloorBrandService.getList(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), criteria);
			for(WxFloorBrand brand :brandList){
				data = new HashMap<String, Object>();
				data.put("id", brand.getId()+",BRAND");
				data.put("pid", brand.getCategoryId()+",CATEGORY");
				data.put("text", brand.getName());
				tree.add(data);
			}
			
			model.addAttribute("tenantId", tenantId);
			model.addAttribute("shopId", wxShop.getId());
			model.addAttribute("tree", JSON.toJSONString(tree));
			return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_sales_brand_input";
		}
	 

		@RequestMapping(value = "/save", method = { RequestMethod.POST })
		public @ResponseBody
		Object save( String brandId,BigDecimal discount,String salesType, HttpServletRequest request) {
			Map<String, Object> result = new HashMap<String, Object>();
			try {
				setLogInfo("新添加微信特惠品牌品牌信息");
				WxFloorBrand brand = wxFloorBrandService.get(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(), brandId);
				brand.setModifyUser(getCurrentUser().getUserName());
				brand.setModifyDate(new Date());
				brand.setDiscount(discount);
				if(salesType.equals("isSales")){
					brand.setIsSales(1);
				}else{
					brand.setIsDiscount(1);
				}
				
				MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
				MultipartFile multipartFile = multipartHttpservletRequest.getFile("attachFile");
				
				if(multipartFile!=null){
					String filePath = saveAttachFile(multipartFile);
					if(StringUtils.isNotEmpty(filePath)){
						brand.setStorePath(filePath);
					}
				}
				
				//保存
				 wxFloorBrandService.
				 update(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(), brand);
				 
				 result.put(STATUS, SUCCESS);
				 result.put(MESSAGE, "");

			} catch (DataSourceDescriptorException ex) {
				logger.error("保存微信特惠品牌品牌信息时，发生(DataSourceDescriptorException)异常", ex);
				return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
			} catch (ServiceException ex) {
				logger.error("保存微信特惠品牌品牌时，发生(ServiceException)异常", ex);
				return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
			} catch (Exception ex) {
				logger.error("保存微信特惠品牌品牌时，发生异常", ex);
				return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
			}
			return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
		}
		
		@RequestMapping(value = "/edit/{id}")
		public String edit(@PathVariable String id, Model model) {
			setLogInfo("编辑微信特惠品牌信息(" + id + ")");
			
			WxFloorBrand wxFloorBrand = wxFloorBrandService.
					get(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), id);
			String salesType ="";
			if(wxFloorBrand.getIsSales()==1){
				salesType = "isSales";
			}else{
				salesType = "isDiscount";
			}
			model.addAttribute("salesType", salesType);
			model.addAttribute("wxFloorBrand", wxFloorBrand);
			model.addAttribute("id", id);
			return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_sales_brand_input";
		}
		
		@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
		public @ResponseBody
		Object update(@PathVariable("id") String id,BigDecimal discount,String salesType,HttpServletRequest request) {
			Map<String, Object> result = new HashMap<String, Object>();
			try {
				setLogInfo("保存修改后的微信特惠品牌信息(" + id + ")");
				
				String oldFile = null;
				MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
				MultipartFile multipartFile = multipartHttpservletRequest.getFile("attachFile");
				
				WxFloorBrand oldWxFloorBrand = wxFloorBrandService.get(WxGlobal.DATASOURCE_WEIXIN,
						getCurrentUser().getTenantId(), id);
				oldWxFloorBrand.setModifyUser(getCurrentUser().getUserName());
				oldWxFloorBrand.setModifyDate(new Date());
				oldWxFloorBrand.setDiscount(discount);
				if(salesType.equals("isSales")){
					oldWxFloorBrand.setIsSales(1);
				}else{
					oldWxFloorBrand.setIsDiscount(1);
				}
				
				if(multipartFile.getSize()!=0){
					String filePath = saveAttachFile(multipartFile);
					if(StringUtils.isNotEmpty(filePath)){
						oldFile = oldWxFloorBrand.getStorePath();
						oldWxFloorBrand.setStorePath(filePath);
					}
				}
				wxFloorBrandService.
				update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), oldWxFloorBrand);
				
				result.put(STATUS, SUCCESS);
				result.put(MESSAGE, "");
				
				if (StringUtils.isNotEmpty(oldFile)) {
					boolean del = deleteFile(oldFile);// 删除原附件
					logger.info("删除附件[" + oldFile + "]" + del);
				}
				
			} catch (DataSourceDescriptorException ex) {
				logger.error("修改微信门店特惠品牌信息时，发生(DataSourceDescriptorException)异常", ex);
				return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
			} catch (ServiceException ex) {
				logger.error("修改微信门店特惠品牌时，发生(ServiceException)异常", ex);
				return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
			} catch (Exception ex) {
				logger.error("修改微信门店特惠品牌时，发生异常", ex);
				return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
			}
			return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
		}
		
		@RequestMapping(value = "/delete", method = { RequestMethod.POST })
		public @ResponseBody
		Object delete(String ids, HttpServletRequest request) {
			setLogInfo("删除微信门店特惠品牌信息(" + ids + ")");
			try {
				 
				List<WxFloorBrand> allwxFloorBrand = wxFloorBrandService.
				findListByIds(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(),Arrays.asList(StringUtils.split(ids, ",")));
				for(WxFloorBrand brand : allwxFloorBrand){
					brand.setIsSales(0);
					brand.setIsDiscount(0);
					brand.setStorePath(null);
					wxFloorBrandService.update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), brand);
				}
				
				for(WxFloorBrand fb:allwxFloorBrand){
					if(fb.getStorePath()!=null && !fb.getStorePath().equals("")){
						boolean del = deleteFile(fb.getStorePath());//删除原附件
						logger.info("删除附件[" + fb.getStorePath() + "]" + del);
					}
				}
				
				return this.ajaxJsonSuccessMessage("");
			} catch (ForeignKeyException ex) {
				logger.error("删除微信门店特惠品牌信息时，发生(ForeignKeyException)异常", ex);
				return this.ajaxJsonErrorMessage("微信门店信息在用,无法删除");
			} catch (DataSourceDescriptorException ex) {
				logger.error("删除微信门店特惠品牌信息时，发生(DataSourceDescriptorException)异常", ex);
				return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
			} catch (ServiceException ex) {
				logger.error("删除微信门店特惠品牌信息时，发生(ServiceException)异常", ex);
				return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
			} catch (Exception ex) {
				logger.error("删除微信门店特惠品牌信息时，发生异常", ex);
				return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
			}
		}
	 
}
