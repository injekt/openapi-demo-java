package com.alibaba.dingtalk.openapi.demo.service;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.isv.CorpAgent;
import com.dingtalk.open.client.api.model.isv.CorpAuthInfo;
import com.dingtalk.open.client.api.model.isv.CorpAuthInfo.AuthCorpInfo;
import com.dingtalk.open.client.api.model.isv.CorpAuthSuiteCode;
import com.dingtalk.open.client.api.model.isv.SuiteToken;
import com.dingtalk.open.client.api.service.isv.IsvService;
import com.dingtalk.open.client.common.SdkInitException;

public class ServiceHelper {

	
	public static String getSuiteToken(String suite_key, String suite_secret,String suite_ticket) throws Exception{
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		IsvService isvService = serviceFactory.getOpenService(IsvService.class);

		SuiteToken suiteToken= isvService.getSuiteToken(suite_key, suite_secret, suite_ticket);
		return suiteToken.getSuite_access_token();
	}
	
	public static CorpAuthSuiteCode getPermanentCode(String tmp_auth_cod, String suiteAccessToken )throws Exception{
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		IsvService isvService = serviceFactory.getOpenService(IsvService.class);
		CorpAuthSuiteCode corpAuthSuiteCode = isvService.getPermanentCode(suiteAccessToken, tmp_auth_cod);
		
		return corpAuthSuiteCode;
	}
	
	public static String getCorpToken(String auth_corpid, String permanent_code, String suiteAccessToken )throws Exception{
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		IsvService isvService = serviceFactory.getOpenService(IsvService.class);
		
		return isvService.getCorpToken(suiteAccessToken, auth_corpid, permanent_code).getAccess_token();
		
	}
	
	public static CorpAuthInfo getAuthInfo(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code)throws Exception{
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		IsvService isvService = serviceFactory.getOpenService(IsvService.class);

		return isvService.getAuthInfo(suiteAccessToken, suite_key, auth_corpid, permanent_code);
	}
	
	public static CorpAgent getAgent(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code, String agentid)throws Exception{
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		IsvService isvService = serviceFactory.getOpenService(IsvService.class);

		return isvService.getAgent(suiteAccessToken, suite_key, auth_corpid, agentid);
	}
	

	public static void getActivateSuite(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code )throws Exception{
		
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		IsvService isvService = serviceFactory.getOpenService(IsvService.class);
		
		
		isvService.activateSuite(suiteAccessToken, suite_key, auth_corpid);
	}
	
	
	
	
}
