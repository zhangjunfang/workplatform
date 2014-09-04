import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.baoyuan.service.weixin.WxCardBindService;
import com.baoyuan.service.weixin.WxConfigService;
import com.baoyuan.service.weixin.WxMessageRequestService;
import com.baoyuan.util.SpringUtils;
import com.baoyuan.weixin.bean.Article;
import com.baoyuan.weixin.bean.EventType;
import com.baoyuan.weixin.bean.Message;
import com.baoyuan.weixin.bean.MessageType;
import com.baoyuan.weixin.utils.DesUtils;
import com.baoyuan.entity.weixin.WxCardBind;
import com.baoyuan.http.DefaultOpenApi;


class DefaultHandleRequest {

	private WxMessageRequestService wxMessageRequestService;

	private WxConfigService wxConfigService;

	private WxCardBindService wxCardBindService;
	
	private DefaultOpenApi defaultOpenApi;
	
	WxMessageRequestService getWxMessageRequestService(){
		if(wxMessageRequestService==null){
			wxMessageRequestService = SpringUtils.getApplicationContext().getBean(WxMessageRequestService.class);
		}

		return wxMessageRequestService;
	}

	WxConfigService getWxConfigService(){
		if(wxConfigService==null){
			wxConfigService = SpringUtils.getApplicationContext().getBean(WxConfigService.class);
		}

		return wxConfigService;
	}

	WxCardBindService getWxCardBindService(){
		if(wxCardBindService==null){
			wxCardBindService = SpringUtils.getApplicationContext().getBean(WxCardBindService.class);
		}

		return wxCardBindService;
	}
	
	DefaultOpenApi getDefaultOpenApi(){
		if(defaultOpenApi==null){
			defaultOpenApi = SpringUtils.getApplicationContext().getBean(DefaultOpenApi.class);
		}

		return defaultOpenApi;
	}
	
	 
	String handleRequest(Map<String,Object> args) {
		String wid = args.get("wid");
		Message request = args.get("message");
		println(request.toXML());
		
		println(wid);
		MessageType type = request.getMessageType();
		
		Message response = null;
		switch(type){
			/*case MessageType.TEXT:
				response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，这是测试消息");
				break;*/
			case MessageType.EVENT:
				String context="<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb901d155b2df7485&redirect_uri=http://zzbaoyuan.f3322.org/weixin-web/web/wx/oauth/authorization/187363843322675200&response_type=code&scope=snsapi_userinfo#wechat_redirect'>OAuth认证</a>";
				response = createTextMessage(request.getToUser(),request.getFromUser(),context);

				EventType eventType = request.getEventType();
				if (eventType == EventType.SUBSCRIBE) {
					response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，欢迎关注喜盈门微信号！");
				}
				if (eventType == EventType.UNSUBSCRIBE) {
					response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，退订消息");
				}
				if (eventType == EventType.SCAN) {
					response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，扫描消息");
				}
				if (eventType == EventType.LOCATION) {
					response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，位置消息");
				}
				if (eventType == EventType.CLICK) {
					WxCardBind cardBind = getWxCardBindService().find("weixin", "165617974705651712", "fromUserName", request.getFromUser());
					
					if(cardBind==null){
						String template = "您好，为了保证您的用卡安全,请先进行身份验证.身份验证成功即刻享受:\n\n1.秒查账单、积分、余额\n2.消费账单、消费通知提醒\n3.卡密码修改、挂失、解挂\n-----------------\n  [OK]<a href='http://116.255.227.139/weixin/web/wx/card/login/234758759493140480?user="+request.getFromUser()+"&card=&passwd='>点击立即绑定卡号</a>.";
						response =  createTextMessage(request.getToUser(),request.getFromUser(),template);
						
					}else {
					 
				     if ("M_VIP_01".equals(request.getEventKey())) {
					     //解除绑定
						String template ="您已成功绑定卡号"+cardBind.getCardNo()+",即刻享受:\n\n1.秒查账单、积分、余额\n2.消费账单、消费通知提醒\n3.卡密码修改、挂失、解挂\n-----------------\n [OK]<a href='http://116.255.227.139/weixin/web/wx/card/login/234758759493140480?user="+request.getFromUser()+"&card=&passwd='>点击立即解除绑定</a>.";
							
						response =  createTextMessage(request.getToUser(),request.getFromUser(),template);
						
					}else if("M_VIP_02".equals(request.getEventKey())){
					    //余额积分
				     	String uniqueCode = cardBind.getCardNo().substring(6, 11);
					    String token = getDefaultOpenApi().getToken(uniqueCode);
						
					    Map<String, Object> history =getDefaultOpenApi().cardYueJf(cardBind.getCardNo(),DesUtils.decrypt(cardBind.getCardPasswd()), token);
						
				    	String template="卡号:"+cardBind.getCardNo()+"\n-----------------\n总余额:"+history.get("totalMoney")+"元\n可用余额:"+history.get("consumeMoney")+"元\n总积分:"+history.get("totalJifen")+"\n可用积分:"+history.get("consumeJifen")+"\n-----------------\n单笔最大消费:"+history.get("maxMoney")+"元";
					   
					   response =  createTextMessage(request.getToUser(),request.getFromUser(),template);
					}
					else if("M_VIP_03".equals(request.getEventKey())){
						//消费历史
					  String uniqueCode = cardBind.getCardNo().substring(6, 11);
					  String token = getDefaultOpenApi().getToken(uniqueCode);
					  Map<String, Object> data = getDefaultOpenApi().consumeTj(cardBind.getCardNo(), token);
						
					  String template="尊敬的用户,你好!贵卡"+cardBind.getCardNo()+"最近7天的消费历史如下:\n-----------------\n消费总次数:"+data.get("totalCount")+"\n消费总金额:"+data.get("sumMoney")+"\n-----------------\n [OK]<a href='http://116.255.227.139/weixin/web/wx/card/history/234758759493140480?user="+request.getFromUser()+"&card="+cardBind.getCardNo()+"&pageSize="+data.get("totalCount")+"'>点击查看消费明细</a>";
					 
					  response =  createTextMessage(request.getToUser(),request.getFromUser(),template);
					   
					}else if("M_VIP_04".equals(request.getEventKey())) {
					   //个人资料
					  String uniqueCode = cardBind.getCardNo().substring(6, 11);
				   	  String token = getDefaultOpenApi().getToken(uniqueCode);
					  Map<String, Object> info = getDefaultOpenApi().cardDetail(cardBind.getCardNo(),token);
					
					  String template="卡号:"+cardBind.getCardNo()+"\n卡类型:"+info.get("cardType")+"\n卡等级:"+info.get("cardLevel")+"\n卡状态:"+info.get("cardStatus")+"\n-----------------\n姓名:" +info.get("name")+"\n性别:"+info.get("sex")+"\n联系电话:"+info.get("phone")+"\n联系地址:"+info.get("address")+"\n民族:"+info.get("nation")+"\n-----------------\n开户门店:"+info.get("shopName")+"\n开户日期:"+info.get("createDate");
					  
					  response =  createTextMessage(request.getToUser(),request.getFromUser(),template);
						
					}else if("M_VIP_05".equals(request.getEventKey())){
						//修改密码
						String template="[玫瑰]温馨提醒:本操作将修改您的持卡密码,请牢记您修改后的密码,以免给您带来不必要的麻烦![愉快]\n-----------------\n  [OK]<a href='http://116.255.227.139/weixin/web/wx/card/change/234758759493140480?user="+request.getFromUser()+"'>点击立即修改密码</a>";
						
						response =  createTextMessage(request.getToUser(),request.getFromUser(),template);
				   }else if("K_SER_01".equals(request.getEventKey())){
					 //卡挂失 
					 String template="[玫瑰]温馨提醒:本操作将挂失您绑定的"+cardBind.getCardNo()+"的卡,卡挂失后将无法进行查询、消费等业务,以免给您带来不必要的损失![愉快]\n-----------------\n  [OK]<a href='http://116.255.227.139/weixin/web/wx/card/loss/234758759493140480?user="+request.getFromUser()+"'>点击立即挂失</a>";
					
					 response =  createTextMessage(request.getToUser(),request.getFromUser(),template);
				  }else if("K_SER_02".equals(request.getEventKey())){  
				   //卡解挂 
				    String template="[玫瑰]温馨提醒:本操作将解挂您绑定的"+cardBind.getCardNo()+"的卡,解挂后即刻享受:\n\n1.秒查账单、积分、余额\n2.消费账单、消费通知提醒\n3.卡密码修改、挂失、解挂\n-----------------\n  [OK]<a href='http://116.255.227.139/weixin/web/wx/card/found/234758759493140480?user="+request.getFromUser()+"'>点击立即解挂</a>.";
				     
					response=createTextMessage(request.getToUser(),request.getFromUser(),template);
				  }else if("K_SER_03".equals(request.getEventKey())){
				    //在线充值
				    String template="[撇嘴]抱歉,在线充值功能需要审核接入,请致电400-602-3332索取接入资料,感谢您的关注!";
					
					response=createTextMessage(request.getToUser(),request.getFromUser(),template);
				  }
				}
				}		
				break;
			/*case MessageType.IMAGE:
				response = createArticleMessage(request.getToUser(),request.getFromUser());
				break;
			case MessageType.LINK:
				response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，这是测试消息");
				break;
			case MessageType.LOCATION:
				response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，这是测试消息");
				break;
			case MessageType.VIDEO:
				response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，这是测试消息");
				break;
			case MessageType.VOICE:
				response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，这是测试消息");
				break;
			case MessageType.MUSIC:
				response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，这是测试消息");
				break;
			case MessageType.NEWS:
				response = createTextMessage(request.getToUser(),request.getFromUser(),"你好，这是测试消息");
				break;
			default:
				String context="<a href='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb901d155b2df7485&redirect_uri=http://zzbaoyuan.f3322.org/weixin-web/web/wx/oauth/authorization/187363843322675200&response_type=code&scope=snsapi_userinfo#wechat_redirect'>OAuth认证</a>";
				
				response = createTextMessage(request.getToUser(),request.getFromUser(),context);
				break;
				*/
		}

		if(response == null){
			return null;
		}
		return response.toXML();
	}
	
	Message createTextMessage(String toUser,String fromUser,String context){
		Message result = new Message();
		
		result.setFromUser(toUser);
		result.setToUser(fromUser);
		result.setMessageType(MessageType.TEXT);
		result.setCreateTime(new Date());
		result.setContent(context);
		
		return result;
	}
	
	Message createArticleMessage(String toUser,String fromUser){
		Message result = new Message();
		
		result.setFromUser(toUser);
		result.setToUser(fromUser);
		result.setMessageType(MessageType.NEWS);
		result.setCreateTime(new Date());
		
		List<Article> articles = new ArrayList<Article>();
		
		Article item = new Article();
		item.setTitle("这里是Title");
		item.setPicUrl("http://www.shop8188.com/shop/template/web/img/graph.png")
		item.setUrl("http://www.wp99.cn/vipeng/");
		item.setDescription("备注说明消息");
		articles.add(item);
		
		item = new Article();
		item.setTitle("这里是Title");
		item.setPicUrl("http://www.shop8188.com/shop/template/web/img/graph.png")
		item.setUrl("http://www.wp99.cn/vipeng/");
		item.setDescription("备注说明消息");
		
		articles.add(item);
		
		item = new Article();
		item.setTitle("这里是Title");
		item.setPicUrl("http://www.shop8188.com/shop/template/web/img/graph.png")
		item.setUrl("http://www.wp99.cn/vipeng/");
		item.setDescription("备注说明消息");
		
		articles.add(item);
		
		item = new Article();
		item.setTitle("这里是Title");
		item.setPicUrl("http://www.shop8188.com/shop/template/web/img/graph.png")
		item.setUrl("http://www.wp99.cn/vipeng/");
		item.setDescription("备注说明消息");
		
		articles.add(item);
		
		item = new Article();
		item.setTitle("这里是Title");
		item.setPicUrl("http://www.shop8188.com/shop/template/web/img/graph.png")
		item.setUrl("http://www.wp99.cn/vipeng/");
		item.setDescription("备注说明消息");
		
		articles.add(item);
		
		result.setArticles(articles)
		
		println(result.toXML());
		
		return result;
	}
}
