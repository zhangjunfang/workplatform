package com.baoyuan.service.weixin;

import java.io.Serializable;
import java.util.List;

import com.baoyuan.bean.Pager;
import com.baoyuan.condition.Criteria;
import com.baoyuan.entity.weixin.BaseEntity;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ForeignKeyException;
import com.baoyuan.exceptions.ServiceException;

/**
 * Service接口 - Service接口基类
 */
public interface BaseService<T extends BaseEntity, PK extends Serializable> {

	/**
	 * 根据主键获取唯一的实体对象.
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param id
	 *            主键
	 * @return
	 * @throws ServiceException
	 */
	T get(String dataSourceId, String tenantId, PK id)
			throws DataSourceDescriptorException, ServiceException;
	
	/**
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	T find(String dataSourceId, String tenantId, String property, Object value)
			throws DataSourceDescriptorException, ServiceException;

	/**
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	List<T> findList(String dataSourceId, String tenantId, String property,
			Object value) throws DataSourceDescriptorException,
			ServiceException;

	/**
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param ids
	 *            主键集合
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	List<T> findListByIds(String dataSourceId, String tenantId, List<PK> ids)
			throws DataSourceDescriptorException, ServiceException;

	/**
	 * 获取全部数据
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	List<T> getAll(String dataSourceId, String tenantId)
			throws DataSourceDescriptorException, ServiceException;

	/**
	 * 获取分页数据
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param criteria
	 *            查询条件
	 * @param pager
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	Pager getPager(String dataSourceId, String tenantId, Criteria criteria,
			Pager pager) throws DataSourceDescriptorException, ServiceException;

	/**
	 * 获取列表数据
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param criteria
	 *            查询条件
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	List<T> getList(String dataSourceId, String tenantId, Criteria criteria)
			throws DataSourceDescriptorException, ServiceException;

	/**
	 * 获取当前总记录数
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	Long getTotalCount(String dataSourceId, String tenantId)
			throws DataSourceDescriptorException, ServiceException;

	/**
	 * 根据属性和值判断记录是否存在
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	Boolean isExist(String dataSourceId, String tenantId, String property,
			Object value) throws DataSourceDescriptorException,
			ServiceException;

	/**
	 * 保存实体对象
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param entity
	 *            实体对象
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	T save(String dataSourceId, String tenantId, T entity)
			throws DataSourceDescriptorException, ServiceException;

	/**
	 * 修改对象
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param entity
	 *            实体对象
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	int update(String dataSourceId, String tenantId, T entity)
			throws DataSourceDescriptorException, ServiceException;

	/**
	 * 删除对象
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param id
	 *            对象ID
	 * @return
	 * @throws ForeignKeyException
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	int delete(String dataSourceId, String tenantId, PK id)
			throws ForeignKeyException, DataSourceDescriptorException,
			ServiceException;

	/**
	 * 批量删除对象
	 * @param dataSourceId 数据源标识
	 * @param tenantId 租户标识
	 * @param ids
	 * @return
	 * @throws ForeignKeyException
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	int[] delete(String dataSourceId, String tenantId, List<PK> ids)
			throws ForeignKeyException, DataSourceDescriptorException,
			ServiceException;
	
	/**
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param property
	 *            列名称
	 * @param value
	 *            列值
	 * @return
	 * @throws ForeignKeyException
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	int delete(String dataSourceId, String tenantId, String property,
			Object value) throws ForeignKeyException,
			DataSourceDescriptorException, ServiceException;
	

	/**
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param entity
	 *            实体对象集合
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	int[] save(String dataSourceId, String tenantId, List<T> entity)
			throws DataSourceDescriptorException, ServiceException;

	/**
	 * 
	 * @param dataSourceId
	 *            数据源标识
	 * @param tenantId
	 *            租户标识
	 * @param entity
	 *            实体对象集合
	 * @return
	 * @throws DataSourceDescriptorException
	 * @throws ServiceException
	 */
	int[] update(String dataSourceId, String tenantId, List<T> entity)
			throws DataSourceDescriptorException, ServiceException;
}
