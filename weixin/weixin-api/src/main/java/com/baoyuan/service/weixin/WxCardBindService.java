package com.baoyuan.service.weixin;

import com.baoyuan.entity.weixin.WxCardBind;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;

public interface WxCardBindService extends BaseService<WxCardBind, String> {
	WxCardBind findByFromUserNameAndCardNo(String dataSourceId,
			String tenantId, String fromUserName, String cardNo)
			throws DataSourceDescriptorException, ServiceException;

}
