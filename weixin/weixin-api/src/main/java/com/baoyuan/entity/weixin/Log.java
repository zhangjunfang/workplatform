package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.validation.annotation.Size;


/**
 * 表名称：日志表
 * 表代码：wx_log
 * 表备注：微信系统日志表
*/
@Alias(WxGlobal.SIGN+"Log")
public class Log extends BaseEntity {

	private static final long serialVersionUID = -1549779668484718434L;

	/*操作类名*/
	private String className;

	/*操作方法名*/
	private String methodName;

	/*操作事件*/
	private String action;

	/*操作名称*/
	private String operation;

	/*访问IP*/
	private String ip;

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
	* 字段名称 :操作方法名
	* 数据类型 :varchar(32)
	* 是否主键 :false
	* 是否必填 :true
	*/
	@Size(max=32)
	public String getMethodName() {
		return methodName;
	}

	/**
	* 字段名称 :操作方法名
	* 数据类型 :varchar(32)
	* 是否主键 :false
	* 是否必填 :true
	*/
	public void setMethodName(String methodName) {
		 this.methodName = methodName;
    }

	/**
	* 字段名称 :操作事件
	* 数据类型 :varchar(128)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=64)
	public String getAction() {
		return action;
	}

	/**
	* 字段名称 :操作事件
	* 数据类型 :varchar(128)
	* 是否主键 :false
	* 是否必填 :false
	*/
	public void setAction(String action) {
		 this.action = action;
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
	* 字段名称 :访问IP
	* 数据类型 :varchar(32)
	* 是否主键 :false
	* 是否必填 :false
	*/
	@Size(max=32)
	public String getIp() {
		return ip;
	}

	/**
	* 字段名称 :访问IP
	* 数据类型 :varchar(32)
	* 是否主键 :false
	* 是否必填 :false
	*/
	public void setIp(String ip) {
		 this.ip = ip;
    }

}