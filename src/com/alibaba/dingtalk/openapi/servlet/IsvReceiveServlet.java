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
 * Servlet implementation class IsvReceiveServlet
 * 这个servlet用来接收钉钉服务器回调接口的推送
 */
public class IsvReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IsvReceiveServlet() {
        super();
        // TODO Auto-generated constructor stub
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
			dingTalkEncryptor = new DingTalkEncryptor(Env.TOKEN, Env.ENCODING_AES_KEY, Env.SUITE_KEY);
			plainText = dingTalkEncryptor.getDecryptMsg(msgSignature, timeStamp, nonce, encrypt);
		} catch (DingTalkEncryptException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
        /**对从encrypt解密出来的明文进行处理**/
		JSONObject plainTextJson = JSONObject.parseObject(plainText);		
		String eventType = plainTextJson.getString("EventType");
		switch (eventType){
		case "suite_ticket":Env.suiteTicket = request.getParameter("SuiteTicket");//do something
			break;
		case "tmp_auth_code":Env.authCode = request.getParameter("AuthCode");//do something
			break;
		case "change_auth":// do something;
			break;
		default : //do something
			break;
		}
		
        /**对返回信息进行加密**/
		long timeStampLong = Long.parseLong(timeStamp);
		Map<String,String> jsonMap = null;
		try {
			jsonMap = dingTalkEncryptor.getEncryptedMap("sucess", timeStampLong, nonce);
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
