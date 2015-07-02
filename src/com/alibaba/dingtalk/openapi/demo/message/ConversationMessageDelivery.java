package com.alibaba.dingtalk.openapi.demo.message;

import com.alibaba.fastjson.JSONObject;

public class ConversationMessageDelivery extends MessageDelivery {

	public String sender;
	public String cid;
	public String agentid;
	
	public ConversationMessageDelivery(String sender, String cid, 
			String agentId) {
		this.sender = sender;
		this.cid = cid;
		this.agentid = agentId;
	}
		
	public JSONObject toJsonObject() {
		JSONObject json = super.toJsonObject();
		json.put("sender", this.sender);
		json.put("cid", this.cid);
		json.put("agentid", this.agentid);
		
		return json;
	}
}
