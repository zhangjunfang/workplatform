package com.baoyuan.http.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baoyuan.http.DefaultOpenApi;
import com.baoyuan.util.Base64Utils;
import com.baoyuan.util.HttpClientUtils;

@Service
public class DefaultOpenApiImpl implements DefaultOpenApi {

	protected static final Logger logger = LoggerFactory
			.getLogger(DefaultOpenApiImpl.class);

	private final String DEFAUTL_PAGER_NUMBER = "1";

	private final String DEFAUTL_PAGER_SIZE = "10";

	private final String cardApiUrl = "http://116.255.227.138:80";

	public String getToken(String uniqueCode) {

		String token = null;
		try {
			String url = cardApiUrl + "/card/open/api/getToken.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("uniqueCode", uniqueCode));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				token = null;

				logger.info("get token fail ", response);

			} else {
				token = map.get("token").toString();
			}
		} catch (Exception ex) {
			logger.error("get token exception", ex);
			token = null;
		}

		return token;
	}

	public boolean validCard(String card, String passwd, String token) {
		boolean result = false;

		try {
			String url = cardApiUrl + "/card/open/api/validCard2.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("cardPwd", Base64Utils
					.encode(passwd)));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = false;

				logger.info("valid card fail ", response);
			} else {
				String nameCardStatus = map.get("nameCardStatus").toString();
				if ("1".equals(nameCardStatus)) {
					result = true;
				} else {
					result = false;
				}
			}
		} catch (Exception ex) {
			logger.error("valid card exception", ex);

			result = false;
		}

		return result;
	}

	public boolean validCard1(String card, String passwd, String token) {
		boolean result = false;

		try {
			String url = cardApiUrl + "/card/open/api/validCard.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("cardPwd", Base64Utils
					.encode(passwd)));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = false;

				logger.info("valid card fail ", response);
			} else {
				result = true;
			}
		} catch (Exception ex) {
			logger.error("valid card exception", ex);

			result = false;
		}
		return result;
	}

	public Map<String, Object> cardDetail(String card, String token) {

		Map<String, Object> result = null;

		try {
			String url = cardApiUrl + "/card/open/api/cardDetail.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = null;
				logger.info("card detail fail ", response);
			} else {
				result = JSON.parseObject(map.get("cardDetail").toString(),
						new TypeReference<Map<String, Object>>() {
						});

			}
		} catch (Exception ex) {
			logger.error("card detail exception", ex);
			result = null;
		}

		return result;
	}

	public Map<String, Object> cardYueJf(String card, String passwd,
			String token) {
		Map<String, Object> result = null;

		try {
			String url = cardApiUrl + "/card/open/api/cardYueJf.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("cardPwd", Base64Utils
					.encode(passwd)));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = null;
				logger.info("card yuejf fail ", response);
			} else {
				result = JSON.parseObject(map.get("cardInfo").toString(),
						new TypeReference<Map<String, Object>>() {
						});
			}
		} catch (Exception ex) {
			logger.error("card yuejf exception", ex);
			result = null;
		}

		return result;
	}

	public Map<String, Object> consumeTj(String card, String token) {
		Map<String, Object> result = null;
		try {
			String url = cardApiUrl + "/card/open/api/consumeMx.do";
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("pageNumber",
					DEFAUTL_PAGER_NUMBER));
			parameters.add(new BasicNameValuePair("pageSize",
					DEFAUTL_PAGER_SIZE));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = null;
				logger.info("consume mx fail", response);
			} else {
				// 移除明细结果
				map.remove("dataList");

				result = map;
			}
		} catch (Exception ex) {
			logger.error("consume mx exception", ex);
			result = null;
		}
		return result;
	}

	public Map<String, Object> consumeMx(String card, String token,
			String pageSize) {
		Map<String, Object> result = null;
		try {
			String url = cardApiUrl + "/card/open/api/consumeMx.do";
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("pageNumber",
					DEFAUTL_PAGER_NUMBER));
			parameters.add(new BasicNameValuePair("pageSize", pageSize));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = null;
				logger.info("consume mx fail", response);
			} else {
				result = map;
			}
		} catch (Exception ex) {
			logger.error("consume mx exception", ex);
			result = null;
		}
		return result;
	}

	public boolean changePasswd(String card, String oldpasswd,
			String newpasswd, String token) {
		boolean result = false;

		try {
			String url = cardApiUrl + "/card/open/api/rePwd.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("cardPwd", Base64Utils
					.encode(oldpasswd)));
			parameters.add(new BasicNameValuePair("newPwd", Base64Utils
					.encode(newpasswd)));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = false;

				logger.info("change passwd fail ", response);
			} else {
				result = true;
			}
		} catch (Exception ex) {
			logger.error("change passwd exception", ex);

			result = false;
		}

		return result;
	}

	public boolean cardLoss(String card, String passwd, String token) {
		boolean result = false;

		try {
			String url = cardApiUrl + "/card/open/api/cardGs.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("cardPwd", Base64Utils
					.encode(passwd)));

			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = false;

				logger.info("card loss fail ", response);
			} else {
				result = true;
			}
		} catch (Exception ex) {
			logger.error("card loss exception", ex);

			result = false;
		}

		return result;
	}

	public boolean cardFound(String card, String passwd, String token) {
		boolean result = false;

		try {
			String url = cardApiUrl + "/card/open/api/cardJg.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("cardPwd", Base64Utils
					.encode(passwd)));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			if ("false".equals(map.get("result").toString())) {
				result = false;

				logger.info("card found fail ", response);
			} else {
				result = true;
			}
		} catch (Exception ex) {
			logger.error("card found exception", ex);

			result = false;
		}

		return result;
	}

	public boolean saveConsume(String card, String passwd, String money,
			String token, String transactionNo) {
		boolean result = false;

		try {
			String url = cardApiUrl + "/card/open/api/saveConsume.do";

			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("cardPwd", Base64Utils
					.encode(passwd)));
			parameters.add(new BasicNameValuePair("money", money));
			parameters.add(new BasicNameValuePair("transactionNo",
					transactionNo));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			Map<String, Object> map = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});

			logger.debug(map.toString());

			if ("false".equals(map.get("result").toString())) {
				result = false;

				logger.info("card consume fail ", response);
			} else {
				result = true;
			}
		} catch (Exception ex) {
			logger.error("card consume exception", ex);

			result = false;
		}

		return result;
	}

	public Map<String, Object> getNewCardNo(String card, String uniqueCode,
			String token) {
		Map<String, Object> result = null;
		try {
			String url = cardApiUrl + "/card/open/api/getNewCardNo.do";
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();

			parameters.add(new BasicNameValuePair("cardNo", card));
			parameters.add(new BasicNameValuePair("bin", uniqueCode));
			parameters.add(new BasicNameValuePair("token", token));

			String response = HttpClientUtils.executePostRequest(
					HttpClientUtils.getHttpClient(), url, parameters,
					HttpClientUtils.DEFAULT_ENCODE);

			result = JSON.parseObject(response,
					new TypeReference<Map<String, Object>>() {
					});
		} catch (Exception ex) {
			logger.error("consume mx exception", ex);
		}
		
		return result;
	}

}
