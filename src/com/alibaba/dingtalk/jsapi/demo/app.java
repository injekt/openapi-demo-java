package com.alibaba.dingtalk.jsapi.demo;

import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;

public class app {
	private static final long serialVersionUID = 1L;
    public app() {
        super();
        // TODO Auto-generated constructor stub
    }
	public static String getConfig(String url){
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
		return "{signature:'"+signature+"',nonceStr:'"+nonceStr+"',timeStamp:'"+timeStamp+"',corpId:'"+Env.CORP_ID+"'}";
	}
}
