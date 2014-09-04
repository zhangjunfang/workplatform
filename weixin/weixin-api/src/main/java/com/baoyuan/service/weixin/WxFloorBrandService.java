package com.baoyuan.service.weixin;

import java.util.List;

import com.baoyuan.entity.weixin.WxFloorBrand;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;

public interface WxFloorBrandService extends BaseService<WxFloorBrand, String> {
	
	/**
	 * 
	 * @param datasource
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param ids
	 *            主键集合
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	List<WxFloorBrand> findListByIds(String datasource, String tenantId,
			List<String> ids) throws DataSourceDescriptorException,
			ServiceException;

}
