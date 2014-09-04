package com.baoyuan.controller.admin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baoyuan.bean.Pager;
import com.baoyuan.condition.Criteria;
import com.baoyuan.condition.Restrictions;
import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.weixin.LogConfig;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.service.weixin.LogConfigService;

@Controller(WxGlobal.SIGN + WxGlobal.ADMIN + "LogConfigController")
@RequestMapping(WxGlobal.ADMIN_PATH + "/logconfig")
public class LogConfigController extends BaseAdminController {

	@Resource
	private LogConfigService logConfigService;

	@RequestMapping(value = "/list", method = { RequestMethod.GET })
	public String list() {
		setLogInfo("日志配置列表页面");
		return "admin/logconfig_list";
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

		pager = logConfigService.getPager(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), criteria, pager);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("Rows", pager.getList());
		result.put("Total", pager.getTotalCount());

		return result;
	}

	@RequestMapping(value = "/add")
	public String add(Model model) {
		setLogInfo("添加日志配置页面");
		return "admin/logconfig_input";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST })
	public @ResponseBody
	Object save(LogConfig logConfig, HttpServletRequest request) {
		try {
			setLogInfo("新添加日志配置信息");

			logConfig.setTenantId(getCurrentUser().getTenantId());

			logConfig.setCreateUser(getCurrentUser().getUserName());
			logConfig.setCreateDate(new Date());

			logConfigService.save(WxGlobal.DATASOURCE_WEIXIN, getCurrentUser()
					.getTenantId(), logConfig);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("保存日志配置信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("保存日志配置时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("保存日志配置时，发生异常", ex);
			return this.ajaxJsonErrorMessage("保存失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		setLogInfo("编辑日志配置信息(" + id + ")");
		LogConfig logConfig = logConfigService.get(WxGlobal.DATASOURCE_WEIXIN,
				getCurrentUser().getTenantId(), id);
		model.addAttribute("logConfig", logConfig);
		model.addAttribute("id", id);
		return "admin/logconfig_input";
	}

	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody
	Object update(@PathVariable("id") String id, LogConfig logConfig) {
		try {
			setLogInfo("保存修改后的日志配置信息(" + id + ")");
			logConfig.setModifyUser(getCurrentUser().getUserName());
			logConfig.setModifyDate(new Date());
			logConfigService.update(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(), logConfig);

			return this.ajaxJsonSuccessMessage("");

		} catch (DataSourceDescriptorException ex) {
			logger.error("修改日志配置信息时，发生(DataSourceDescriptorException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (ServiceException ex) {
			logger.error("修改日志配置时，发生(ServiceException)异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		} catch (Exception ex) {
			logger.error("修改日志配置时，发生异常", ex);
			return this.ajaxJsonErrorMessage("修改失败,请联系管理员");
		}
	}

	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
	public @ResponseBody
	Object delete(String ids, HttpServletRequest request) {
		setLogInfo("删除日志配置信息(" + ids + ")");
		try {
			logConfigService.delete(WxGlobal.DATASOURCE_WEIXIN,
					getCurrentUser().getTenantId(),
					Arrays.asList(StringUtils.split(ids, ",")));
			return this.ajaxJsonSuccessMessage("");
		} catch (ForeignKeyException ex) {
			logger.error("删除日志配置信息时，发生(ForeignKeyException)异常", ex);
			return this.ajaxJsonErrorMessage("日志配置信息在用,无法删除");
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

	@RequestMapping(value = "/controllers", method = { RequestMethod.POST })
	public @ResponseBody
	Object controllers(HttpServletRequest request, HttpServletResponse response) {

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
				false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", "");
		map.put("text", "请选择...");
		result.add(map);

		for (BeanDefinition beanDefinition : scanner
				.findCandidateComponents("com.baoyuan.controller")) {
			map = new HashMap<String, Object>();
			map.put("id", beanDefinition.getBeanClassName());
			map.put("text", beanDefinition.getBeanClassName());

			result.add(map);
		}

		return result;
	}

	@RequestMapping(value = "/methods", method = { RequestMethod.POST })
	public @ResponseBody
	Object methods(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {
			String className = request.getParameter("className");
			Class<?> controllerClass = Class.forName(className);
			Method[] methods = controllerClass.getDeclaredMethods();

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("id", "");
			map.put("text", "请选择...");
			result.add(map);

			for (Method method : methods) {
				if (method.getReturnType() == String.class) {

					map = new HashMap<String, Object>();
					map.put("id", method.getName());
					map.put("text", method.getName());

					result.add(map);
				}
			}

		} catch (Exception ex) {
			logger.error("日志配置中，根据所选操作类名获取操作方法名称列表时，发生异常。Exception:" + ex);
		}

		return result;
	}
}
