package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.validation.annotation.Range;
import com.baoyuan.validation.annotation.Size;


/**
 * 表名称：日志配置表
 * 表代码：wx_logConfig
 * 表备注：微信系统日志配置表
*/
@Alias(WxGlobal.SIGN+"LogConfig")
public class LogConfig extends BaseEntity {

	private static final long serialVersionUID = 398881924869038978L;

	/*操作类名*/
	private String className;

	/*操作方法名称*/
	private String methodName;

	/*操作名称*/
	private String operation;

	/*是否启用*/
	private Integer enabled;

	/*备注说明*/
	private String description;

	/**
	* 字段名称 :操作类名
	* 数据类型 :varchar(64)
	* 是否主键 :false
	* 是否必填 :true
	*/
	@Size(max=64)
	public String getClassName() {
		return className;
	}

	/**
	* 字段名称 :操作类名
	* 数据类型 :varchar(64)
	* 是否主键 :false
	* 是否必填 :true
	*/
	public void setClassName(String className) {
		 this.className = className;
    }

	/**
	* 字段名称 :操作方法名称
	* 数据类型 :varchar(16)
	* 是否主键 :false
	* 是否必填 :true
	*/
	@Size(max=16)
	public String getMethodName() {
		return methodName;
	}

	/**
	* 字段名称 :操作方法名称
	* 数据类型 :varchar(16)
	* 是否主键 :false
	* 是否必填 :true
	*/
	public void setMethodName(String methodName) {
		 this.methodName = methodName;
    }

	/**
	* 字段名称 :操作名称
	* 数据类型 :varchar(16)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=16)
	public String getOperation() {
		return operation;
	}

	/**
	* 字段名称 :操作名称
	* 数据类型 :varchar(16)
	* 是否主键 :false
	* 是否必填 :false
	*/
	public void setOperation(String operation) {
		 this.operation = operation;
    }

	/**
	* 字段名称 :是否启用
	* 数据类型 :int
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Range(max=1)
	public Integer getEnabled() {
		return this.enabled;
	}

	/**
	* 字段名称 :是否启用
	* 数据类型 :int
	* 是否主键 :false
	* 是否必填 :false
	*/
	public void setEnabled(Integer enabled) {
		 this.enabled = enabled;
    }

	/**
	* 字段名称 :备注说明
	* 数据类型 :varchar(128)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=128)
	public String getDescription() {
		return description;
	}

	/**
	* 字段名称 :备注说明
	* 数据类型 :varchar(128)
	* 是否主键 :false
	* 是否必填 :false
	*/
	public void setDescription(String description) {
		 this.description = description;
    }

}