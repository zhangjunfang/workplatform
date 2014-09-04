package com.baoyuan.service.weixin;

import java.util.List;

import com.baoyuan.entity.weixin.WxFollowers;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;

public interface WxFollowersService extends BaseService<WxFollowers, String> {

	boolean save(String dataSourceId, String tenantId, String wid,
			List<WxFollowers> entity) throws DataSourceDescriptorException,
			ServiceException;

}
