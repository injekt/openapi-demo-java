package com.alibaba.dingtalk.openapi.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.utils.aes.DingTalkEncryptException;
import com.alibaba.dingtalk.openapi.demo.utils.aes.DingTalkEncryptor;
import com.alibaba.fastjson.JSONObject;

/**
 * Servlet implementation class CreateSuiteReceiveServlet
 * 这个servlet用来接收钉钉服务器创建套件回调接口的推送
 */
public class CreateSuiteReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CreateSuiteReceiveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	public static void main(String arg[]){
		DingTalkEncryptor dingTalkEncryptor = null;
		String plainText = null;
		try {
			//对于DingTalkEncryptor的第三个参数，ISV进行配置的时候传对应套件的SUITE_KEY，普通企业传Corpid,由于此回调接口只有isv使用，所以传Env.SUITE_KEY
			dingTalkEncryptor = new DingTalkEncryptor("123456", "4g5j64qlyl3zvetqxz5jiocdr586fn2zvjpa8zls3ij", Env.CREATE_SUITE_KEY);
			plainText = dingTalkEncryptor.getDecryptMsg("5a65ceeef9aab2d149439f82dc191dd6c5cbe2c0", "1445827045067", "nEXhMP4r", "1a3NBxmCFwkCJvfoQ7WhJHB+iX3qHPsc9JbaDznE1i03peOk1LaOQoRz3+nlyGNhwmwJ3vDMG+OzrHMeiZI7gTRWVdUBmfxjZ8Ej23JVYa9VrYeJ5as7XM/ZpulX8NEQis44w53h1qAgnC3PRzM7Zc/D6Ibr0rgUathB6zRHP8PYrfgnNOS9PhSBdHlegK+AGGanfwjXuQ9+0pZcy0w9lQ==");
		} catch (DingTalkEncryptException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		System.out.println(plainText);
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 /**url中的签名**/
        String msgSignature = request.getParameter("signature");
        /**url中的时间戳**/
        String timeStamp = request.getParameter("timestamp");
        /**url中的随机字符串**/
        String nonce = request.getParameter("nonce");
        
        /**post数据包数据中的加密数据**/
        ServletInputStream sis =  request.getInputStream(); 
        BufferedReader br = new BufferedReader(new InputStreamReader(sis));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
         sb.append(line);
        }
        JSONObject jsonEncrypt = JSONObject.parseObject(sb.toString());
		String encrypt = jsonEncrypt.getString("encrypt");
		
        /**对encrypt进行解密**/
		DingTalkEncryptor dingTalkEncryptor = null;
		String plainText = null;
		try {
			//对于DingTalkEncryptor的第三个参数，ISV进行配置的时候传对应套件的SUITE_KEY，普通企业传Corpid,由于此回调接口只有isv使用，所以传Env.SUITE_KEY
			dingTalkEncryptor = new DingTalkEncryptor(Env.TOKEN, Env.ENCODING_AES_KEY, Env.CREATE_SUITE_KEY);
			plainText = dingTalkEncryptor.getDecryptMsg(msgSignature, timeStamp, nonce, encrypt);
		} catch (DingTalkEncryptException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
        /**对从encrypt解密出来的明文进行处理**/
		JSONObject plainTextJson = JSONObject.parseObject(plainText);		
		String eventType = plainTextJson.getString("EventType");
		String random = "";
		switch (eventType){
		case "check_create_suite_url":
			random = plainTextJson.getString("Random");
			String testSuiteKey = plainTextJson.getString("TestSuiteKey");
			break;
		default : //do something
			break;
		}
		
        /**对返回信息进行加密**/
		long timeStampLong = Long.parseLong(timeStamp);
		Map<String,String> jsonMap = null;
		try {
			jsonMap = dingTalkEncryptor.getEncryptedMap(random, timeStampLong, nonce);
		} catch (DingTalkEncryptException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		JSONObject json = new JSONObject();
		json.putAll(jsonMap);	
		response.getWriter().append(json.toString());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
