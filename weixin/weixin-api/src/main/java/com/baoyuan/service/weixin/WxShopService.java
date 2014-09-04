package com.baoyuan.service.weixin;

import java.util.List;

import com.baoyuan.entity.weixin.WxShop;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;

public interface WxShopService extends BaseService<WxShop, String> {

	/**
	 * 根据ID集合获取信息
	 * 
	 * @param datasource
	 *            数据源标示
	 * @param tenantId
	 *            租户ID
	 * @param ids
	 *            Id集合
	 * @return
	 */
	List<WxShop> findListByIds(String datasource, String tenantId,
			List<String> ids) throws DataSourceDescriptorException,
			ServiceException;

}
