package com.alibaba.dingtalk.openapi.demo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.dingtalk.openapi.demo.auth.AuthHelper;
import com.alibaba.dingtalk.openapi.demo.department.Department;
import com.alibaba.dingtalk.openapi.demo.department.DepartmentHelper;
import com.alibaba.dingtalk.openapi.demo.media.MediaHelper;
import com.alibaba.dingtalk.openapi.demo.message.ConversationMessageDelivery;
import com.alibaba.dingtalk.openapi.demo.message.ImageMessage;
import com.alibaba.dingtalk.openapi.demo.message.LightAppMessageDelivery;
import com.alibaba.dingtalk.openapi.demo.message.LinkMessage;
import com.alibaba.dingtalk.openapi.demo.message.MessageHelper;
import com.alibaba.dingtalk.openapi.demo.message.OAMessage;
import com.alibaba.dingtalk.openapi.demo.message.TextMessage;
import com.alibaba.dingtalk.openapi.demo.user.User;
import com.alibaba.dingtalk.openapi.demo.user.UserHelper;

public class Demo {
	
	public static void main(String[] args) {
		
		try {
			// 获取access token
			String accessToken = AuthHelper.getAccessToken();
			log("成功获取access token: ", accessToken);
			
			// 获取jsapi ticket
			String ticket = AuthHelper.getJsapiTicket(accessToken);
			log("成功获取jsapi ticket: ", ticket);
			
			// 获取签名
			String nonceStr = "nonceStr";
			long timeStamp = System.currentTimeMillis();
			String url = "http://www.dingtalk.com";
			String signature = AuthHelper.sign(ticket, nonceStr, timeStamp, url);
			log("成功签名: ", signature);
			
			//创建部门
			String name = "TestDept.16";
			String parentId = "1";
			String order = "1";
			long departmentId = DepartmentHelper.createDepartment(accessToken, 
					name, parentId, order);
			log("成功创建部门", name, " 部门id=", departmentId);
			
			//获取部门列表
			List<Department> list = DepartmentHelper.listDepartments(accessToken);
			log("成功获取部门列表", list);
			
			//更新部门
			DepartmentHelper.updateDepartment(accessToken, name, parentId, order, departmentId);
			log("成功更新部门"," 部门id=", departmentId);
			
			//创建成员
			User user = new User("id_yuhuan", "name_yuhuan");
			user.email = "yuhuan@abc.com";
			user.mobile = "18645512324";
			user.department = new ArrayList();
			user.department.add(departmentId);
			UserHelper.createUser(accessToken, user);
			log("成功创建成员","成员信息=", user);
			
			//上传图片
			File file = new File("/Users/liqiao/Desktop/icon.jpg");
			MediaHelper.MediaUploadResult uploadResult = 
					MediaHelper.upload(accessToken, MediaHelper.TYPE_IMAGE, file);
			log("成功上传图片", uploadResult);
			
			//下载图片
			String fileDir = "/Users/liqiao/Desktop/";
			MediaHelper.download(accessToken, uploadResult.media_id, fileDir);
			log("成功下载图片");
			
			TextMessage textMessage = new TextMessage("TextMessage");
			ImageMessage imageMessage = new ImageMessage(uploadResult.media_id);
			LinkMessage linkMessage = new LinkMessage("http://www.baidu.com", "@lALOACZwe2Rk", 
					"Link Message", "This is a link message");
			
			//创建oa消息
			OAMessage oaMessage = new OAMessage();
			oaMessage.message_url = "http://www.dingtalk.com";
			OAMessage.Head head = new OAMessage.Head();
			head.bgcolor = "FFCC0000";
			oaMessage.head = head;
			OAMessage.Body body = new OAMessage.Body();
			body.title = "征婚启事";
			OAMessage.Body.Form form1 = new OAMessage.Body.Form();
			form1.key = "姓名";
			form1.value = "刘增产";
			OAMessage.Body.Form form2 = new OAMessage.Body.Form();
			form2.key = "年龄";
			form2.value = "18";
			body.form = new ArrayList();
			body.form.add(form1);
			body.form.add(form2);
			OAMessage.Body.Rich rich = new OAMessage.Body.Rich();
			rich.num = "5";
			rich.unit = "毛";
			body.rich = rich;
			body.content = "这是一则严肃的征婚启事。不约。";
			body.image = "";
			body.file_found = "3";
			body.author = "识器";
			oaMessage.body = body;
			
			//发送微应用消息
			String toUsers = Vars.TO_USER;
			String toParties = Vars.TO_PARTY;
			String agentId = Vars.AGENT_ID;
			LightAppMessageDelivery lightAppMessageDelivery = 
					new LightAppMessageDelivery(toUsers, toParties, agentId);
			
			lightAppMessageDelivery.withMessage(textMessage);
			MessageHelper.send(accessToken, lightAppMessageDelivery);
			log("成功发送 微应用文本消息");
			lightAppMessageDelivery.withMessage(imageMessage);
			MessageHelper.send(accessToken, lightAppMessageDelivery);
			log("成功发送 微应用图片消息");			
			lightAppMessageDelivery.withMessage(linkMessage);
			MessageHelper.send(accessToken, lightAppMessageDelivery);
			log("成功发送 微应用link消息");
			lightAppMessageDelivery.withMessage(oaMessage);
			MessageHelper.send(accessToken, lightAppMessageDelivery);
			log("成功发送 微应用oa消息");
			
			//发送会话消息
			String sender = Vars.SENDER;
			String cid = Vars.CID;
			ConversationMessageDelivery conversationMessageDelivery = 
					new ConversationMessageDelivery(sender, cid, agentId);
			
			conversationMessageDelivery.withMessage(textMessage);
			MessageHelper.send(accessToken, conversationMessageDelivery);
			log("成功发送 会话文本消息");
			conversationMessageDelivery.withMessage(imageMessage);
			MessageHelper.send(accessToken, conversationMessageDelivery);
			log("成功发送 会话图片消息");			
			conversationMessageDelivery.withMessage(linkMessage);
			MessageHelper.send(accessToken, conversationMessageDelivery);
			log("成功发送 会话link消息");
			
			//更新成员
			user.mobile = "18612341234";
			UserHelper.updateUser(accessToken, user);
			log("成功更新成员","成员信息=", user);
			
			//获取成员
			UserHelper.getUser(accessToken, user.userid);
			log("成功获取成员","成员userid=", user.userid);
			
			//获取部门成员
			List<User> userList = UserHelper.getDepartmentUser(accessToken, departmentId, 0);
			log("成功获取部门成员","部门成员user=", userList);
			
			//获取部门成员（详情）
			List<User> userList2 = UserHelper.getUserDetails(accessToken, departmentId, 0);
			log("成功获取部门成员详情","部门成员详情user=", userList2);
			
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
			UserHelper.batchDeleteUser(accessToken, useridlist);
			log("成功批量删除成员","成员列表useridlist=",useridlist);
			
			//删除成员
			User user3 = new User("id_yuhuan3", "name_yuhuan3");
			user3.email = "yuhua2n@abc.com";
			user3.mobile = "18611111111";
			user3.department = new ArrayList();
			user3.department.add(departmentId);
			UserHelper.createUser(accessToken, user3);
			UserHelper.deleteUser(accessToken, user3.userid);
			log("成功删除成员","成员userid=", user3.userid);
			
			//删除部门
			DepartmentHelper.deleteDepartment(accessToken, departmentId);
			log("成功删除部门"," 部门id=", departmentId);
			
		}
		catch (OApiException e) {
			e.printStackTrace();
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
