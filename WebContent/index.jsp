<!DOCTYPE html>
<html>
<head>
<link type="text/css" rel="stylesheet" href="stylesheets/style.css" />
<title>jsapi-demo</title>
</head>

<body >
<script type="text/javascript">
var _config = <%= com.alibaba.dingtalk.openapi.demo.auth.AuthHelper.getConfig(request.getRequestURL().toString(),request.getQueryString()) %>;
</script>
<script type="text/javascript" src="javascripts/zepto.min.js"></script>
<script type="text/javascript" src="http://g.alicdn.com/ilw/ding/0.3.8/scripts/dingtalk.js">
</script>
<script type="text/javascript" src="javascripts/logger.js"></script>
<script type="text/javascript" src="javascripts/demo.js">
</script>
</body>

</html>
