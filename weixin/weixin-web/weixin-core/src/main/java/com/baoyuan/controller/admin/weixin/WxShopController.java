package com.baoyuan.controller.admin.weixin;

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
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.entity.weixin.WxShopCategory;
import com.baoyuan.entity.weixin.WxShopFloor;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxShopCategoryService;
import com.baoyuan.service.weixin.WxShopService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxShopController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/shop")
public class WxShopController extends BaseAdminController {

	@Resource
	private WxConfigService wxConfigService;
	@Resource
	private WxShopService wxShopService;
	@Resource
	private WxShopCategoryService wxShopCategoryService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model){
		setLogInfo("微信门店列表页面");
		
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", "1");
		data.put("pid", "0");
		data.put("text", "所有微信");

		tree.add(data);

		List<WxConfig> configs = wxConfigService.getAll(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId());

		String tenantId = "";
		String wid = "";

		for (WxConfig config : configs) {
			data = new HashMap<String, Object>();
			data.put("id", config.getId());
			data.put("pid", "1");
			data.put("text", config.getName());

			if (StringUtils.isEmpty(tenantId)) {
				tenantId = config.getTenantId();
				wid = config.getId();
			}

			tree.add(data);
		}

		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);

		model.addAttribute("tree", JSON.toJSONString(tree));
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_shop_list";
	}
	
	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String wid, Pager pager) {
		setLogInfo("获取微信门店信息数据页面");

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", wid));
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

		pager = wxShopService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}
	
	@RequestMapping(value = "/add/{tenantId}/{wid}")
	public String add(@PathVariable String tenantId, @PathVariable String wid,
			Model model) {
		setLogInfo("添加微信门店页面");
		
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", wid));
		criteria.add(Restrictions.eq("enabled", 1));
		List<WxShopCategory> categoryList = wxShopCategoryService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);
		model.addAttribute("categoryList", categoryList);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_shop_input";
	}
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxShop wxShop, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("新添加微信门店信息");

			wxShop.setTenantId(getCurrentUser().getTenantId());
			wxShop.setCreateUser(getCurrentUser().getUserName());
			wxShop.setCreateDate(new Date());
			wxShop.setCategoryId(wxShop.getCategoryId());
			
			MultipartHttpServletRequest multipart= (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipart.getFile("attachFile");
			MultipartFile multipartFile2 = multipart.getFile("imageFile");
			
			if(multipartFile!=null){
				String filePath=saveAttachFile(multipartFile);
				if(StringUtils.isNotEmpty(filePath)){
					wxShop.setGps(filePath);
				}
			}
			
			if(multipartFile2!=null){
				String logoPath=saveAttachFile(multipartFile2);
				if(StringUtils.isNotEmpty(logoPath)){
					wxShop.setLogoPath(logoPath);
				}
			}
			//保存
		   wxShopService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), wxShop);

		   result.put(STATUS, SUCCESS);
		   result.put(MESSAGE, "");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存微信门店信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存微信门店时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存微信门店时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	
	 
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑微信门店信息(" + id + ")");
		WxShop wxShop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid",wxShop.getWid()));
		criteria.add(Restrictions.eq("enabled", 1));
		List<WxShopCategory> categoryList = wxShopCategoryService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		model.addAttribute("wxShop", wxShop);
		model.addAttribute("id", id);
		model.addAttribute("categoryList", categoryList);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_shop_input";
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxShop wxShop,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("保存修改后的微信门店信息(" + id + ")");
			
			String tenantId=getCurrentUser().getTenantId();
			wxShop.setModifyUser(getCurrentUser().getUserName());
			wxShop.setModifyDate(new Date());
			
			String oldFile = null;
			String oldFile2 = null;
			MultipartHttpServletRequest multipart = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile=multipart.getFile("attachFile");
			MultipartFile multipartFile2=multipart.getFile("imageFile");
			
			WxShop oldWxShop=wxShopService.get(WxGlobal.DATASOURCE_WEIXIN, tenantId, id);
			
			if(multipartFile.getSize()!=0){
					String filePath=saveAttachFile(multipartFile);
					if(StringUtils.isNotEmpty(filePath)){
						wxShop.setGps(filePath);
					}
					oldFile=oldWxShop.getGps();
				}
			if(multipartFile2.getSize()!=0){	
					String imageFile=saveAttachFile(multipartFile2);
					if(StringUtils.isNotEmpty(imageFile)){
						wxShop.setLogoPath(imageFile);
					}
					oldFile2=oldWxShop.getLogoPath();
				}
					
			wxShopService.update(WxGlobal.DATASOURCE_WEIXIN,tenantId, wxShop);
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
			// 删除原附件
			if (StringUtils.isNotEmpty(oldFile)) {
				boolean del = deleteFile(oldFile);
				logger.info("删除附件[" + oldFile + "]" + del);
			}
			if (StringUtils.isNotEmpty(oldFile2)) {
				boolean del = deleteFile(oldFile2);
				logger.info("删除附件[" + oldFile2 + "]" + del);
			}
			
		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信门店信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信门店时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信门店时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信门店信息(" + ids + ")");
		try {
			String tenantId=getCurrentUser().getTenantId();
			 
			List<WxShop> allWxShop=wxShopService.
					findListByIds(WxGlobal.DATASOURCE_WEIXIN,tenantId,Arrays.asList(StringUtils.split(ids, ",")));
			
			for(WxShop shop:allWxShop){
				// 删除原附件
				if(shop.getGps()!=null&&shop.getGps().equals("")){
					boolean del=deleteFile(shop.getGps());
					logger.info("删除附件[" + shop.getGps() + "]" + del);
				}
				if(shop.getLogoPath()!=null&&shop.getLogoPath().equals("")){
					boolean del=deleteFile(shop.getLogoPath());
					logger.info("删除附件[" + shop.getLogoPath() + "]" + del);
				}
			}
			
			wxShopService.delete(WxGlobal.DATASOURCE_WEIXIN,tenantId,Arrays.asList(StringUtils.split(ids, ",")));
			
			return this.ajaxJsonSuccessMessage("");
			
		} catch (ForeignKeyException ex) {
			logger.error("删除微信门店信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信门店信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除微信门店信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除微信门店信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除微信门店信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}
	
	@RequestMapping(value = "/deleteLogo", method = { RequestMethod.POST })
	public @ResponseBody
	Object deleteLogo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			setLogInfo("删除微信门店信息图片，门店信息的id：(" + id + ")");
			String logoPath = request.getParameter("logoPath");

			// 删除原附件
			if (StringUtils.isNotEmpty(logoPath)) {
				boolean del = deleteFile(logoPath);
				logger.info("删除附件[" + logoPath + "]" + del);
			}
			
			WxShop wxShop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),id);
			wxShop.setLogoPath("");
			wxShop.setModifyUser(getCurrentUser().getUserName());
			wxShop.setModifyDate(new Date());
			wxShopService.update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), wxShop);
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
		} catch (Exception ex) {
			logger.error("删除微信门店信息图片时，发生异常", ex);
			result.put(STATUS, ERROR);
			result.put(MESSAGE, "删除附件失败!");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	
	@RequestMapping(value = "/deleteGps", method = { RequestMethod.POST })
	public @ResponseBody
	Object deleteGps(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			setLogInfo("删除微信门店信息的地理位置图片，门店信息的id：(" + id + ")");
			String logoPath = request.getParameter("logoPath");

			// 删除原附件
			if (StringUtils.isNotEmpty(logoPath)) {
				boolean del = deleteFile(logoPath);
				logger.info("删除附件[" + logoPath + "]" + del);
			}
			
			WxShop wxShop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),id);
			wxShop.setGps("");
			wxShop.setModifyUser(getCurrentUser().getUserName());
			wxShop.setModifyDate(new Date());
			wxShopService.update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), wxShop);
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
		} catch (Exception ex) {
			logger.error("删除微信门店信息的地理位置图片时，发生异常", ex);
			result.put(STATUS, ERROR);
			result.put(MESSAGE, "删除附件失败!");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	/**
	 * 名称唯一验证
	 */
	@RequestMapping(value="/checkName",method={RequestMethod.POST})
	public @ResponseBody Object checkName(String property,String oldValue,String wid){
	  boolean result=false;
	  String value=getParameter(property);
	  try{
		  if(StringUtils.isNotEmpty(oldValue)
					&&StringUtils.equalsIgnoreCase(oldValue, value)){
				return true;
		} 
		  String tenantId=getCurrentUser().getTenantId();
		  Criteria criteria = new Criteria();
		  criteria.add(Restrictions.eq(property, value));
		  criteria.add(Restrictions.eq("wid", wid));
		  
		  result=wxShopService.getList(WxGlobal.DATASOURCE_WEIXIN,tenantId, criteria).size()==0;
	  }catch(Exception e){
		  logger.error("门店名称是否可用Ajax验证时，发生异常Exception:"+e);
		  e.printStackTrace();
	  }
	  return result;
	}
}
