<!DOCTYPE html>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=utf-8"%>
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<link type="text/css" rel="stylesheet" href="stylesheets/style.css" />
    <style>
        body {
            background-color: burlywood;
        }
    </style>

<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">

<title>index</title>
<script type="text/javascript">
var _config = <%= com.alibaba.dingtalk.openapi.demo.auth.AuthHelper.getConfig(request) %>;
</script>
<script type="text/javascript" src="javascripts/zepto.min.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/ilw/ding/0.7.3/scripts/dingtalk.js">
</script>
<script type="text/javascript" src="javascripts/logger.js">
</script>
<script type="text/javascript" src="javascripts/demo.js">
 
</script>
<script>
function openLink(){
	dd.biz.util.openLink({
		url:'http://h5.m.laiwang.com/home/ding.html',
	    onSuccess : function(result) {
	    },
	    onFail : function(err) {
	    	alert(JSON.stringify(err));
	    }
	});
}

</script>

</head>

<body >
	<div>
		<img id ="userImg" alt="头像" src="./nav/default.png">
	</div>
	<div>
		<span>UserName:</span>
		<div id="userName" style="display:inline-block"></div>
	</div>
	<div>
		<span>UserId:</span>
		<div id="userId" style="display:inline-block"></div>
	</div>
	
	<div><button onclick="openLink()">JSAPI</button></div>
	<div><button onclick="window.location='./nav/1.html'">导航框架</button></div>
	<div><button onclick="window.location='./list/list.html'">go to list</button></div>
	
	<div><button onclick="window.location='http://ddtalk.github.io/dingTalkDoc'">go to doc</button></div>
	
    <div class="clear-float"/>
    <div id="dd"></div>

</body>

</html>
