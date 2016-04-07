package com.alibaba.dingtalk.openapi.demo.user;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.FileUtils;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.CorpUserDetailList;
import com.dingtalk.open.client.api.model.corp.CorpUserList;
import com.dingtalk.open.client.api.service.corp.CorpUserService;
import com.dingtalk.open.client.api.service.corp.MediaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserHelper {

	//创建成员
	public static void createUser(String accessToken,CorpUserDetail userDetail) throws Exception {
		CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
		JSONObject js = (JSONObject)JSONObject.parse(userDetail.getOrderInDepts());
		Map<Long, Long> orderInDepts = FileUtils.toHashMap(js);
		
		corpUserService.createCorpUser(accessToken, userDetail.getUserid(), userDetail.getName(), orderInDepts,
				userDetail.getDepartment(), userDetail.getPosition(), userDetail.getMobile(), userDetail.getTel(), userDetail.getWorkPlace(), 
				userDetail.getRemark(), userDetail.getEmail(), userDetail.getJobnumber(), 
				userDetail.getIsHide(), userDetail.getSenior(), userDetail.getExtattr());
		
		
//		String url = Env.OAPI_HOST + "/user/create?" +
//				"access_token=" + accessToken;
//		HttpHelper.httpPost(url, user);
	}
	
	
	//更新成员
	public static void updateUser( String accessToken,CorpUserDetail userDetail) throws Exception {
		CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
		JSONObject js = (JSONObject)JSONObject.parse(userDetail.getOrderInDepts());
		Map<Long, Long> orderInDepts = FileUtils.toHashMap(js);
		
		
		corpUserService.updateCorpUser(accessToken, userDetail.getUserid(), userDetail.getName(), orderInDepts, 
				userDetail.getDepartment(), userDetail.getPosition(), userDetail.getMobile(), userDetail.getTel(), userDetail.getWorkPlace(), 
				userDetail.getRemark(), userDetail.getEmail(), userDetail.getJobnumber(), 
				userDetail.getIsHide(), userDetail.getSenior(), userDetail.getExtattr());
	}
	
	
	//删除成员
	public static void deleteUser(String accessToken, String userid) throws Exception {
		CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
		corpUserService.deleteCorpUser(accessToken, userid);
//		String url = Env.OAPI_HOST + "/user/delete?" +
//				"access_token=" + accessToken + "&userid=" + userid;
//		HttpHelper.httpGet(url);
	}
	
	
	//获取成员
	public static CorpUserDetail getUser(String accessToken, String userid) throws Exception {
		
		CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
		return corpUserService.getCorpUser(accessToken, userid);
	}	
	
	//批量删除成员
	public static void batchDeleteUser(String accessToken, List<String> useridlist) 
			throws Exception {
		CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
		corpUserService.batchdeleteCorpUserListByUserids(accessToken, useridlist);
		
//		String url = Env.OAPI_HOST + "/user/batchdelete?" +
//				"access_token=" + accessToken;
//		JSONObject args = new JSONObject();
//		args.put("useridlist", useridlist);
//		HttpHelper.httpPost(url, args);
	}
	
	
	//获取部门成员
	public static CorpUserList getDepartmentUser(
			String accessToken, 
			long departmentId,
			Long offset,
    		Integer size,
    		String order)
			throws Exception {
		
		CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
		return corpUserService.getCorpUserSimpleList(accessToken, departmentId,
				offset, size, order);
	}
	
	
	//获取部门成员（详情）
	public static CorpUserDetailList getUserDetails(
			String accessToken, 
			long departmentId,
			Long offset,
    		Integer size,
    		String order)
			throws Exception {
		
		CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
		return corpUserService.getCorpUserList(accessToken, departmentId,
				offset, size, order);
	}

	public static CorpUserDetail getUserInfo(String accessToken, String code) throws Exception{
		
		CorpUserService corpUserService = ServiceFactory.getInstance().getOpenService(CorpUserService.class);
		return corpUserService.getUserinfo(accessToken, code);
	}
	
	public static JSONObject getAgentUserInfo(String ssoToken, String code) throws OApiException  {
		
		String url = Env.OAPI_HOST + "/sso/getuserinfo?" + "access_token=" + ssoToken + "&code=" + code;
		JSONObject response = HttpHelper.httpGet(url);
		return response;
	}

}
