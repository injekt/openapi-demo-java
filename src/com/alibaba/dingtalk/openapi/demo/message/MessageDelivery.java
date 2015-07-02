package com.alibaba.dingtalk.openapi.demo.message;

import com.alibaba.fastjson.JSONObject;

public class MessageDelivery {
	
	public String msgtype;
	public Message message;
	
	public MessageDelivery withMessage(Message msg) {
		this.msgtype = msg.type();
		this.message = msg;
		return this;
	}
	
	public JSONObject toJsonObject() {
		JSONObject json = new JSONObject();
		json.put("msgtype", this.msgtype);
		json.put(this.msgtype, this.message);
		return json;
	}
}
