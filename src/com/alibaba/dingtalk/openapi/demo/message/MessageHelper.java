package com.alibaba.dingtalk.openapi.demo.message;


import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;

public class MessageHelper {

	public static class Receipt {
		String invaliduser;
		String invalidparty;
	}
	
	
	public static Receipt send(String accessToken, LightAppMessageDelivery delivery) 
			throws OApiException {
		String url = Env.OAPI_HOST + "/message/send?" +
				"access_token=" + accessToken;
		
		JSONObject response = HttpHelper.httpPost(url, delivery.toJsonObject());
		if (response.containsKey("invaliduser") || response.containsKey("invalidparty")) {
			Receipt receipt = new Receipt();
			receipt.invaliduser = response.getString("invaliduser");
			receipt.invalidparty = response.getString("invalidparty");
			return receipt;
		}
		else {
			throw new OApiResultException("invaliduser or invalidparty");
		}
	}
	
	
	public static String send(String accessToken, ConversationMessageDelivery delivery) 
		throws OApiException {
		String url = Env.OAPI_HOST + "/message/send_to_conversation?" +
				"access_token=" + accessToken;
		
		JSONObject response = HttpHelper.httpPost(url, delivery.toJsonObject());
		if (response.containsKey("receiver")) {
			return response.getString("receiver");
		}
		else {
			throw new OApiResultException("receiver");
		}
	}
}
