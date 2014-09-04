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
import com.baoyuan.entity.weixin.WxShopFloor;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxFloorBrandService;
import com.baoyuan.service.weixin.WxGoodsCategoryService;
import com.baoyuan.service.weixin.WxShopFloorService;
import com.baoyuan.service.weixin.WxShopService;

@Controller(WxGlobal.SIGN+WxGlobal.ADMIN+"WxFloorBrandController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH+"/floorbrand")
public class WxFloorBrandController extends BaseAdminController{
	
	@Resource
	private WxFloorBrandService wxFloorBrandService;
	@Resource
	private WxConfigService wxConfigService;
	@Resource
	private WxShopService wxShopService;
	@Resource
	private WxShopFloorService wxShopFloorService;
	@Resource
	private WxGoodsCategoryService wxGoodsCategoryService;
	
    @RequestMapping(value="/list",method={RequestMethod.GET})
	public String list(Model model){
    	setLogInfo("微信楼层列表页面");
    	
    	List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", "1");
		data.put("pid", "0");
		data.put("text", "所有微信");
		tree.add(data);

		String tenantId = "";
		String treeId = "";
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
				tree.add(data);
				
				Criteria criteria1 = new Criteria();
				criteria1.add(Restrictions.eq("shopId", wxShop.getId()));
				List<WxShopFloor> allWxShopFloor=wxShopFloorService.
						getList(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(), criteria1);
				for(WxShopFloor shopFloor:allWxShopFloor){
					data=new HashMap<String,Object>();
					data.put("id",shopFloor.getId());
					data.put("pid",shopFloor.getShopId());
					data.put("type", "WXFLOOR");
					data.put("text",shopFloor.getName());
					data.put("icon", getServletContext().getContextPath()+"/static/icons/communication.gif");
					if(StringUtils.isEmpty(treeId)){
						treeId=shopFloor.getId();
						treeType="WXFLOOR";
					}
					tree.add(data);
				}
			}
			
		}	
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("treeId", treeId);
		model.addAttribute("treeType", treeType);
		model.addAttribute("tree", JSON.toJSONString(tree));
    	return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_floor_brand_list";
	}
    
    @RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String floorId, Pager pager) {
		setLogInfo("获取微信楼层品牌页面");

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("floorId", floorId));
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
    
    @RequestMapping(value = "/add/{tenantId}/{floorId}")
	public String add(@PathVariable String tenantId, @PathVariable String floorId,
			Model model) {
		setLogInfo("添加微信楼层品牌页面");
		WxShopFloor floor = wxShopFloorService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), floorId);
		WxShop wxShop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), floor.getShopId());
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
			data.put("id", wxGoodsCategory.getId()+","+wxGoodsCategory.getName());
			if(StringUtils.isNotEmpty(wxGoodsCategory.getParentId()) && !wxGoodsCategory.getParentId().equals("1")){
				data.put("pid",wxGoodsCategory.getParentId()+","+wxGoodsCategory.getParentName());
			}else{
				data.put("pid","1");
			}
			data.put("text", wxGoodsCategory.getName());
			tree.add(data);
		}
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("floorId", floorId);
		model.addAttribute("shopId", wxShop.getId());
		model.addAttribute("tree", JSON.toJSONString(tree));
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_floor_brand_input";
	}
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxFloorBrand wxFloorBrand, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("新添加微信楼层品牌信息");

			wxFloorBrand.setTenantId(getCurrentUser().getTenantId());
			wxFloorBrand.setCreateUser(getCurrentUser().getUserName());
			wxFloorBrand.setCreateDate(new Date());
			wxFloorBrand.setCategoryId(wxFloorBrand.getCategoryId());
			wxFloorBrand.setIsSales(0);
			wxFloorBrand.setShopId(wxFloorBrand.getShopId());
			wxFloorBrand.setIsDiscount(0);
			wxFloorBrand.setDiscount(new BigDecimal(0.00));
			
			MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile2 = multipartHttpservletRequest.getFile("imgFile");	
			logger.error("multipartFile2--------"+multipartFile2);
			
			if(multipartFile2!=null){
				String filePath = saveAttachFile(multipartFile2);
				if(StringUtils.isNotEmpty(filePath)){
					wxFloorBrand.setLogoPath(filePath);
				}
			}
			
			//保存
			 wxFloorBrandService.
			 save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(), wxFloorBrand);
			 
			 result.put(STATUS, SUCCESS);
			 result.put(MESSAGE, "");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存微信楼层品牌信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存微信楼层品牌时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存微信楼层品牌时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑微信楼层品牌信息(" + id + ")");
		
		WxFloorBrand wxFloorBrand = wxFloorBrandService.
				get(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), id);
		WxShopFloor floor = wxShopFloorService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), wxFloorBrand.getFloorId());
		WxShop wxShop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), floor.getShopId());
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
			data.put("id", wxGoodsCategory.getId()+","+wxGoodsCategory.getName());
			if(StringUtils.isNotEmpty(wxGoodsCategory.getParentId()) && !wxGoodsCategory.getParentId().equals("1")){
				data.put("pid",wxGoodsCategory.getParentId()+","+wxGoodsCategory.getParentName());
			}else{
				data.put("pid","1");
			}
			data.put("text", wxGoodsCategory.getName());
			tree.add(data);
		}
		model.addAttribute("wxFloorBrand", wxFloorBrand);
		model.addAttribute("id", id);
		model.addAttribute("tree", JSON.toJSONString(tree));
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_floor_brand_input";
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxFloorBrand wxFloorBrand,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("保存修改后的微信楼层品牌信息(" + id + ")");
			wxFloorBrand.setModifyUser(getCurrentUser().getUserName());
			wxFloorBrand.setModifyDate(new Date());
			
			String oldFile = null;
			MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile2 = multipartHttpservletRequest.getFile("imgFile");	
			
			WxFloorBrand oldWxFloorBrand = wxFloorBrandService.get(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), id);
			
			if(multipartFile2.getSize()!=0){
				String filePath = saveAttachFile(multipartFile2);
				if(StringUtils.isNotEmpty(filePath)){
					wxFloorBrand.setLogoPath(filePath);
				}
				oldFile = oldWxFloorBrand.getLogoPath();
			}
			wxFloorBrandService.
			update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), wxFloorBrand);
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
			if (StringUtils.isNotEmpty(oldFile)) {
				boolean del = deleteFile(oldFile);// 删除原附件
				logger.info("删除附件[" + oldFile + "]" + del);
			}
			
		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信门店楼层信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信门店楼层时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信门店楼层时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信门店楼层信息(" + ids + ")");
		try {
			 
			List<WxFloorBrand> allwxFloorBrand = wxFloorBrandService.
			findListByIds(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(),Arrays.asList(StringUtils.split(ids, ",")));
			
			for(WxFloorBrand fb:allwxFloorBrand){
				if(fb.getStorePath()!=null && !fb.getStorePath().equals("")){
					boolean del = deleteFile(fb.getStorePath());//删除原附件
					logger.info("删除附件[" + fb.getStorePath() + "]" + del);
				}
				if(fb.getLogoPath()!=null && !fb.getLogoPath().equals("")){
					boolean del = deleteFile(fb.getLogoPath());//删除原附件
					logger.info("删除附件[" + fb.getLogoPath() + "]" + del);
				}
			}
			
			wxFloorBrandService.
			delete(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(),Arrays.asList(StringUtils.split(ids, ",")));
			
			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除微信门店楼层信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信门店信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除微信门店楼层信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除微信门店楼层信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除微信门店楼层信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}
	
	/**
	 * 名称唯一验证
	 */
	@RequestMapping(value="/checkName",method={RequestMethod.POST})
	public @ResponseBody Object checkName(String property,String oldValue,String floorId){
	  boolean result=false;
	  String value=getParameter(property);
	  try{
		  if(StringUtils.isNotEmpty(oldValue)
					&&StringUtils.equalsIgnoreCase(oldValue, value)){
				return true;
		} 
		  String tenantId=getCurrentUser().getTenantId();
		  logger.error("floorId---"+floorId);
		  Criteria criteria = new Criteria();
		  criteria.add(Restrictions.eq(property, value));
		  criteria.add(Restrictions.eq("floorId",floorId));
		  
		  result=wxFloorBrandService.getList(WxGlobal.DATASOURCE_WEIXIN,tenantId, criteria).size()==0;
		  logger.error("result---"+result);
	  }catch(Exception e){
		  logger.error("部门名称是否可用Ajax验证时，发生异常Exception:"+e);
		  e.printStackTrace();
	  }
	return result;
	}
	
	@RequestMapping(value = "/deleteLogo", method = { RequestMethod.POST })
	public @ResponseBody
	Object deleteLogo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			setLogInfo("删除微信门店楼层信息图片，id为：(" + id + ")");
			String logoPath = request.getParameter("logoPath");

			// 删除原附件
			if (StringUtils.isNotEmpty(logoPath)) {
				boolean del = deleteFile(logoPath);
				logger.info("删除附件[" + logoPath + "]" + del);
			}
			
			WxFloorBrand wxFloorBrand = wxFloorBrandService.get(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),id);
			wxFloorBrand.setLogoPath("");
			wxFloorBrand.setModifyUser(getCurrentUser().getUserName());
			wxFloorBrand.setModifyDate(new Date());
			wxFloorBrandService.update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), wxFloorBrand);
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
		} catch (Exception ex) {
			logger.error("删除微信门店楼层信息里的图片时，发生异常", ex);
			result.put(STATUS, ERROR);
			result.put(MESSAGE, "删除附件失败!");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
}
