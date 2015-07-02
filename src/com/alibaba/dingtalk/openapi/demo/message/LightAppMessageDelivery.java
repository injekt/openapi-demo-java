package com.alibaba.dingtalk.openapi.demo.message;

import com.alibaba.fastjson.JSONObject;

public class LightAppMessageDelivery extends MessageDelivery {

	public String touser;
	public String toparty;
	public String agentid;
	
	public LightAppMessageDelivery(String toUsers, String toParties, String agentId) {
		this.touser = toUsers;
		this.toparty = toParties;
		this.agentid = agentId;
	}
	
	@Override
	public JSONObject toJsonObject() {
		JSONObject json = super.toJsonObject();
		json.put("touser", this.touser);
		json.put("toparty", this.toparty);
		json.put("agentid", this.agentid);
		
		return json;
	}
}
