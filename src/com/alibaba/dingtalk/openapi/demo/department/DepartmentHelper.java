package com.alibaba.dingtalk.openapi.demo.department;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DepartmentHelper {

	public static long createDepartment(String accessToken, String name, String parentId, String order) {
		String url = Env.OAPI_HOST + "/department/create?" +
				"access_token=" + accessToken;
		long id = 0; //无效id
		JSONObject args = new JSONObject();
		args.put("name", name);
		args.put("parentid", parentId);
		args.put("order", order);
		try {
			id = HttpHelper.httpPost(url, args, "id", Long.class);
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public static List<Department> listDepartments(String accessToken) {
		String url = Env.OAPI_HOST + "/department/list?" +
				"access_token=" + accessToken;
		List<Department> list = null;
		try {
			JSONArray arr = HttpHelper.httpGet(url, "department", JSONArray.class);
			list = new ArrayList<>();
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getObject(i, Department.class));
			}
		} catch (OApiException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static boolean deleteDepartment(String accessToken, Long id){
		String url = Env.OAPI_HOST  + "/department/delete?" +
				"access_token=" + accessToken + "&id=" + id;
		try {
			HttpHelper.httpGet(url, null, null);
			return true;
		} catch (OApiException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static boolean updateDepartment(String accessToken, String name, String parentId, String order, long id){
		String url = Env.OAPI_HOST  + "/department/update?" +
				"access_token=" + accessToken;
		JSONObject args = new JSONObject();
		args.put("name", name);
		args.put("parentid", parentId);
		args.put("order", order);
		args.put("id",id);
		try {
			HttpHelper.httpPost(url, args, null, null);
			return true;
		} catch (OApiException e) {
			e.printStackTrace();
			return false;
		}
	}
}
