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
        *{
		padding: 0;
		margin: 0;
		}
		ul {
			list-style: none
		}
		li{
			height: 70px;
			padding: 10px;
			border-bottom: 1px solid #ccc;
			vertical-align: middle;
		}
        
        .icon img {
		height: 70px;
		width: 70px;
		}
		.icon {
			display: inline-block;
			vertical-align: middle;
			/*border: 1px solid #00ff00;*/
		}
		.text {
		margin-left: 10px;
		width: calc(100% - 90px);
		display: inline-block;
		text-align: left;
		line-height: 70px;
		vertical-align: middle;
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
function openLink(url){
	dd.biz.util.openLink({
		url:url,
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
	<div align="center">
		<img id ="userImg" alt="头像" src="./nav/default.png">
	</div>
	<div align="center">
		<span>UserName:</span>
		<div id="userName" style="display:inline-block"></div>
	</div >
	<div align="center">
		<span>UserId:</span>
		<div id="userId" style="display:inline-block"></div>
	</div>
 <ul>
<li>
	<div class="icon"><img src="list/heart1.png"></div>
	<div class="text">ISV接入文档</div>
</li>
<li>
	<div class="icon"><img src="list/heart2.png"></div>
	<div class="text">ISV快速部署</div>
</li>
<li>
	<div class="icon"><img src="list/heart3.png"></div>
	<div class="text">JSAPI</div>
</li>
<li>
	<div class="icon"><img src="list/heart4.png"></div>
	<div class="text">导航框架</div>
</li>
<li>
	<div class="icon"><img src="list/heart5.png"></div>
	<div class="text">go to list</div>
</li>
<li>
	<div class="icon"><img src="list/heart6.png"></div>
	<div class="text">go to drawer</div>
</li>
<li>
  <div class="icon"><img src="list/heart1.png"></div>
  <div class="text">通讯录接口</div>
</li>
</ul>
 <script type="text/javascript">
window.addEventListener('load', function() {
	setTimeout(function(){
	}, 500);
});

	var items = document.querySelectorAll('li');
	var i = 0;
	items[0].addEventListener('click',function(){
 		window.location='http://ddtalk.github.io/dingTalkDoc/#isv接入指南';
	});
	items[1].addEventListener('click',function(){
 		window.location='http://www.dingtalk.com/index-b.html';
	});
	items[2].addEventListener('click',function(){
		openLink('http://h5.m.laiwang.com/home/ding.html');
	});
	items[3].addEventListener('click',function(){
		window.location='./nav/1.html';
	});
	items[4].addEventListener('click', function(){
 		window.location = './list/list.html';
	});
	items[5].addEventListener('click',function(){
 		window.location='./drawer/index.html';
	});
	items[6].addEventListener('click',function(){
		alert('corpid:'+_config.corpId);
 		window.location='./contacts.jsp?corpid='+_config.corpId;
	});	
</script>
 
</body>

</html>
