package com.alibaba.dingtalk.openapi.demo.user;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;

public class UserHelper {

	public static boolean createUser(String accessToken, User user) {
		String url = Env.OAPI_HOST + "/user/create?" +
				"access_token=" + accessToken;
		boolean success = false;
		try {
			HttpHelper.httpPost(url, user, null, null);
			success = true;
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	//更新成员
	public static boolean updateUser(String accessToken, User user) {
		String url = Env.OAPI_HOST + "/user/update?" +
				"access_token=" + accessToken;
		boolean success = false;
		try {
			HttpHelper.httpPost(url, user, null, null);
			success = true;
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	//删除成员
	public static boolean deleteUser(String accessToken, String userid) {
		String url = Env.OAPI_HOST + "/user/delete?" +
				"access_token=" + accessToken + "&userid=" + userid;
		boolean success = false;
		try {
			HttpHelper.httpGet(url, null, null);
			success = true;
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	//获取成员
	public static User getUser(String accessToken, String userid) {
		String url = Env.OAPI_HOST + "/user/get?" +
				"access_token=" + accessToken + "&userid=" + userid;
		User user = null;
		try {
			JSONObject json = HttpHelper.httpGet(url, null, JSONObject.class);
			json.remove("errcode");
			json.remove("errmsg");
			user = JSON.parseObject(json.toJSONString(), User.class);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	//批量删除成员
	public static boolean batchDeleteUser(String accessToken, List<String> useridlist) {
		String url = Env.OAPI_HOST + "/user/batchdelete?" +
				"access_token=" + accessToken;
		boolean success = false;
		JSONObject args = new JSONObject();
		args.put("useridlist", useridlist);
		try {
			HttpHelper.httpPost(url, args, null, null);
			success = true;
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	//获取部门成员
	public static List<User> getDepartmentUser(String accessToken, long department_id, int fetch_child) {
		String url = Env.OAPI_HOST + "/user/simplelist?" +
				"access_token=" + accessToken + "&department_id=" + 1 + "&fetch_child=" + 0;
		List<User> list = null;
		try {
			JSONArray arr = HttpHelper.httpGet(url, "userlist", JSONArray.class);
			list = new ArrayList<>();
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getObject(i, User.class));
			}
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//获取部门成员（详情）
	public static List<User> getUserDetails(String accessToken, long department_id, int fetch_child) {
		String url = Env.OAPI_HOST + "/user/list?" +
				"access_token=" + accessToken + "&department_id=" + 1 + "&fetch_child=" + 0;
		List<User> list = null;
		try {
			JSONArray arr = HttpHelper.httpGet(url, "userlist", JSONArray.class);
			list = new ArrayList<>();
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getObject(i, User.class));
			}
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return list;
	}
}
