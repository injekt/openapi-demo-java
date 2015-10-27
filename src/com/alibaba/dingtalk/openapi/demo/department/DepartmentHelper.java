package com.alibaba.dingtalk.openapi.demo.department;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DepartmentHelper {

	public static long createDepartment(String accessToken, String name, 
			String parentId, String order, boolean createDeptGroup ) throws OApiException {
		String url = Env.OAPI_HOST + "/department/create?" +
				"access_token=" + accessToken;
		JSONObject args = new JSONObject();
		args.put("name", name);
		args.put("parentid", parentId);
		args.put("order", order);
		args.put("createDeptGroup", createDeptGroup);
		JSONObject response = HttpHelper.httpPost(url, args);
		if (response.containsKey("id")) {
			return response.getLong("id");
		}
		else {
			throw new OApiResultException("id");
		}
	}

	
	public static List<Department> listDepartments(String accessToken) 
			throws OApiException {
		String url = Env.OAPI_HOST + "/department/list?" +
				"access_token=" + accessToken;
		JSONObject response = HttpHelper.httpGet(url);
		if (response.containsKey("department")) {
			JSONArray arr = response.getJSONArray("department");
			List<Department> list  = new ArrayList<>();
			for (int i = 0; i < arr.size(); i++) {
				list.add(arr.getObject(i, Department.class));
			}
			return list;
		}
		else {
			throw new OApiResultException("department");
		}
	}
	
	
	public static void deleteDepartment(String accessToken, Long id) throws OApiException{
		String url = Env.OAPI_HOST  + "/department/delete?" +
				"access_token=" + accessToken + "&id=" + id;
		HttpHelper.httpGet(url);
	}
	
	
	public static void updateDepartment(String accessToken, String name, 
			String parentId, String order, long id,
			boolean autoAddUser, String deptManagerUseridList, boolean deptHiding, String deptPerimits) throws OApiException{
		String url = Env.OAPI_HOST  + "/department/update?" +
				"access_token=" + accessToken;
		JSONObject args = new JSONObject();
		args.put("name", name);
		args.put("parentid", parentId);
		args.put("order", order);
		args.put("id",id);
		args.put("autoAddUser",autoAddUser);
		args.put("deptManagerUseridList",deptManagerUseridList);
		args.put("deptHiding",deptHiding);
		args.put("deptPerimits",deptPerimits);

		HttpHelper.httpPost(url, args);
	}
}
