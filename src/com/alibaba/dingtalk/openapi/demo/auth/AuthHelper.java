package com.alibaba.dingtalk.openapi.demo.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.FileUtils;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.JsapiTicket;
import com.dingtalk.open.client.api.model.isv.CorpAuthToken;
import com.dingtalk.open.client.api.service.corp.JsapiService;
import com.dingtalk.open.client.api.service.isv.IsvService;

public class AuthHelper {

	// public static String jsapiTicket = null;
	// public static String accessToken = null;
	public static Timer timer = null;
	// 调整到1小时50分钟
	public static final long cacheTime = 1000 * 60 * 55 * 2;
	public static long currentTime = 0 + cacheTime + 1;
	public static long lastTime = 0;
	public static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/*
	 * 在此方法中，为了避免频繁获取access_token，
	 * 在距离上一次获取access_token时间在两个小时之内的情况，
	 * 将直接从持久化存储中读取access_token
	 * 
	 * 因为access_token和jsapi_ticket的过期时间都是7200秒
	 * 所以在获取access_token的同时也去获取了jsapi_ticket
	 * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的
	 * 具体信息请查看开发者文档--权限验证配置
	 */
	public static String getAccessToken(String corpId) throws Exception {
		long curTime = System.currentTimeMillis();
		JSONObject accessTokenValue = (JSONObject) FileUtils.getValue("accesstoken", corpId);
		String accToken = "";
		String jsTicket = "";
		JSONObject jsontemp = new JSONObject();
		if (accessTokenValue == null || curTime - accessTokenValue.getLong("begin_time") >= cacheTime) {
			ServiceFactory serviceFactory = ServiceFactory.getInstance();

			IsvService isvService = serviceFactory.getOpenService(IsvService.class);
			CorpAuthToken corpAuthToken = isvService.getCorpToken((String)FileUtils.getValue("ticket", "suiteToken"), 
					corpId,(String)FileUtils.getValue("permanentcode", corpId));
			
			if (corpAuthToken.getAccess_token() != null) {
				// save accessToken
				accToken = corpAuthToken.getAccess_token();
				JSONObject jsonAccess = new JSONObject();
				jsontemp.clear();
				jsontemp.put("access_token", accToken);
				jsontemp.put("begin_time", curTime);
				jsonAccess.put(corpId, jsontemp);

				FileUtils.write2File(jsonAccess, "accesstoken");
			} else {
				throw new OApiResultException("access_token");
			}
			
			if(accToken.length() > 0){
				
				JsapiService jsapiService = serviceFactory.getOpenService(JsapiService.class);

				JsapiTicket JsapiTicket = jsapiService.getJsapiTicket(accToken, "jsapi");
				jsTicket = JsapiTicket.getTicket();
				
				JSONObject jsonTicket = new JSONObject();
				jsontemp.clear();
				jsontemp.put("ticket", jsTicket);
				jsontemp.put("begin_time", curTime);
				jsonTicket.put(corpId, jsontemp);
				FileUtils.write2File(jsonTicket, "jsticket");
			}

		} else {
			return accessTokenValue.getString("access_token");
		}

		return accToken;
	}

	// 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
	public static String getJsapiTicket(String accessToken, String corpId) throws Exception {
		JSONObject jsTicketValue = (JSONObject) FileUtils.getValue("jsticket", corpId);
		long curTime = System.currentTimeMillis();
		String jsTicket = "";

		 if (jsTicketValue == null || curTime -
		 jsTicketValue.getLong("begin_time") >= cacheTime) {
				ServiceFactory serviceFactory;
				
			serviceFactory = ServiceFactory.getInstance();
			JsapiService jsapiService = serviceFactory.getOpenService(JsapiService.class);

			JsapiTicket JsapiTicket = jsapiService.getJsapiTicket(accessToken, "jsapi");
			jsTicket = JsapiTicket.getTicket();
			
			JSONObject jsonTicket = new JSONObject();
			JSONObject jsontemp = new JSONObject();
			jsontemp.clear();
			jsontemp.put("ticket", jsTicket);
			jsontemp.put("begin_time", curTime);
			jsonTicket.put(corpId, jsontemp);
			FileUtils.write2File(jsonTicket, "jsticket");

			return jsTicket;
		 } else {
			 return jsTicketValue.getString("ticket");
		 }
	}

	public static String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
				+ "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return bytesToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new OApiResultException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new OApiResultException(e.getMessage());
		}
	}

	private static String bytesToHex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

	public static String getConfig(HttpServletRequest request) {
		String urlString = request.getRequestURL().toString();
		String queryString = request.getQueryString();

		// todo
		String corpId = request.getParameter("corpid");
		String appId = request.getParameter("appid");

		System.out.println(df.format(new Date())+
				" getconfig,url:" + urlString + " query:" + queryString + " corpid:" + corpId + " appid:" + appId);

		String queryStringEncode = null;
		String url;
		if (queryString != null) {
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		} else {
			url = urlString;
		}
		System.out.println(url);
		String nonceStr = "abcdefg";
		long timeStamp = System.currentTimeMillis() / 1000;
		String signedUrl = url;
		String accessToken = null;
		String ticket = null;
		String signature = null;
		String agentid = null;

		try {
			accessToken = AuthHelper.getAccessToken(corpId);
			ticket = AuthHelper.getJsapiTicket(accessToken, corpId);
			signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
			agentid = AuthHelper.getAgentId(corpId, appId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "{jsticket:'" + ticket + "',signature:'" + signature + "',nonceStr:'" + nonceStr + "',timeStamp:'"
				+ timeStamp + "',corpId:'" + corpId + "',agentid:'" + agentid+ "',appid:'" + appId + "'}";
	}

	public static String getAgentId(String corpId, String appId) throws OApiException {
		String agentId = null;
		String accessToken = FileUtils.getValue("ticket", "suiteToken").toString();
		String url = "https://oapi.dingtalk.com/service/get_auth_info?suite_access_token=" + accessToken;
		JSONObject args = new JSONObject();
		args.put("suite_key", Env.SUITE_KEY);
		args.put("auth_corpid", corpId);
		args.put("permanent_code", FileUtils.getValue("permanentcode", corpId));
		JSONObject response = HttpHelper.httpPost(url, args);

		if (response.containsKey("auth_info")) {
			JSONArray agents = (JSONArray) ((JSONObject) response.get("auth_info")).get("agent");

			for (int i = 0; i < agents.size(); i++) {

				if (((JSONObject) agents.get(i)).get("appid").toString().equals(appId)) {
					agentId = ((JSONObject) agents.get(i)).get("agentid").toString();
					break;
				}
			}
		} else {
			throw new OApiResultException("agentid");
		}
		return agentId;
	}

	public static String getSsoToken() throws OApiException {
		String url = "https://oapi.dingtalk.com/sso/gettoken?corpid=" + Env.CORP_ID + "&corpsecret=" + Env.SSO_Secret;
		JSONObject response = HttpHelper.httpGet(url);
		String ssoToken;
		if (response.containsKey("access_token")) {
			ssoToken = response.getString("access_token");
		} else {
			throw new OApiResultException("Sso_token");
		}
		return ssoToken;

	}

}
