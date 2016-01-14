<!DOCTYPE html>
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

<!--  <script type="text/javascript" src="javascripts/dingtalk.js">
</script>
 --> <script type="text/javascript" src="javascripts/logger.js">
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
	<span>UserName:</span>
	<div id="userName"></div>
	<div><button onclick="openLink()">JSAPI</button></div>
	<div><button onclick="window.location='./nav/1.html'">go to nav</button></div>
	<div><button onclick="window.location='./list/list.html'">go to list</button></div>
	
    <div class="clear-float"/>
    <div id="dd"></div>

</body>

</html>
