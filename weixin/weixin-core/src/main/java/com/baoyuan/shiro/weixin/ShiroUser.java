package com.baoyuan.shiro.weixin;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser implements Serializable {

	private static final long serialVersionUID = -2547309089031681024L;

	private static Logger logger = LoggerFactory.getLogger(ShiroUser.class);

	private String id;
	private String userName;
	private String nickName;
	private String realName;

	public ShiroUser(String id, String userName, String nickName,
			String realName) {
		super();
		this.id = id;
		this.userName = userName;
		this.nickName = nickName;
		this.realName = realName;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return StringUtils.isNotEmpty(nickName) ? nickName : userName;
	}
	
	/**
	 * 重载hashCode,只计算loginName;
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(userName);
	}

	/**
	 * 重载equals,只计算username;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		return (userName == null ? other.userName == null : userName
				.equals(other.userName));
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数. 标注了 @PostConstruct 注释的方法将在类实例化后调用 标注 @PostConstruct
	 * 注释的方法都会在初始化被执行。
	 */
	@PostConstruct
	public void postConstruct() {
		logger.info("=========== postConstruct ===========");
	}

	/**
	 * 标注了 @PreDestroy 的方法将在类销毁之前调用。 标注 @PreDestroy 注释的方法都会在销毁时被执行。
	 */
	@PreDestroy
	public void preDestroy() {
		logger.info("=========== preDestroy ===========");
	}

	public String getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getRealName() {
		return realName;
	}
	
}
