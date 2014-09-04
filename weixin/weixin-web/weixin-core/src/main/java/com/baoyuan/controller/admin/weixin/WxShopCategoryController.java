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
import com.baoyuan.entity.weixin.WxShopCategory;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxShopCategoryService;
import com.baoyuan.service.weixin.WxShopService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxShopCategoryController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/shopcategory")
public class WxShopCategoryController extends BaseAdminController {

	@Resource
	private WxConfigService wxConfigService;
	@Resource
	private WxShopService wxShopService;
	@Resource
	private WxShopCategoryService wxShopCategoryService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model){
		setLogInfo("微信门店类别列表页面");
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
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_shop_category_list";
	}
	
	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String wid, Pager pager) {
		setLogInfo("获取微信门店类别信息数据页面");

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

		pager = wxShopCategoryService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}
	
	@RequestMapping(value = "/add/{tenantId}/{wid}")
	public String add(@PathVariable String tenantId, @PathVariable String wid,
			Model model) {
		setLogInfo("添加微信门店类别页面");
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_shop_category_input";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑微信门店类别信息(" + id + ")");
		WxShopCategory wxShopCategory = wxShopCategoryService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("wxShopCategory", wxShopCategory);
		model.addAttribute("id", id);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_shop_category_input";
	}
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxShopCategory wxShopCategory, HttpServletRequest request) {
		try {
			setLogInfo("新添加微信门店类别信息");

			wxShopCategory.setTenantId(getCurrentUser().getTenantId());

			wxShopCategory.setCreateUser(getCurrentUser().getUserName());
			wxShopCategory.setCreateDate(new Date());

			//保存
			wxShopCategoryService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), wxShopCategory);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存微信门店类别信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存微信门店类别时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存微信门店类别时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxShopCategory wxShopCategory) {
		try {
			setLogInfo("保存修改后的微信门店类别信息(" + id + ")");
			wxShopCategory.setModifyUser(getCurrentUser().getUserName());
			wxShopCategory.setModifyDate(new Date());
			wxShopCategoryService.update(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), wxShopCategory);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信门店类别信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信门店类别时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信门店类别时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信门店类别信息(" + ids + ")");
		try {
			wxShopCategoryService.delete(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(),
					Arrays.asList(StringUtils.split(ids, ",")));
			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除微信门店类别信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信门店类别信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除微信门店类别信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除微信门店类别信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除微信门店类别信息时，发生异常", ex);
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
		  
		  result=wxShopCategoryService.getList(WxGlobal.DATASOURCE_WEIXIN,tenantId, criteria).size()==0;
	  }catch(Exception e){
		  logger.error("门店类别名称是否可用Ajax验证时，发生异常Exception:"+e);
		  e.printStackTrace();
	  }
	  return result;
	}
	
}
