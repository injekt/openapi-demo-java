package com.alibaba.dingtalk.openapi.demo.auth;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;

public class AuthHelper {

	public static String getAccessToken() {
		String url = Env.OAPI_HOST + "/gettoken?" + 
				"corpid=" + Env.CORP_ID + "&corpsecret=" + Env.SECRET;
		String accessToken = null;
		try {
			accessToken = HttpHelper.httpGet(url, "access_token", String.class);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		
		return accessToken;
	}
}
