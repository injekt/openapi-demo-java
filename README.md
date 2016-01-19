# open api demo (java ver.)
java version "1.7.0_75"

## Awesome project

### Getting started



###假如你是ISV 

####创建套件
1.git clone https://github.com/injekt/openapi-demo-java.git

2.登录到 '''http://console.d.aliyun.com/#/dingding/suite'''创建套件

3.填写套件信息，并将相应的套件信息填写到工程的Env.java

4.将demo工程部署到服务器上

5.点击『创建套件』弹窗中的『验证有效性』

6.创建套件成功。

7.把创建套件成功之后，得到的suitekey填写到工程的Env.java，并重新部署工程。

####创建微应用



###假如你是普通企业

1. 在Env.java中配置所需要的参数，

2. 在eclipse中运行demo

3. 查看“权限验证配置”的demo,请从WebContent/index.jsp开始看

4. 查看回调接口加解密demo，请从IsvReceiveServlet.java开始看

5. 查看通讯录事件回调demo，请从EventChangeReceiveServlet.java开始查看
