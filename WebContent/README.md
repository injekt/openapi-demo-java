# open api demo (java ver.)
java version "1.7.0_75"

## Getting Started

###创建套件前
1.将工程clone到本地：```git clone https://github.com/injekt/openapi-demo-java.git```，在eclipse点击```File->import```导入到eclipse中

2.登录到 http://console.d.aliyun.com/#/dingding/suite 创建套件（需要先注册开发者账号和钉钉企业才能创建套件）
###创建套件

![cjtj](https://gw.alicdn.com/tps/TB1IBkFLpXXXXc0XpXXXXXXXXXX-717-597.jpg)

3.填写套件信息，

其中：

- Token:  可以随意填写，填写完之后，打开工程的src/com/alibaba/dingtalk/openapi/demo/Env.java文件，把Token的值复制给Env.TOKEN
- 数据加密密钥：点击自动生成，然后打开工程的src/com/alibaba/dingtalk/openapi/demo/Env.java文件，把值复制给给Env.ENCODING_AES_KEY
- IP白名单:  调用钉钉API的合法IP列表(例如，工程部署在ip地址为123.56.71.118的主机上，那就填写"123.56.71.118")
- 回调URL:   url为```工程地址/isvreceive```(例如，工程将部署在ip地址为123.56.71.118的主机上，端口为8080，工程导出的war包包名为"ian_demo1"，那么我的回调URL即为：```http://123.56.71.118:8080/ian_demo1/isvreceive```，假如你有域名的话，也可以把IP地址换成域名)，注：请在工程部署成功之后再点击『验证有效性』

4.配置服务器java环境，在官方网站下载JCE无限制权限策略文件 

- JDK6的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html

- JDK7的下载地址：http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html

下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt。如果安装的是JRE，将两个jar文件放到%JRE_HOME% \lib\security目录下覆盖原来的文件，如果安装的是JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件。


5.将demo工程部署到服务器上（例如是tomcat的话，将war包打出，复制到tomcat的webapps目录下就完成了部署）

6.部署成功之后，点击『创建套件』弹窗中的『验证有效性』。

具体是如何验证回调URL有效性的，请查看[链接](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/IsvReceiveServlet.java)

7.创建套件成功。

![suitea](https://img.alicdn.com/tps/TB1xGrpLpXXXXXMaXXXXXXXXXXX-1227-239.jpg)

<br/>
8.创建套件成功之后，将得到的SuiteKey和SuiteSecret填写到工程的[Env.java](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/demo/Env.java)中，并**重新部署工程**。

###创建微应用
1.进入套件『管理』点击『创建微应用』

2.微应用主页地址填写。地址为```工程地址/index.jsp?corpid=$CORPID$```，(例如，工程部署在ip地址为123.56.71.118的主机上，端口为8080，工程导出的war包包名为"ian_demo1"，那么微应用地址即为：```http://123.56.71.118:8080/ian_demo1/index.jsp?corpid=$CORPID$```，假如你有域名的话，也可以把IP地址换成域名)

3.微应用创建成功后，需要把微应用地址改为```主机地址/工程名/index.jsp?corpid=$CORPID$&appid=你的应用id```

![miapp](https://img.alicdn.com/tps/TB1Z0HzLpXXXXc0XFXXXXXXXXXX-1193-132.jpg)

3.点击开发者后台左边栏『开发环境』，注册测试企业，注册完成后，点击『登录管理』到```oa.dingtalk.com```完成测试企业的激活

4.测试企业激活完成后，进入套件『管理』，在页面底部选择要授权的测试企业进行授权

5.打开钉钉，进入对应企业，即可看到微应用，点击进入


###本DEMO具体实现

1.URL回调流程

请查看[文档](http://ddtalk.github.io/dingTalkDoc/#2-回调接口（分为五个回调类型）)
- 后端文件：[链接](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/IsvReceiveServlet.java)

2.jsapi权限验证配置流程

请查看[文档](http://ddtalk.github.io/dingTalkDoc/#页面引入js文件)
- 前端文件:WebContent/index.jsp，WebContent/javascripts/demo.js
- 后端文件:[链接](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/demo/auth/AuthHelper.java)

3.免登流程

请查看[文档](http://ddtalk.github.io/dingTalkDoc/#手机客户端微应用中调用免登)
- 前端文件:WebContent/javascripts/demo.js和
- 后端文件:[链接](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/UserInfoServlet.java)



