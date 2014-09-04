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
import com.baoyuan.entity.weixin.WxBespeakOrder;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxBespeakOrderService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxShopBespeakService;
import com.baoyuan.service.weixin.WxShopService;

@Controller(WxGlobal.SIGN+WxGlobal.ADMIN+"WxBespeakOrderController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH+"/bespeakorder")
public class WxBespeakOrderController extends BaseAdminController{
	
	@Resource
	private WxConfigService wxConfigService;
	@Resource
	private WxShopService wxShopService;
	@Resource
	private WxShopBespeakService wxShopBespeakService;
	@Resource
	private WxBespeakOrderService wxBespeakOrderService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model){
		setLogInfo("微信门店预约订购列表页面");
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
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_bespeak_order_list";
	}
	
	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String shopId, Pager pager) {
		setLogInfo("获取微信门店预约订购信息数据页面");

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

		pager = wxBespeakOrderService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑微信门店预约信息(" + id + ")");
		WxBespeakOrder wxBespeakOrder = wxBespeakOrderService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("wxBespeakOrder", wxBespeakOrder);
		model.addAttribute("id", id);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE+"/wx_bespeak_order_input";
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxBespeakOrder wxBespeakOrder,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("保存修改后的微信门店预约信息(" + id + ")");
			wxBespeakOrder.setModifyUser(getCurrentUser().getUserName());
			wxBespeakOrder.setModifyDate(new Date());
			
			wxBespeakOrderService.update(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), wxBespeakOrder);

			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改微信门店预约信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改微信门店预约时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改微信门店预约时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除微信门店预约信息(" + ids + ")");
		logger.error("删除微信门店预约信息ids"+ids);
		try {
					wxBespeakOrderService.
					delete(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(),Arrays.asList(StringUtils.split(ids, ",")));
					
					return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除微信门店预约信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("微信门店信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除微信门店预约信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除微信门店预约信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除微信门店预约信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}

}
