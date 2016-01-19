package com.alibaba.dingtalk.openapi.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.user.User;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class ContactsServlet
 */
public class ContactsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String corpId = request.getParameter("corpid");

		try {
			response.setContentType("text/html; charset=utf-8"); 

			String accessToken = AuthHelper.getAccessToken(corpId);
			
			List<Department> departments = new ArrayList<Department>();
			departments = DepartmentHelper.listDepartments(accessToken);
			JSONObject json = new JSONObject();
			JSONArray usersArray = new JSONArray();
			
			
			System.out.println("depart num:"+departments.size());
			for(int i = 0;i<departments.size();i++){
				JSONObject userDepJSON = new JSONObject();
				
				JSONObject usersJSON = new JSONObject();
				JSONArray userArray = new JSONArray();
				
				System.out.println("dep:"+departments.get(i).toString());
				List<User> users = new ArrayList<User>();
				users = UserHelper.getDepartmentUser(accessToken,Long.valueOf(departments.get(i).id));
				if(users.size()==0){
					continue;
				}
				for(int j = 0;j<users.size();j++){
					String user = JSON.toJSONString(users.get(j));
					userArray.add(JSONObject.parseObject(user, User.class));
				}
				System.out.println("user:"+userArray.toString());
				usersJSON.put("name", departments.get(i).name);
				usersJSON.put("member", userArray);
				usersArray.add(usersJSON);
			}
			json.put("department", usersArray);
			System.out.println("depart:"+json.toJSONString());
			response.getWriter().append(json.toJSONString());

		} catch (OApiException e) {
			e.printStackTrace();
			response.getWriter().append(e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
