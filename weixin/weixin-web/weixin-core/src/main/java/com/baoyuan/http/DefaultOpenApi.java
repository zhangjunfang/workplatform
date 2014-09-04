package com.baoyuan.http;

import java.util.Map;

public interface DefaultOpenApi {

	String getToken(String uniqueCode);
	
	boolean validCard(String card,String passwd,String token);
	
	boolean validCard1(String card,String passwd,String token);
	
	Map<String,Object> cardDetail(String card,String token);
	
	Map<String,Object> cardYueJf(String card,String passwd,String token);
	
	Map<String,Object> consumeMx(String card,String token,String pageSize);
	
	Map<String,Object> consumeTj(String card,String token);
	
	boolean changePasswd(String card,String oldpasswd,String newpasswd,String token);
	
	boolean cardLoss(String card,String passwd,String token);
	
	boolean cardFound(String card,String passwd,String token);
	
	boolean saveConsume(String card,String passwd,String money,String token,String transactionNo);
	
	Map<String,Object> getNewCardNo(String card,String uniqueCode,String token);
}
