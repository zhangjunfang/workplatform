package com.baoyuan.service.weixin;

import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;

public interface WxConfigService extends BaseService<WxConfig, String> {
	
	WxConfig getWxConfig(String dataSourceId, String id) throws DataSourceDescriptorException, ServiceException;
}
