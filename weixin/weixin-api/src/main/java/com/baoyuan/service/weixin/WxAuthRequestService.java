package com.baoyuan.service.weixin;

import com.baoyuan.entity.weixin.WxAuthRequest;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;

public interface WxAuthRequestService extends BaseService<WxAuthRequest, String> {

	WxAuthRequest getWxAuthRequest(String dataSourceId,String wid) throws DataSourceDescriptorException, ServiceException;
	
}
