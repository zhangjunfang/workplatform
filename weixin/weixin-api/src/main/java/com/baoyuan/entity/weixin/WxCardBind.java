package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN + "WxCardBind")
public class WxCardBind extends BaseEntity {
	 
	private static final long serialVersionUID = -22714970210582381L;
	
	private String wid;
	private String fromUserName;
	private String cardNo;
	private String cardPasswd;
	private String status = "1";
	private String nickName;
	private Long dateTime;
	
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardPasswd() {
		return cardPasswd;
	}
	public void setCardPasswd(String cardPasswd) {
		this.cardPasswd = cardPasswd;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getDateTime() {
		return dateTime;
	}
	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

}
