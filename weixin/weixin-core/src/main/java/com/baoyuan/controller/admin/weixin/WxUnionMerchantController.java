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
import com.baoyuan.entity.weixin.WxUnionMerchant;
import com.baoyuan.entity.weixin.WxUnionMerchantType;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxUnionMerchantService;
import com.baoyuan.service.weixin.WxUnionMerchantTypeService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxUnionMerchantController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/merchant")
public class WxUnionMerchantController extends BaseAdminController {
	
    @Resource
	private WxUnionMerchantService wxUnionMerchantService;
    @Resource
    private WxUnionMerchantTypeService WxUnionMerchantTypeService;
	@Resource
	private WxConfigService wxConfigService;
	
    @RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model){
		setLogInfo("联盟商户列表页面");
		
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
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_unionMerchant_list";
	}
	
	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String wid, Pager pager) {
		setLogInfo("获取微信联盟商户信息数据页面");

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

		pager = wxUnionMerchantService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}
	
	@RequestMapping(value = "/add/{tenantId}/{wid}")
	public String add(@PathVariable String tenantId, @PathVariable String wid,
			Model model) {
		setLogInfo("添加微信联盟商户页面");
		
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid", wid));
		criteria.add(Restrictions.eq("enabled", 1));
		List<WxUnionMerchantType> listType = WxUnionMerchantTypeService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);
		model.addAttribute("listType", listType);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_unionMerchant_input";
	}
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxUnionMerchant wxUnionMerchant, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("新添加微信联盟商户信息");

			wxUnionMerchant.setTenantId(getCurrentUser().getTenantId());
			wxUnionMerchant.setCreateUser(getCurrentUser().getUserName());
			wxUnionMerchant.setCreateDate(new Date());
			
			String wxContent = wxUnionMerchant.getIntroduction();
			wxContent = wxContent.replace("&lt;", "<");
			wxContent = wxContent.replace("&gt;", ">");
			wxUnionMerchant. setIntroduction(wxContent); 
			//保存
			wxUnionMerchantService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(), wxUnionMerchant);

		   result.put(STATUS, SUCCESS);
		   result.put(MESSAGE, "");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存微信联盟商户信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存微信联盟商户时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存微信联盟商户时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
		return result;
	}
	
	
	 
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑微信门店信息(" + id + ")");
		WxUnionMerchant  wxMerchant = wxUnionMerchantService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("wid",wxMerchant.getWid()));
		 
		List<WxUnionMerchantType> listType = WxUnionMerchantTypeService.getList(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria);
		
		model.addAttribute("wxMerchant", wxMerchant);
		model.addAttribute("id", id);
		model.addAttribute("listType", listType);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_unionMerchant_input";
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxUnionMerchant wxUnionMerchant,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("保存修改后的微信联盟商户信息(" + id + ")");
			
			String tenantId=getCurrentUser().getTenantId();
			
			wxUnionMerchant.setModifyUser(getCurrentUser().getUserName());
			wxUnionMerchant.setModifyDate(new Date());
			
			String wxContent = wxUnionMerchant.getIntroduction();
			wxContent = wxContent.replace("&lt;", "<");
			wxContent = wxContent.replace("&gt;", ">");
			wxUnionMerchant. setIntroduction(wxContent); 
			
			wxUnionMerchantService.update(WxGlobal.DATASOURCE_WEIXIN,tenantId, wxUnionMerchant);
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信联盟商户信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信联盟商户时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信联盟商户时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
		return result;
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信联盟商户信息(" + ids + ")");
		try {
			String tenantId=getCurrentUser().getTenantId();
			 
			wxUnionMerchantService.delete(WxGlobal.DATASOURCE_WEIXIN,tenantId,Arrays.asList(StringUtils.split(ids, ",")));
			
			return this.ajaxJsonSuccessMessage("");
			
		} catch (ForeignKeyException ex) {
			logger.error("删除联盟商户信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信门店信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除联盟商户信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除联盟商户信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除联盟商户信息时，发生异常", ex);
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
		  
		  result=wxUnionMerchantService.getList(WxGlobal.DATASOURCE_WEIXIN,tenantId, criteria).size()==0;
	  }catch(Exception e){
		  logger.error("商户名称是否可用Ajax验证时，发生异常Exception:"+e);
		  e.printStackTrace();
	  }
	  return result;
	}

}
