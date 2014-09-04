package com.baoyuan.service.weixin;

import java.util.List;

import com.baoyuan.entity.weixin.WxFollowerGroup;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;

public interface WxFollowerGroupService extends BaseService<WxFollowerGroup, String> {

	boolean save(String dataSourceId,String tenantId,String wid, List<WxFollowerGroup> entity) throws DataSourceDescriptorException, ServiceException;
	
}
