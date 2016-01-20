# open api demo (java ver.)
java version "1.7.0_75"

## Awesome project

### Getting started

###假如你是ISV 

####创建套件
1.将工程clone到本地：```git clone https://github.com/injekt/openapi-demo-java.git```

2.登录到 http://console.d.aliyun.com/#/dingding/suite 创建套件

3.填写套件信息，

---

IP白名单:  调用钉钉API的合法IP列表  
回调URL:   url为```主机地址/工程名/isvreceive```

---

 | 套件信息   |  如何填写 |
 | ----- | ---- |
| IP白名单 |   调用钉钉API的合法IP列表     |
 |  回调URL   | url为```主机地址/工程名/isvreceive``` |


并将相应的套件信息填写到工程的Env.java
 | 套件信息   |  Env.java对应字段  |
 | ----- | ---- |
| Token |   TOKEN     |
 |   数据加密密钥   |   ENCODING_AES_KEY   |

4.将demo工程部署到服务器上，基本的javaweb环境即可。

5.点击『创建套件』弹窗中的『验证有效性』。具体验证过程，请查看[链接](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/IsvReceiveServlet.java)

6.创建套件成功。

7.创建套件成功之后，得到的SuiteKey和SuiteSecret填写到工程的Env.java中，并**重新部署工程**。

####创建微应用
1.在相应套件下面创建微应用

2.微应用主页地址填写。以部署到阿里云ECS为例，地址为```主机地址/工程名/index.jsp?corpid=$CORPID$```

3.微应用创建成功后，需要把微应用地址改为```主机地址/工程名/index.jsp?corpid=$CORPID$&appid=应用id```

3.在阿里云后台注册测试企业，并在后台为测试企业授权微应用

4.打开钉钉，在钉钉底部工作tab选择测试企业，即可看到微应用，点击进入

5.done

###本DEMO具体实现

1.URL回调流程
请查看[链接](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/IsvReceiveServlet.java)

2.jsapi权限验证配置流程
请查看
- 前端文件:WebContent/index.jsp，WebContent/javascripts/demo.js
- 后端文件:[链接](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/demo/auth/AuthHelper.java)

3.免登流程
请查看
- 前端文件:WebContent/javascripts/demo.js和
- 后端文件:[链接](https://github.com/injekt/openapi-demo-java/blob/master/src/com/alibaba/dingtalk/openapi/servlet/UserInfoServlet.java)



