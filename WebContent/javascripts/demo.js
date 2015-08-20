/**
 * Created by liqiao on 8/10/15.
 */

/**
 * _config comes from server-side template. see views/index.jade
 */
dd.config({
    appId: '',
    corpId: _config.corpId,
    timeStamp: _config.timeStamp,
    nonceStr: _config.nonceStr,
    signature: _config.signature,
    jsApiList: ['device.notification.confirm',
        'device.notification.toast',
        'device.notification.alert',
        'device.notification.prompt',
        'biz.chat.chooseConversation',
        'biz.ding.post']
});

dd.ready(function() {
    alert('dd ready');

    document.addEventListener('pause', function() {
        alert('pause');
    });

    document.addEventListener('resume', function() {
        alert('resume');
    });

    var head = document.querySelector('h1');
    head.innerHTML = head.innerHTML + ' It rocks!';

    dd.device.notification.toast({
        icon: '', //icon样式，有success和error，默认为空 0.0.2
        text: 'this is text', //提示信息
        duration: 3, //显示持续时间，单位秒，默认按系统规范[android只有两种(<=2s >2s)]
        delay: 0, //延迟显示，单位秒，默认0
        onSuccess : function(result) {
            /*{}*/
        },
        onFail : function(err) {}


    })


    dd.device.notification.alert({
        message: 'dd.device.notification.alert',
        title: 'This is title',
        buttonName: 'button',
        onSuccess: function(data) {
            alert('win: ' + JSON.stringify(data));
        },
        onFail: function(err) {
            alert('fail: ' + JSON.stringify(err));
        }
    });
});

dd.error(function(err) {
    alert('dd error: ' + JSON.stringify(err));
});