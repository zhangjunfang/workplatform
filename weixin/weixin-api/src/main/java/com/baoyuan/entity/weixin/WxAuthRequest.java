package com.baoyuan.entity.weixin;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

/**
 * 根据URL和Token，其中URL是开发者用来接收微信服务器数据的接口URL。Token可由开发者可以任意填写，用作生成签名
 * 
 * http://mp.weixin.qq.com/wiki/index.php?title=%E6%8E%A5%E5%85%A5%E6%8C%87%E5%8D%97
 * 
 * @author 张莹
 *
 */
@Alias(WxGlobal.SIGN+"WxAuthRequest")
public class WxAuthRequest extends BaseEntity {

	private static final long serialVersionUID = 2698254692432377865L;

	/*微信ID标识*/
	private String wid;
	
	/*微信加密签名*/
	private String signature;

	/*时间戳*/
	private String timestamp;

	/*随机数*/
	private String nonce;

	/*随机字符串*/
	private String echostr;

	/**
	 * 微信标识，用来标识不同租户的微信
	 * @return
	 */
	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	/**
	 * 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	 * @return
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 时间戳
	 * @return
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * 随机数
	 * @return
	 */
	public String getNonce() {
		return nonce;
	}

	/**
	 * 随机字符串
	 * @return
	 */
	public String getEchostr() {
		return echostr;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public void setEchostr(String echostr) {
		this.echostr = echostr;
	}
}
