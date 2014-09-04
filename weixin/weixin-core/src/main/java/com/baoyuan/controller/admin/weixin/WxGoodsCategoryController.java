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

import com.alibaba.fastjson.JSON;
import com.baoyuan.bean.Pager;
import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.controller.admin.BaseAdminController;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxGoodsCategory;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxGoodsCategoryService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxGoodsCategoryController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/goodscategory")
public class WxGoodsCategoryController extends BaseAdminController{

	@Resource
	private WxConfigService wxConfigService;
	@Resource
	private WxGoodsCategoryService wxGoodsCategoryService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model){
		setLogInfo("微信商品分类列表页面");
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
			data.put("type", "CONFIG");
			data.put("icon", getServletContext().getContextPath()+"/static/icons/customers.gif");
			if (StringUtils.isEmpty(tenantId)) {
				tenantId = config.getTenantId();
				wid = config.getId();
			}

			tree.add(data);
			
		}
		
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);
		model.addAttribute("tree", JSON.toJSONString(tree));
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_goods_category_list";
	}
	
	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String wid, Pager pager) {
		setLogInfo("获取微信商品分类信息数据页面");

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

		pager = wxGoodsCategoryService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}
	
	@RequestMapping(value = "/add/{tenantId}/{wid}")
	public String add(@PathVariable String tenantId, @PathVariable String wid,
			Model model) {
		setLogInfo("添加微信商品页面");
		
		List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", "1");
		data.put("pid", "0");
		data.put("text", "顶级分类");
		tree.add(data);
		
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid",wid));
		List<WxGoodsCategory> allWxGoodsCategorys = wxGoodsCategoryService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
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
		model.addAttribute("wid", wid);
		model.addAttribute("tree", JSON.toJSONString(tree));
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_goods_category_input";
	}
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxGoodsCategory wxGoodsCategory, HttpServletRequest request) {
		try {
			setLogInfo("新添加微信商品分类信息");

			wxGoodsCategory.setTenantId(getCurrentUser().getTenantId());

			wxGoodsCategory.setCreateUser(getCurrentUser().getUserName());
			wxGoodsCategory.setCreateDate(new Date());

			//保存
			wxGoodsCategoryService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), wxGoodsCategory);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存微信商品分类信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存微信商品分类时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存微信商品分类时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑微信商品分类信息(" + id + ")");
		WxGoodsCategory wxGoodsCategory = wxGoodsCategoryService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("wxGoodsCategory", wxGoodsCategory);
		model.addAttribute("id", id);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_goods_category_input";
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxGoodsCategory wxGoodsCategory) {
		try {
			setLogInfo("保存修改后的微信商品分类信息(" + id + ")");
			wxGoodsCategory.setModifyUser(getCurrentUser().getUserName());
			wxGoodsCategory.setModifyDate(new Date());
			wxGoodsCategoryService.update(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), wxGoodsCategory);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信商品分类信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信商品分类时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信商品分类时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信商品分类信息(" + ids + ")");
		try {
			wxGoodsCategoryService.delete(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(),
					Arrays.asList(StringUtils.split(ids, ",")));
			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除微信商品分类信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信商品分类信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除微信商品分类信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除微信商品分类信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除微信商品分类信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
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
		  
		  result=wxGoodsCategoryService.getList(WxGlobal.DATASOURCE_WEIXIN,tenantId, criteria).size()==0;
	  }catch(Exception e){
		  logger.error("商品标题是否可用Ajax验证时，发生异常Exception:"+e);
		  e.printStackTrace();
	  }
	  return result;
	}
}
