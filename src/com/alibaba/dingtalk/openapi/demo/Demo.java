package com.alibaba.dingtalk.openapi.demo;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.user.User;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class Demo {
	
	public static void main(String[] args) {
		
		// 获取access token
		String accessToken = AuthHelper.getAccessToken();
		
		if (accessToken != null) {
			log("成功获取access token: ", accessToken);
			
			//创建部门
			String name = "TestDept.12";
			String parentId = "1";
			String order = "1";
			long departmentId = DepartmentHelper.createDepartment(accessToken, name, parentId, order);
			if (departmentId != 0) {
				log("成功创建部门", name, " 部门id=", departmentId);
			}
			else {
				log("创建部门失败");
			}
			//获取部门列表
			List<Department> list = DepartmentHelper.listDepartments(accessToken);
			if (list != null) {
				log("成功获取部门列表", list);
			}
			else {
				log("获取部门失败");
			}
			
			//更新部门
			boolean isUpdated = DepartmentHelper.updateDepartment(accessToken, name, parentId, order,
					departmentId);
			if (isUpdated){
				log("成功更新部门"," 部门id=", departmentId);
			}
			else {
				log("更新部门失败");
			}
			
			
			//创建成员
			User user = new User("id_yuhuan", "name_yuhuan");
			user.email = "yuhuan@abc.com";
			user.mobile = "18645512324";
			user.department = new ArrayList();
			user.department.add(departmentId);
			boolean isCreated = UserHelper.createUser(accessToken, user);
			if (isCreated) {
				log("成功创建成员","成员信息=", user);
			}
			else {
				log("创建成员失败");
			}
			
			//更新成员
			user.mobile = "18612341234";
			boolean isUpdate = UserHelper.updateUser(accessToken, user);
			if (isUpdate) {
				log("成功更新成员","成员信息=", user);
			}
			else {
				log("更新成员失败");
			}
			
			//获取成员
			User isGet = UserHelper.getUser(accessToken, user.userid);
			if (isGet != null) {
				log("成功获取成员","成员userid=", user.userid);
			}
			else {
				log("获取成员失败", "成员userid=", user.userid);
			}
			
			//获取部门成员
			int fetch_child = 0;
			List<User> userList = UserHelper.getDepartmentUser(accessToken, departmentId, fetch_child);
			if (userList != null) {
				log("成功获取部门成员","部门成员user=", userList);
			}
			else {
				log("获取部门成员失败");
			}
			
			//获取部门成员（详情）
			int fetch_child2 = 0;
			List<User> userList2 = UserHelper.getUserDetails(accessToken, departmentId, fetch_child2);
			if (userList2 != null) {
				log("成功获取部门成员详情","部门成员详情user=", userList2);
			}
			else {
				log("获取部门成员失败");
			}
			
			//批量删除成员
			User user2 = new User("id_yuhuan2", "name_yuhuan2");
			user2.email = "yuhua2n@abc.com";
			user2.mobile = "18611111111";
			user2.department = new ArrayList();
			user2.department.add(departmentId);
			UserHelper.createUser(accessToken, user2);
			
			List<String> useridlist = new ArrayList();
			useridlist.add(user.userid);
			useridlist.add(user2.userid);
			boolean isBatchDeleted = UserHelper.batchDeleteUser(accessToken, useridlist);
			if (isBatchDeleted) {
				log("成功批量删除成员","成员列表useridlist=",useridlist);
			}
			else {
				log("批量删除成员失败");
			}
			
			//删除成员
			User user3 = new User("id_yuhuan3", "name_yuhuan3");
			user3.email = "yuhua2n@abc.com";
			user3.mobile = "18611111111";
			user3.department = new ArrayList();
			user3.department.add(departmentId);
			UserHelper.createUser(accessToken, user3);
			boolean isDelete = UserHelper.deleteUser(accessToken, user3.userid);
			if (isDelete) {
				log("成功删除成员","成员userid=", user3.userid);
			}
			else {
				log("删除成员失败");
			}
			
			//删除部门
			boolean isDeleted = DepartmentHelper.deleteDepartment(accessToken, departmentId);
			if (isDeleted) {
				log("成功删除部门"," 部门id=", departmentId);
			}
			else {
				log("删除部门失败");
			}	
		}
	}
	
	
	private static void log(Object... msgs) {
		StringBuilder sb = new StringBuilder();
		for (Object o : msgs) {
			if (o != null) {
				sb.append(o.toString());
			}
		}
		System.out.println(sb.toString());
	}
}
