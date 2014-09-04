package com.baoyuan.entity.weixin;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.baoyuan.constant.weixin.WxGlobal;

@Alias(WxGlobal.SIGN+"WxMessageRequest")
public class WxMessageRequest extends BaseEntity {

	private static final long serialVersionUID = 4499493472655904654L;

	/* 微信ID标识 */
	private String wid;
	/*消息id*/
	private String msgId;
	/*发送方帐号（一个OpenID）*/
	private String fromUser;
	/*开发者微信号*/
	private String toUser;
	/*消息创建时间*/
	private Date createTime;
	/*消息类型*/
	private String msgType;
	/*文本消息内容*/
	private String content;
	/*消息媒体id，可以调用多媒体文件下载接口拉取数据。*/
	private String mediaId;
	/*图片链接*/
	private String picUrl;
	/*语音格式*/
	private String voiceType;
	/*语音识别结果*/
	private String recognition;
	/*视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。*/
	private String thumbMediaId;
	/*地理位置经度*/
	private Double lon;
	/*地理位置纬度*/
	private Double lat;
	/*地图缩放大小*/
	private Integer scale;
	/*地理位置信息*/
	private String label;
	/*消息标题*/
	private String title;
	/*消息描述*/
	private String description;
	/*消息链接*/
	private String url;
	/*音乐链接*/
	private String musicUrl;
	/*高质量音乐链接，WIFI环境优先使用该链接播放音乐*/
	private String hqMusicUrl;
	/*事件类型*/
	private String eventType;
	/*事件KEY值*/
	private String eventKey;
	/*二维码的ticket，可用来换取二维码图片*/
	private String ticket;
	/*地理位置精度*/
	private Double precision;

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}
	
	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getFromUser() {
		return fromUser;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getVoiceType() {
		return voiceType;
	}

	public void setVoiceType(String voiceType) {
		this.voiceType = voiceType;
	}

	public String getRecognition() {
		return recognition;
	}

	public void setRecognition(String recognition) {
		this.recognition = recognition;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Double getPrecision() {
		return precision;
	}

	public void setPrecision(Double precision) {
		this.precision = precision;
	}
}
