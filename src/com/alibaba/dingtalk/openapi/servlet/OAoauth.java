package com.alibaba.dingtalk.openapi.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;
import com.alibaba.fastjson.JSONObject;

public class OAoauth extends HttpServlet{
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String code = request.getParameter("code");
		if(code != null){
			try {
				String ssoToken = AuthHelper.getSsoToken();
				response.getWriter().append(ssoToken);
				JSONObject js = UserHelper.getAgentUserInfo(ssoToken, code);
				response.getWriter().append(js.toString());
			} catch (OApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			String reurl = "https://oa.dingtalk.com/omp/api/micro_app/admin/landing?corpid=" + 
			Env.CORP_ID + "&redirect_url=" + Env.OA_BACKGROUND_URL;//配置的OA后台地址
			response.addHeader("location", reurl);
			response.setStatus(302);
		}
	}
	
	public static void main(String[] args) throws OApiException{
		String ssoToken = AuthHelper.getSsoToken();
		JSONObject js = UserHelper.getAgentUserInfo(ssoToken, "1ac76d35062f300286100b3ca2b74e91");
		System.out.println(js.toString());
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	

}
