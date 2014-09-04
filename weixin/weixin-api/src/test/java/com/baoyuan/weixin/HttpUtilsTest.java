package com.baoyuan.weixin;

import com.baoyuan.weixin.exception.HttpException;
import com.baoyuan.weixin.http.HttpUtils;

public class HttpUtilsTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String uri= "https://api.weixin.qq.com/cgi-bin/user/info?access_token=GelDTsuckQV8dPwRSIY5LdGKWimxdy2-wtgYtKBkQ4xg8UW0z4glmNUNNFzdkw1pvZiN3OfC512m9VZVpsOC3rcrmUhkM1u24VDg69nbYU5r1qspeiJX-TeXNYycUkc6yliJka6ulCMRrqwdVnHEZQ&openid=o2bstt6Jn72EKNUaZr0vtvfJk6cs&lang=zh_CN";
		
		try {
			System.out.println(HttpUtils.get(uri));
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
