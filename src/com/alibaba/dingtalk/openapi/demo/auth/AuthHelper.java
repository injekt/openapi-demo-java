package com.alibaba.dingtalk.openapi.demo.auth;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;

public class AuthHelper {

	public static String jsapiTicket = null;
	public static String accessToken = null;
	public static Timer timer = null;
	public static final Integer cacheTime = 1000 * 60 * 60 * 2;
	public static long currentTime = 0 + cacheTime +1;
	public static long lastTime = 0;

	public static String getAccessToken() throws OApiException {
		
		if(lastTime != 0){
			currentTime = System.currentTimeMillis();
		}
		if(currentTime - lastTime >= cacheTime){
			String url = Env.OAPI_HOST + "/gettoken?" + 
    				"corpid=" + Env.CORP_ID + "&corpsecret=" + Env.SECRET;
    		JSONObject response = HttpHelper.httpGet(url);
    		if (response.containsKey("access_token")) {
    			accessToken = response.getString("access_token");
    			
    		}
    		else {
    			throw new OApiResultException("access_token");
    		}
    		
			String url_ticket = Env.OAPI_HOST + "/get_jsapi_ticket?" + 
					"type=jsapi" + "&access_token=" + accessToken;
			JSONObject response_ticket = HttpHelper.httpGet(url_ticket);
			if (response_ticket.containsKey("ticket")) {
				jsapiTicket = response_ticket.getString("ticket");
    			currentTime = System.currentTimeMillis();
    			lastTime = System.currentTimeMillis();
			}
			else {
				throw new OApiResultException("ticket");
			}

		}else{
			return accessToken;
		}
		
		return accessToken;
	}
	//正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
	public static String getJsapiTicket(String accessToken) throws OApiException {    
		if (jsapiTicket == null){
			String url = Env.OAPI_HOST + "/get_jsapi_ticket?" + 
					"type=jsapi" + "&access_token=" + accessToken;
			JSONObject response = HttpHelper.httpGet(url);
			if (response.containsKey("ticket")) {
				jsapiTicket = response.getString("ticket");
				return jsapiTicket;
			}
			else {
				throw new OApiResultException("ticket");
			}
		}else{
			return jsapiTicket;
		}
	}
	
	public static String sign(String ticket, String nonceStr, long timeStamp, String url) 
			throws OApiException {
		String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr +
	            "&timestamp=" + String.valueOf(timeStamp) + "&url=" + url;
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
        for (byte b : hash){
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
	}
	
public static String getConfig(String urlString, String queryString){
		
		String queryStringEncode = null;
		String url;
		if(queryString != null){
			queryStringEncode = URLDecoder.decode(queryString);
			url = urlString + "?" + queryStringEncode;
		}
		else{
			url = urlString;
		}
		System.out.println(url);
		String nonceStr = "abcdefg";
		long timeStamp = System.currentTimeMillis()/1000;
		String signedUrl = url;
		String accessToken = null;
		String ticket = null;
		String signature = null;
		try {
			accessToken = AuthHelper.getAccessToken();
			ticket = AuthHelper.getJsapiTicket(accessToken);
			signature = AuthHelper.sign(ticket, nonceStr, timeStamp, signedUrl);
		} catch (OApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "{jsticket:'" + ticket + "',signature:'"+signature+"',nonceStr:'"+nonceStr+"',timeStamp:'"+timeStamp+"',corpId:'"+Env.CORP_ID+"'}";
	}
}
