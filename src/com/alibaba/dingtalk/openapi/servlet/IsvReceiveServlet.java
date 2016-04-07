package com.alibaba.dingtalk.openapi.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.alibaba.dingtalk.openapi.demo.Env;
import com.alibaba.dingtalk.openapi.demo.OApiException;
import com.alibaba.dingtalk.openapi.demo.OApiResultException;
import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.service.ServiceHelper;
import com.alibaba.dingtalk.openapi.demo.utils.FileUtils;
import com.alibaba.dingtalk.openapi.demo.utils.HttpHelper;
import com.alibaba.dingtalk.openapi.demo.utils.aes.DingTalkEncryptException;
import com.alibaba.dingtalk.openapi.demo.utils.aes.DingTalkEncryptor;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.model.isv.CorpAuthSuiteCode;
import com.dingtalk.open.client.api.service.isv.IsvService;
import com.dingtalk.open.client.common.SdkInitException;
import com.dingtalk.open.client.common.ServiceNotExistException;

/**
 * Servlet implementation class IsvReceiveServlet 这个servlet用来接收钉钉服务器回调接口的推送
 */
public class IsvReceiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public IsvReceiveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	/*
	 * 接收钉钉服务器的回调数据
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response){
		try{
			/** url中的签名 **/
			String msgSignature = request.getParameter("signature");
			/** url中的时间戳 **/
			String timeStamp = request.getParameter("timestamp");
			/** url中的随机字符串 **/
			String nonce = request.getParameter("nonce");
	
			/** post数据包数据中的加密数据 **/
			ServletInputStream sis = request.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(sis));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			/*post数据包数据中的加密数据转换成JSON对象，JSON对象的格式如下
			 *  {
	    	 *	"encrypt":"1ojQf0NSvw2WPvW7LijxS8UvISr8pdDP+rXpPbcLGOmIBNbWetRg7IP0vdhVgkVwSoZBJeQwY2zhROsJq/HJ+q6tp1qhl9L1+ccC9ZjKs1wV5bmA9NoAWQiZ+7MpzQVq+j74rJQljdVyBdI/dGOvsnBSCxCVW0ISWX0vn9lYTuuHSoaxwCGylH9xRhYHL9bRDskBc7bO0FseHQQasdfghjkl"
			 *	}
			 */
			JSONObject jsonEncrypt = JSONObject.parseObject(sb.toString());
			String encrypt = "";
	
			/** 取得JSON对象中的encrypt字段， **/
			try {
				encrypt = jsonEncrypt.getString("encrypt");
			} catch (Exception e) {
				e.printStackTrace();
			}
	
			/** 对encrypt进行解密 **/
			DingTalkEncryptor dingTalkEncryptor = null;
			String plainText = null;
			DingTalkEncryptException dingTalkEncryptException = null;
			try {
				/*创建加解密类
				 * 第一个参数为注册套件的之时填写的token
				 * 第二个参数为注册套件的之时生成的数据加密密钥（ENCODING_AES_KEY）
				 * 第三个参数，ISV开发传入套件的suiteKey，普通企业开发传Corpid
				 * 具体参数值请查看开发者后台(http://console.d.aliyun.com)
				 * 
				 * 注：其中，对于第三个参数，在第一次接受『验证回调URL有效性事件的时候』
				 * 传入Env.CREATE_SUITE_KEY，对于这种情况，已在异常中catch做了处理。
				 * 具体区别请查看文档『验证回调URL有效性事件』
				 */ 
				dingTalkEncryptor = new DingTalkEncryptor(Env.TOKEN, Env.ENCODING_AES_KEY, Env.SUITE_KEY);
				/*
				 * 获取从encrypt解密出来的明文
				 */
				plainText = dingTalkEncryptor.getDecryptMsg(msgSignature, timeStamp, nonce, encrypt);
			} catch (DingTalkEncryptException e) {
				// TODO Auto-generated catch block
				dingTalkEncryptException = e;
				e.printStackTrace();
			} finally {
				if (dingTalkEncryptException != null) {
					if (dingTalkEncryptException.code == DingTalkEncryptException.COMPUTE_DECRYPT_TEXT_CORPID_ERROR) {
						try {
							/*
							 * 第一次创建套件生成加解密类需要传入Env.CREATE_SUITE_KEY，
							 */
							dingTalkEncryptor = new DingTalkEncryptor(Env.TOKEN, Env.ENCODING_AES_KEY,
									Env.CREATE_SUITE_KEY);
							plainText = dingTalkEncryptor.getDecryptMsg(msgSignature, timeStamp, nonce, encrypt);
						} catch (DingTalkEncryptException e) {
							// TODO Auto-generated catch block
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
					} else {
						System.out.println(dingTalkEncryptException.getMessage());
						dingTalkEncryptException.printStackTrace();
					}
				}
			}
	
			/*
			 *  对从encrypt解密出来的明文进行处理
			 *  不同的eventType的明文数据格式不同
			 */
			JSONObject plainTextJson = JSONObject.parseObject(plainText);
			String eventType = plainTextJson.getString("EventType");
			/* res是需要返回给钉钉服务器的字符串，一般为success
			 * "check_create_suite_url"和"check_update_suite_url"事件为random字段
			 * 具体请查看文档或者对应eventType的处理步骤
			 */
			String res = "success";
	
			switch (eventType) {
			case "suite_ticket":
				/*"suite_ticket"事件每二十分钟推送一次,数据格式如下
				 * {
					  "SuiteKey": "suitexxxxxx",
					  "EventType": "suite_ticket ",
					  "TimeStamp": 1234456,
					  "SuiteTicket": "adsadsad"
					}
				 */
				Env.suiteTicket = plainTextJson.getString("SuiteTicket");
				//获取到suiteTicket之后需要换取suiteToken，
				String suiteToken = ServiceHelper.getSuiteToken(Env.SUITE_KEY, Env.SUITE_SECRET, Env.suiteTicket);
				/*
				 * ISV应当把最新推送的suiteTicket做持久化存储，
				 * 以防重启服务器之后丢失了当前的suiteTicket
				 * 
				 */
				JSONObject json = new JSONObject();
				json.put("suiteTicket", Env.suiteTicket);
				json.put("suiteToken", suiteToken);
				FileUtils.write2File(json, "ticket");
				break;
			case "tmp_auth_code":
				/*"tmp_auth_code"事件将企业对套件发起授权的时候推送
				 * 数据格式如下
				{
				  "SuiteKey": "suitexxxxxx",
				  "EventType": " tmp_auth_code",
				  "TimeStamp": 1234456,
				  "AuthCode": "adads"
				}			 
				*/
				Env.authCode = plainTextJson.getString("AuthCode");
				Object value = FileUtils.getValue("ticket", "suiteToken");//获取当前的suiteToken
				if (value == null) {
					break;
				}
				String suiteTokenPerm = value.toString();
				/*
				 * 拿到tmp_auth_code（临时授权码）后，需要向钉钉服务器获取企业的corpId（企业id）和permanent_code（永久授权码）
				 */
				CorpAuthSuiteCode corpAuthSuiteCode = ServiceHelper.getPermanentCode(Env.authCode, suiteTokenPerm);
				String corpId = corpAuthSuiteCode.getAuth_corp_info().getCorpid();
				String permanent_code = corpAuthSuiteCode.getPermanent_code();
				/*
				 * 将corpId（企业id）和permanent_code（永久授权码）做持久化存储
				 * 之后在获取企业的access_token时需要使用
				 */
				JSONObject jsonPerm = new JSONObject();
				jsonPerm.put(corpId, permanent_code);
				FileUtils.write2File(jsonPerm, "permanentcode");
				/*
				 * 对企业授权的套件发起激活，
				 */
				ServiceHelper.getActivateSuite(suiteTokenPerm, Env.SUITE_KEY, corpId, permanent_code);
				/*
				 * 获取对应企业的access_token，每一个企业都会有一个对应的access_token，访问对应企业的数据都将需要带上这个access_token
				 * access_token的过期时间为两个小时
				 */
				try {
					AuthHelper.getAccessToken(corpId);
	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(e1.toString());
					e1.printStackTrace();
				}
				break;
			case "change_auth":
				/*"change_auth"事件将在企业授权变更消息发生时推送
				 * 数据格式如下
				{
				  "SuiteKey": "suitexxxxxx",
				  "EventType": " change_auth",
				  "TimeStamp": 1234456,
				  "AuthCorpId": "xxxxx"
				}
				*/
	
				String corpid = plainTextJson.getString("AuthCorpID");
				// 由于以下操作需要从持久化存储中获得数据，而本demo并没有做持久化存储（因为部署环境千差万别），所以没有具体代码，只有操作指导。
				// 1.根据corpid查询对应的permanent_code(永久授权码)
				// 2.调用『企业授权的授权数据』接口（ServiceHelper.getAuthInfo方法），此接口返回数据具体详情请查看文档。
				// 3.遍历从『企业授权的授权数据』接口中获取所有的微应用信息
				// 4.对每一个微应用都调用『获取企业的应用信息接口』（ServiceHelper.getAgent方法）
				/*
				 * 5.获取『获取企业的应用信息接口』接口返回值其中的"close"参数，才能得知微应用在企业用户做了授权变更之后的状态，有三种状态码
				 * 	分别为0，1，2.含义如下：
				 *  0:禁用（例如企业用户在OA后台禁用了微应用） 
				 *  1:正常 (例如企业用户在禁用之后又启用了微应用)
				 *  2:待激活 (企业已经进行了授权，但是ISV还未为企业激活应用) 
				 *  再根据具体状态做具体操作。 
				 */
	
				break;
			case "check_create_suite_url":
				/*"check_create_suite_url"事件将在创建套件的时候推送
				 * {
					  "EventType":"check_create_suite_url",
					  "Random":"brdkKLMW",
					  "TestSuiteKey":"suite4xxxxxxxxxxxxxxx"
					}
				 */
				//此事件需要返回的"Random"字段，
				res = plainTextJson.getString("Random");
				String testSuiteKey = plainTextJson.getString("TestSuiteKey");
				break;
	
			case "check_update_suite_url":
				/* "check_update_suite_url"事件将在更新套件的时候推送
				 * {
					  "EventType":"check_update_suite_url",
					  "Random":"Aedr5LMW",
					  "TestSuiteKey":"suited6db0pze8yao1b1y"
					
					}
				 */
				res = plainTextJson.getString("Random");
				break;
			default: // do something
				break;
			}
	
			/** 对返回信息进行加密 **/
			long timeStampLong = Long.parseLong(timeStamp);
			Map<String, String> jsonMap = null;
			try {
				/*
				 * jsonMap是需要返回给钉钉服务器的加密数据包
				 */
				jsonMap = dingTalkEncryptor.getEncryptedMap(res, timeStampLong, nonce);
			} catch (DingTalkEncryptException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			JSONObject json = new JSONObject();
			json.putAll(jsonMap);
			response.getWriter().append(json.toString());
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
