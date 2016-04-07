package com.alibaba.dingtalk.openapi.demo.department;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.corp.Department;
import com.dingtalk.open.client.api.service.corp.CorpDepartmentService;
import com.dingtalk.open.client.common.SdkInitException;
import com.dingtalk.open.client.common.ServiceException;
import com.dingtalk.open.client.common.ServiceNotExistException;

public class DepartmentHelper {

	public static String createDepartment(String accessToken, String name, 
			String parentId, String order, boolean createDeptGroup ) throws Exception {
		
		CorpDepartmentService corpDepartmentService = ServiceFactory.getInstance().getOpenService(CorpDepartmentService.class);
		return corpDepartmentService.deptCreate(accessToken, name, parentId, order, createDeptGroup);
		 
	}

	
	public static List<Department> listDepartments(String accessToken, String parentDeptId) 
			throws  ServiceNotExistException, SdkInitException, ServiceException {
		CorpDepartmentService corpDepartmentService = ServiceFactory.getInstance().getOpenService(CorpDepartmentService.class);
		List<Department> deptList = corpDepartmentService.getDeptList(accessToken, parentDeptId);
		return deptList;
	}
	
	
	public static void deleteDepartment(String accessToken, Long id) throws Exception{
		CorpDepartmentService corpDepartmentService = ServiceFactory.getInstance().getOpenService(CorpDepartmentService.class);
		corpDepartmentService.deptDelete(accessToken, id);
	}
	
	
	public static void updateDepartment(String accessToken,long id, String name, 
			String parentId, String order, Boolean createDeptGroup,
			boolean autoAddUser, String deptManagerUseridList, boolean deptHiding, String deptPerimits,
			String userPerimits, Boolean outerDept, String outerPermitDepts,
			String outerPermitUsers, String orgDeptOwner) throws Exception{
		CorpDepartmentService corpDepartmentService = ServiceFactory.getInstance().getOpenService(CorpDepartmentService.class);
		corpDepartmentService.deptUpdate(accessToken, id, name, parentId, order, createDeptGroup, 
				autoAddUser, deptManagerUseridList, deptHiding, deptPerimits, userPerimits, 
				outerDept, outerPermitDepts, outerPermitUsers, orgDeptOwner);

	}
}
