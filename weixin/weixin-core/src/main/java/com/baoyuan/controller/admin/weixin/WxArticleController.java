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
import com.baoyuan.entity.weixin.WxArticle;
import com.baoyuan.entity.weixin.WxArticleCategory;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxArticleCategoryService;
import com.baoyuan.service.weixin.WxArticleService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxShopService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxArticleController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/article")
public class WxArticleController extends BaseAdminController{

	@Resource
	private WxArticleService wxArticleService;
	@Resource
	private WxArticleCategoryService wxArticleCategoryService;
	@Resource
	private WxConfigService wxConfigService;
	@Resource
	private WxShopService wxShopService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model){
		setLogInfo("微信门店文章列表页面");
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
		String treeId = "";
		String treeType = "";

		for (WxConfig config : configs) {
			data = new HashMap<String, Object>();
			data.put("id", config.getId());
			data.put("pid", "1");
			data.put("text", config.getName());
			data.put("type", "CONFIG");
			data.put("icon", getServletContext().getContextPath()+"/static/icons/customers.gif");
			if (StringUtils.isEmpty(tenantId)) {
				tenantId = config.getTenantId();
				wid = config.getId();
			}

			tree.add(data);
			
			Criteria criteria = new Criteria();
			criteria.add(Restrictions.eq("wid", config.getId()));
			List<WxShop> allWxShopLists = wxShopService.getList(
					WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),
					criteria);
			for (WxShop wxShop : allWxShopLists) {
				data = new HashMap<String, Object>();
				data.put("id", wxShop.getId());
				data.put("type", "WXSHOP");
				data.put("pid",wxShop.getWid());
				data.put("text", wxShop.getName());
				data.put("icon", getServletContext().getContextPath()+"/static/icons/home.gif");
				if (StringUtils.isEmpty(treeId)) {
					treeId = wxShop.getId();
					treeType = "WXSHOP";
				}
				tree.add(data);
			}
			
		}
		
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);
		model.addAttribute("treeId", treeId);
		model.addAttribute("treeType", treeType);
		model.addAttribute("tree", JSON.toJSONString(tree));
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_article_list";
	}
	
	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String shopId, Pager pager) {
		setLogInfo("获取微信文章信息数据页面");

		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("shopId", shopId));
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

		pager = wxArticleService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}
	
	@RequestMapping(value = "/add/{tenantId}/{shopId}")
	public String add(@PathVariable String tenantId, @PathVariable String shopId,
			Model model) {
		setLogInfo("添加微信文章页面");
		WxShop shop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), shopId);
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", shop.getWid()));
		List<WxArticleCategory> categoryList = wxArticleCategoryService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("shopId", shopId);
		model.addAttribute("categoryList", categoryList);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_article_input";
	}
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxArticle wxArticle, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("新添加微信门店文章信息");

			wxArticle.setHits(0);
			wxArticle.setTenantId(getCurrentUser().getTenantId());
			wxArticle.setCreateUser(getCurrentUser().getUserName());
			wxArticle.setCreateDate(new Date());
			
			wxArticle.setCategoryName(urlDecoder(wxArticle.getCategoryName()));
			wxArticle.setTitle(urlDecoder(wxArticle.getTitle()));
			wxArticle.setAuthor(urlDecoder(wxArticle.getAuthor()));
			wxArticle.setSource(urlDecoder(wxArticle.getSource()));
			
			String wxContent = urlDecoder(wxArticle.getContent());
			wxContent = wxContent.replace("&lt;", "<");
			wxContent = wxContent.replace("&gt;", ">");
			wxArticle.setContent(wxContent);

			MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpservletRequest.getFile("imgFile");	
			logger.error("multipartFile--------"+multipartFile);
			
			if(multipartFile.getSize()!=0){
				String filePath = saveAttachFile(multipartFile);
				if(StringUtils.isNotEmpty(filePath)){
					wxArticle.setLogoPath(filePath);
				}
			}
			//保存
		  wxArticleService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), wxArticle);

		  result.put(STATUS, SUCCESS);
			 result.put(MESSAGE, "");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存微信门店文章信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存微信门店文章时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存微信门店文章时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑微信门店文章信息(" + id + ")");
		WxArticle wxArticle = wxArticleService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		Criteria criteria = new Criteria();
		WxShop shop = wxShopService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), wxArticle.getShopId());
		criteria.add(Restrictions.eq("wid", shop.getWid()));
		List<WxArticleCategory> categoryList = wxArticleCategoryService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		model.addAttribute("wxArticle", wxArticle);
		model.addAttribute("id", id);
		model.addAttribute("categoryList", categoryList);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_article_input";
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxArticle wxArticle,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("保存修改后的微信门店文章信息(" + id + ")");
			wxArticle.setModifyUser(getCurrentUser().getUserName());
			wxArticle.setModifyDate(new Date());
			
			wxArticle.setCategoryName(urlDecoder(wxArticle.getCategoryName()));
			wxArticle.setTitle(urlDecoder(wxArticle.getTitle()));
			if(wxArticle.getAuthor()==null || "".equals(wxArticle.getAuthor()))
			{
				wxArticle.setAuthor("");
			}
			else
			{
				wxArticle.setAuthor(urlDecoder(wxArticle.getAuthor()));
			}
			
			if(wxArticle.getSource()==null || "".equals(wxArticle.getSource()))
			{
				wxArticle.setSource("");
			}
			else
			{
				wxArticle.setSource(urlDecoder(wxArticle.getSource()));
			}
			
			String wxContent = urlDecoder(wxArticle.getContent());
			wxContent = wxContent.replace("&lt;", "<");
			wxContent = wxContent.replace("&gt;", ">");
			wxArticle.setContent(wxContent);
			
			String oldFile = null;
			MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpservletRequest.getFile("imgFile");	
			logger.error("multipartFile--------"+multipartFile);
			
			WxArticle oldWxArticle = wxArticleService.get(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), id);
			
			if(multipartFile.getSize()!=0){
				String filePath = saveAttachFile(multipartFile);
				if(StringUtils.isNotEmpty(filePath)){
					wxArticle.setLogoPath(filePath);
				}
				oldFile = oldWxArticle.getLogoPath();
			}
			
			
			wxArticleService.update(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), wxArticle);

			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
			if (StringUtils.isNotEmpty(oldFile)) {
				boolean del = deleteFile(oldFile);// 删除原附件
				logger.info("删除附件[" + oldFile + "]" + del);
			}

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信门店文章信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信门店文章时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信门店文章时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信门店文章信息(" + ids + ")");
		logger.error("删除微信门店文章信息ids"+ids);
		try {
			List<WxArticle> allwxArticle = wxArticleService.
					findListByIds(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(),Arrays.asList(StringUtils.split(ids, ",")));
					
			for (WxArticle fb : allwxArticle) {
				if (fb.getLogoPath() != null && !fb.getLogoPath().equals("")) {
					boolean del = deleteFile(fb.getLogoPath());// 删除原附件
					logger.info("删除附件[" + fb.getLogoPath() + "]" + del);
				}
			}

			wxArticleService.delete(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(),Arrays.asList(StringUtils.split(ids, ",")));

					return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除微信门店文章信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信门店信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除微信门店文章信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除微信门店文章信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除微信门店文章信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}
	
	@RequestMapping(value = "/deleteLogo", method = { RequestMethod.POST })
	public @ResponseBody
	Object deleteLogo(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String id = request.getParameter("id");
			setLogInfo("删除微信文章Logo，门店信息的id：(" + id + ")");
			String logoPath = request.getParameter("logoPath");

			// 删除原附件
			if (StringUtils.isNotEmpty(logoPath)) {
				boolean del = deleteFile(logoPath);
				logger.info("删除附件[" + logoPath + "]" + del);
			}
			
			WxArticle wxArticle = wxArticleService.get(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),id);
			wxArticle.setLogoPath("");
			wxArticle.setModifyUser(getCurrentUser().getUserName());
			wxArticle.setModifyDate(new Date());
			wxArticleService.update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), wxArticle);
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
		} catch (Exception ex) {
			logger.error("删除微信文章Logo时，发生异常", ex);
			result.put(STATUS, ERROR);
			result.put(MESSAGE, "删除附件失败!");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	/**
	 * 名称唯一验证
	 */
	@RequestMapping(value="/checkName",method={RequestMethod.POST})
	public @ResponseBody Object checkName(String property,String oldValue,String shopId){
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
		  criteria.add(Restrictions.eq("shopId", shopId));
		  
		  result=wxArticleService.getList(WxGlobal.DATASOURCE_WEIXIN,tenantId, criteria).size()==0;
	  }catch(Exception e){
		  logger.error("文章标题是否可用Ajax验证时，发生异常Exception:"+e);
		  e.printStackTrace();
	  }
	  return result;
	}
	
}
