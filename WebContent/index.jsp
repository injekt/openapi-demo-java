<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="stylesheets/style.css" />
<title>jsapi-demo</title>
</head>

<body onload="form.submit();">
<script language="JavaScript">
var _config = <%= com.alibaba.dingtalk.jsapi.demo.app.getConfig(request.getRequestURL().toString())%>;
</script>
<script language="JavaScript" src="http://g.alicdn.com/ilw/ding/0.3.8/scripts/dingtalk.js">
</script>
<script language="JavaScript" src="javascripts/demo.js">
</script>
    <h1>hello world!</h1>
</body>

</html>
