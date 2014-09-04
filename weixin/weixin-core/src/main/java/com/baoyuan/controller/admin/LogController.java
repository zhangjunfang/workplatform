package com.baoyuan.controller.admin;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baoyuan.bean.Pager;
import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.LogService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN+"LogController")
@RequestMapping(WxGlobal.ADMIN_PATH + "/log")
public class LogController extends BaseAdminController {

	@Resource
	private LogService logService;
	
	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list() {
		setLogInfo("日志配置列表页面");
		return "admin/log_list";
	}

	@RequestMapping(value = "/ajax")
	public @ResponseBody
	Object ajax(Pager pager) {
		setLogInfo("获取日志配置信息数据页面");
		
		Criteria criteria = new Criteria();

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
		
		pager = logService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除日志配置信息(" + ids + ")");
		try {
			logService.delete(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), Arrays.asList(StringUtils.split(ids,",")));
			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除日志配置信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("日志信息在用,无法删除");
		} catch (DataSourceDescriptorException ex) {
			logger.error("删除日志配置信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("删除日志配置信息时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("删除日志配置信息时，发生异常", ex);
			return this.ajaxJsonErrorMessage("删除失败,请联系管理员");
		}
	}
}
