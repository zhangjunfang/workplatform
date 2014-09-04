package com.baoyuan.controller.open.weixin;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baoyuan.constant.weixin.WxGlobal;
import com.baoyuan.entity.weixin.WxAuthRequest;
import com.baoyuan.entity.weixin.WxConfig;
import com.baoyuan.entity.weixin.WxMessageRequest;
import com.baoyuan.exceptions.DataSourceDescriptorException;
import com.baoyuan.exceptions.ServiceException;
import com.baoyuan.groovy.DynamicGroovy;
import com.baoyuan.service.weixin.WxAuthRequestService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxMessageRequestService;
import com.baoyuan.weixin.bean.EventType;
import com.baoyuan.weixin.bean.Message;
import com.baoyuan.weixin.bean.MessageType;

/**
 * 微信公众平台接口
 * 
 * @author 张莹
 */
@Controller(WxGlobal.SIGN + WxGlobal.OPEN + "WxController")
@RequestMapping(WxGlobal.OPEN_PATH + "/wx")
public class WxController extends BaseOpenController {

	@Resource(name = WxGlobal.SIGN + "WxAuthRequestService")
	private WxAuthRequestService wxAuthRequestService;

	@Resource(name = WxGlobal.SIGN + "WxConfigService")
	private WxConfigService wxConfigService;

	@Resource(name = WxGlobal.SIGN + "WxMessageRequestService")
	private WxMessageRequestService wxMessageRequestService;
	
	@Resource
	private DynamicGroovy dynamicGroovy;
	
	@RequestMapping(value = "/{wid}", method = { RequestMethod.GET })
	public @ResponseBody
	String validate(@PathVariable String wid,
			@RequestParam(value = "signature") String signature,
			@RequestParam(value = "timestamp") String timestamp,
			@RequestParam(value = "nonce") String nonce,
			@RequestParam(value = "echostr") String echostr) {

		if (StringUtils.isEmpty(wid)) {
			return "";
		}

		try {
			WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.SIGN, wid);
			if (wxConfig == null) {
				return "";
			}

			// 开发者认证
			if (this.validate(signature, timestamp, nonce, wxConfig.getToken())) {

				WxAuthRequest wxAuthRequest = wxAuthRequestService
						.getWxAuthRequest(WxGlobal.SIGN, wid);

				if (wxAuthRequest != null) {/* 已经认证，更新数据 */
					wxAuthRequest.setSignature(signature);
					wxAuthRequest.setTimestamp(timestamp);
					wxAuthRequest.setNonce(nonce);
					wxAuthRequest.setEchostr(echostr);

					wxAuthRequest.setModifyUser(DEFAULT_CREATE_USER);
					wxAuthRequest.setModifyDate(new Date());

					wxAuthRequestService.update(WxGlobal.SIGN,
							wxConfig.getTenantId(), wxAuthRequest);
				} else {/* 未认证，新增数据 */
					wxAuthRequest = new WxAuthRequest();

					wxAuthRequest.setWid(wid);
					wxAuthRequest.setTenantId(wxConfig.getTenantId());

					wxAuthRequest.setSignature(signature);
					wxAuthRequest.setTimestamp(timestamp);
					wxAuthRequest.setNonce(nonce);
					wxAuthRequest.setEchostr(echostr);

					wxAuthRequest.setCreateUser(DEFAULT_CREATE_USER);
					wxAuthRequest.setCreateDate(new Date());

					wxAuthRequestService.save(WxGlobal.SIGN,
							wxConfig.getTenantId(), wxAuthRequest);
				}

				return echostr;
			}

		} catch (DataSourceDescriptorException ex) {
			logger.error("校验微信配置信息时，发生(DataSourceDescriptorException)异常", ex);
		} catch (ServiceException ex) {
			logger.error("校验微信配置时，发生(ServiceException)异常", ex);
		} catch (Exception ex) {
			logger.error("校验微信配置时，发生异常", ex);
		}

		return "";
	}

	@RequestMapping(value = "/{wid}", method = RequestMethod.POST)
	public @ResponseBody
	String post(@PathVariable String wid, @RequestBody String requestBody) {

		Message message = this.processMessage(wid, requestBody);
		
		Map<String,Object> args = new HashMap<String, Object>();
		args.put("wid", wid);
		args.put("message", message);
		
		String basePath = this.getSession().getServletContext().getRealPath("/WEB-INF/groovy/")+File.separator;
			
		logger.info("args="+args.toString());
		
		Object result = dynamicGroovy.invokeScriptMethod(basePath+"DefaultHandleRequest.groovy", "handleRequest", args);
		
		logger.info("result="+result);
		
		return result.toString();
	}

	/**
	 * 存储上行消息
	 * @param wid
	 * @param requestBody
	 * @return
	 */
	protected Message processMessage(String wid,String requestBody){
		
		Message request = Message.parse(requestBody);
		MessageType type = request.getMessageType();

		WxMessageRequest wxMsgReq = new WxMessageRequest();
		wxMsgReq.setMsgType(type.toString());
		wxMsgReq.setCreateTime(request.getCreateTime());
		wxMsgReq.setFromUser(request.getFromUser());
		wxMsgReq.setToUser(request.getToUser());

		wxMsgReq.setCreateUser(DEFAULT_CREATE_USER);
		wxMsgReq.setCreateDate(new Date());

		switch (type) {
			case TEXT:
				wxMsgReq.setMsgId(request.getMsgId().toString());
				wxMsgReq.setContent(request.getContent());
				break;
			case EVENT:
				EventType eventType = request.getEventType();
				wxMsgReq.setEventType(eventType.toString());
				if (eventType == EventType.SUBSCRIBE) {
					wxMsgReq.setEventKey(request.getEventKey());
					wxMsgReq.setTicket(request.getTicket());
				}
				if (eventType == EventType.UNSUBSCRIBE) {
					// Do nothing
				}
				if (eventType == EventType.SCAN) {
					wxMsgReq.setEventKey(request.getEventKey());
					wxMsgReq.setTicket(request.getTicket());
				}
				if (eventType == EventType.LOCATION) {
					wxMsgReq.setLat(request.getLat());
					wxMsgReq.setLon(request.getLon());
				}
				if (eventType == EventType.CLICK) {
					wxMsgReq.setEventKey(request.getEventKey());
				}
				break;
			case IMAGE:
				wxMsgReq.setMsgId(request.getMsgId().toString());
				wxMsgReq.setMediaId(request.getMediaId());
				wxMsgReq.setPicUrl(request.getPicUrl());
				break;
			case LINK:
				wxMsgReq.setMsgId(request.getMsgId().toString());
				wxMsgReq.setTitle(request.getTitle());
				wxMsgReq.setUrl(request.getUrl());
				wxMsgReq.setDescription(request.getDescription());
				break;
			case LOCATION:
				wxMsgReq.setMsgId(request.getMsgId().toString());
				wxMsgReq.setLat(request.getLat());
				wxMsgReq.setLon(request.getLon());
				wxMsgReq.setLabel(request.getLabel());
				wxMsgReq.setScale(request.getScale());
				break;
			case VIDEO:
				wxMsgReq.setMsgId(request.getMsgId().toString());
				wxMsgReq.setMediaId(request.getMediaId());
				wxMsgReq.setThumbMediaId(request.getThumbMediaId());
				break;
			case VOICE:
				wxMsgReq.setMsgId(request.getMsgId().toString());
				wxMsgReq.setMediaId(request.getMediaId());
				wxMsgReq.setVoiceType(request.getVoiceType().toString());
				wxMsgReq.setRecognition(request.getRecognition());
				break;
			case MUSIC:
				break;
			case NEWS:
				break;
		}
		
		WxConfig wxConfig = wxConfigService.getWxConfig(WxGlobal.SIGN, wid);
		wxMsgReq.setWid(wid);
		wxMsgReq.setTenantId(wxConfig.getTenantId());
		wxMessageRequestService.save(WxGlobal.SIGN,wxConfig.getTenantId(), wxMsgReq);

		return request;
	}

	/**
	 * 开发者认证
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param token
	 * @return
	 */
	private boolean validate(String signature, String timestamp, String nonce,
			String token) {
		String[] chars = new String[] { token, timestamp, nonce, };
		Arrays.sort(chars);
		String sha1 = DigestUtils.shaHex(StringUtils.join(chars));
		if (sha1.equals(signature)) {
			return true;
		}

		return false;
	}
}
