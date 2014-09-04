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
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.entity.weixin.WxUnionMerchantType;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxUnionMerchantTypeService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "WxUnionMerchantTypeController")
@RequestMapping(WxGlobal.WEIXIN_ADMIN_PATH + "/merchanttype")
public class WxUnionMerchantTypeController extends BaseAdminController {
	
	@Resource
	private WxUnionMerchantTypeService WxMerchantTypeService;
	@Resource
	private WxConfigService wxConfigService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list(Model model) {
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
	return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_merchantType_list";
	}

	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(String wid, Pager pager) {
		setLogInfo("获取联盟商户类别信息数据页面");

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

		pager = WxMerchantTypeService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}

	@RequestMapping(value = "/add/{tenantId}/{wid}")
	public String add(@PathVariable String tenantId, @PathVariable String wid,
			Model model) {
		setLogInfo("添加联盟商户类别页面");
		model.addAttribute("tenantId", tenantId);
		model.addAttribute("wid", wid);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_merchantType_input";
	}

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑联盟商户类别信息(" + id + ")");
		WxUnionMerchantType type=WxMerchantTypeService.get(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(),id);
		model.addAttribute("id", id);
		model.addAttribute("type",type);
		return WxGlobal.WEIXIN_ADMIN_TEMPLATE + "/wx_merchantType_input";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(WxUnionMerchantType type, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("新添加联盟商户类别信息");
			
			type.setTenantId(getCurrentUser().getTenantId());
			type.setCreateUser(getCurrentUser().getUserName());
			type.setCreateDate(new Date());
			type.setName(urlDecoder(type.getName()));
			type.setMemo(urlDecoder(type.getMemo()));
			
			MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpservletRequest.getFile("imgFile");	
			logger.error("multipartFile--------"+multipartFile);
			if(multipartFile.getSize() != 0)
			{
				if(multipartFile.getSize() >0 && multipartFile.getSize()<= 102400){
					String filePath = saveAttachFile(multipartFile);
					if(StringUtils.isNotEmpty(filePath)){
						type.setImgPath(filePath);
					}
				}
				else{
					  result.put(STATUS, ERROR);
					  result.put(MESSAGE, "图片不能大于100KB！");
				}
			}
			
			
			// 保存
			WxMerchantTypeService.save(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), type);

			  result.put(STATUS, SUCCESS);
			  result.put(MESSAGE, ""); 

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存联盟商户类别信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存联盟商户类别时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存联盟商户类别时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}

	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, WxUnionMerchantType type,HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			setLogInfo("保存修改后的联盟商户类别信息(" + id + ")");
			
			type.setModifyUser(getCurrentUser().getUserName());
			type.setModifyDate(new Date());
			type.setName(urlDecoder(type.getName()));
			type.setMemo(urlDecoder(type.getMemo()));
			String oldFile = null;
			
			MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;
			MultipartFile multipartFile = multipartHttpservletRequest.getFile("imgFile");	
			logger.error("multipartFile--------"+multipartFile);
			
			WxUnionMerchantType oldxUnionMerchantType = WxMerchantTypeService.get(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), id);
			
			if(multipartFile.getSize() !=0 )
			{
			  if(multipartFile.getSize() >0 && multipartFile.getSize()<= 102400){
				 String filePath = saveAttachFile(multipartFile);
				  if(StringUtils.isNotEmpty(filePath)){
					  type.setImgPath(filePath);
				 }
				oldFile = oldxUnionMerchantType.getImgPath();

				
			   }else{
				  result.put(STATUS, ERROR);
				  result.put(MESSAGE, "图片不能大于100KB！");
			   }
			}
			

			WxMerchantTypeService.update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), type);
			
			if (StringUtils.isNotEmpty(oldFile)) {
				boolean del = deleteFile(oldFile);// 删除原附件
				logger.info("删除附件[" + oldFile + "]" + del);
			}
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
		} catch (DataSourceDescriptorException ex) {
			logger.error("修改联盟商户类别信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改联盟商户类别时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改联盟商户类别时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
		
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除联盟商户类别信息(" + ids + ")");
		try {
			String tenantId=getCurrentUser().getTenantId();
			
			List<WxUnionMerchantType> allType = WxMerchantTypeService.findListByIds(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(),Arrays.asList(StringUtils.split(ids, ",")));

			for (WxUnionMerchantType fb : allType) {
				if (fb.getImgPath() != null && !fb.getImgPath().equals("")) {
					boolean del = deleteFile(fb.getImgPath());// 删除原附件
					logger.info("删除附件[" + fb.getImgPath() + "]" + del);
				}
			}
			
			WxMerchantTypeService.delete(WxGlobal.DATASOURCE_WEIXIN,tenantId,Arrays.asList(StringUtils.split(ids, ",")));
			
			
			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除联盟商户类别信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("联盟商户类别信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除联盟商户类别信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除联盟商户类别信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除联盟商户类别信息时，发生异常", ex);
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
			
			WxUnionMerchantType wxUnionMerchantType = WxMerchantTypeService.get(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser().getTenantId(),id);
			wxUnionMerchantType.setImgPath("");
			wxUnionMerchantType.setModifyUser(getCurrentUser().getUserName());
			wxUnionMerchantType.setModifyDate(new Date());
			WxMerchantTypeService.update(WxGlobal.DATASOURCE_WEIXIN,getCurrentUser().getTenantId(), wxUnionMerchantType);
			
			result.put(STATUS, SUCCESS);
			result.put(MESSAGE, "");
			
		} catch (Exception ex) {
			logger.error("删除微信门店信息图片时，发生异常", ex);
			result.put(STATUS, ERROR);
			result.put(MESSAGE, "删除附件失败!");
		}
		return StringUtils.equalsIgnoreCase(result.get(STATUS).toString(), SUCCESS)+","+result.get(MESSAGE);
	}
	
	/**
	 * 名称唯一验证
	 */
	@RequestMapping(value = "/checkName", method = { RequestMethod.POST })
	public @ResponseBody
	Object checkName(String property, String oldValue, String wid) {
		boolean result = false;
		String value = getParameter(property);
		try {
			if (StringUtils.isNotEmpty(oldValue)
					&& StringUtils.equalsIgnoreCase(oldValue, value)) {
				return true;
			}
			String tenantId = getCurrentUser().getTenantId();
			Criteria criteria = new Criteria();
			criteria.add(Restrictions.eq(property, value));
			criteria.add(Restrictions.eq("wid", wid));

			result = WxMerchantTypeService.getList(WxGlobal.DATASOURCE_WEIXIN,
					tenantId, criteria).size() == 0;
		} catch (Exception e) {
			logger.error("商户类别名称是否可用Ajax验证时，发生异常Exception:" + e);
			e.printStackTrace();
		}
		return result;
	}
}
