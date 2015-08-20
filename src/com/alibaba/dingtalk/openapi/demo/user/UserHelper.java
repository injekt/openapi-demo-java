package com.alibaba.dingtalk.openapi.demo.user;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

	//创建成员
	public static void createUser(String accessToken, User user) throws OApiException {
		String url = Env.OAPI_HOST + "/user/create?" +
				"access_token=" + accessToken;
		HttpHelper.httpPost(url, user);
	}
	
	
	//更新成员
	public static void updateUser(String accessToken, User user) throws OApiException {
		String url = Env.OAPI_HOST + "/user/update?" +
				"access_token=" + accessToken;
		HttpHelper.httpPost(url, user);
	}
	
	
	//删除成员
	public static void deleteUser(String accessToken, String userid) throws OApiException {
		String url = Env.OAPI_HOST + "/user/delete?" +
				"access_token=" + accessToken + "&userid=" + userid;
		HttpHelper.httpGet(url);
	}
	
	
	//获取成员
	public static User getUser(String accessToken, String userid) throws OApiException {
		String url = Env.OAPI_HOST + "/user/get?" +
				"access_token=" + accessToken + "&userid=" + userid;
		JSONObject json = HttpHelper.httpGet(url);
		return JSON.parseObject(json.toJSONString(), User.class);
	}
	
	
	//批量删除成员
	public static void batchDeleteUser(String accessToken, List<String> useridlist) 
			throws OApiException {
		String url = Env.OAPI_HOST + "/user/batchdelete?" +
				"access_token=" + accessToken;
		JSONObject args = new JSONObject();
		args.put("useridlist", useridlist);
		HttpHelper.httpPost(url, args);
	}
	
	
	//获取部门成员
	public static List<User> getDepartmentUser(String accessToken, long department_id, int fetch_child) 
			throws OApiException {
		String url = Env.OAPI_HOST + "/user/simplelist?" +
				"access_token=" + accessToken + "&department_id=" + 1 + "&fetch_child=" + 0;
		JSONObject response = HttpHelper.httpGet(url);
		if (response.containsKey("userlist")) {
			List<User> list = new ArrayList<>();
			JSONArray arr = response.getJSONArray("userlist");
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getObject(i, User.class));
			}
			return list;
		}
		else {
			throw new OApiResultException("userlist");
		}
	}
	
	
	//获取部门成员（详情）
	public static List<User> getUserDetails(String accessToken, long department_id, int fetch_child) 
			throws OApiException {
		String url = Env.OAPI_HOST + "/user/list?" +
				"access_token=" + accessToken + "&department_id=" + 1 + "&fetch_child=" + 0;
		JSONObject response = HttpHelper.httpGet(url);
		if (response.containsKey("userlist")) {
			JSONArray arr = response.getJSONArray("userlist");
			List<User> list = new ArrayList<>();
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getObject(i, User.class));
			}
			return list;
		}
		else {
			throw new OApiResultException("userlist");
		}
	}
}
