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

7.创建套件成功之后，得到的SuiteKey和SuiteSecret填写到工程的Env.java中，并重新部署工程。

####创建微应用
1.在相应套件下面创建微应用

2.微应用主页地址填写。以部署到阿里云ECS为例，地址为```主机地址/工程名/index.jsp?corpid=$CORPID$```

3.微应用创建成功后需要把微应用地址改为```主机地址/工程名/index.jsp?corpid=$CORPID$&appid=应用id```

3.注册测试企业授权开通微应用

4.打开钉钉，在钉钉底部工作tab选择测试企业，即可看到微应用，点击进入

5.done

