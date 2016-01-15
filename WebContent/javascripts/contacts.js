dd.config({
			agentId : _config.agentid,
			corpId : _config.corpId,
			timeStamp : _config.timeStamp,
			nonceStr : _config.nonceStr,
			signature : _config.signature,
			jsApiList : [ 'runtime.info', 'biz.contact.choose',
					'device.notification.confirm', 'device.notification.alert',
					'device.notification.prompt', 'biz.ding.post',
					'biz.util.openLink' ]
		});

//alert('contact');
//alert('authcode: ' + info.code);
$.ajax({
	url : 'contactsinfo?corpid='+ _config.corpId,
	type : 'GET',
	success : function(data, status, xhr) {
		alert('data:'+JSON.stringify(data));
		var json = data;
		document.getElementById("contactId").innerHTML = json.clean10.1.name;

	},
	error : function(xhr, errorType, error) {
		logger.e("yinyien:" + _config.corpId);
		alert(errorType + ', ' + error);
	}
});

//dd.ready(function() {
	
	
	
//    dd.biz.navigation.setTitle({
//        title: '钉钉demo',
//        onSuccess: function(data) {
//        },
//        onFail: function(err) {
//            log.e(JSON.stringify(err));
//        }
//    });
////	 alert('dd.ready rocks!');
//
//	dd.runtime.info({
//		onSuccess : function(info) {
//			alert('runtime info: ' + JSON.stringify(info));
//		},
//		onFail : function(err) {
//			logger.e('fail: ' + JSON.stringify(err));
//		}
//	});
//	dd.ui.pullToRefresh.enable({
//	    onSuccess: function() {
//	    },
//	    onFail: function() {
//	    }
//	})
//
//	dd.runtime.permission.requestAuthCode({
//		corpId : _config.corpId,
//		onSuccess : function(info) {
//			alert('authcode: ' + info.code);
//			$.ajax({
//				url : 'contactsinfo?corpid='+ _config.corpId,
//				type : 'GET',
//				success : function(data, status, xhr) {
//					alert('data:'+JSON.stringify(data));
//					var info = JSON.parse(data);
//
//					document.getElementById("contactid").innerHTML = JSON.stringify(info);
//
//				},
//				error : function(xhr, errorType, error) {
//					logger.e("yinyien:" + _config.corpId);
//					alert(errorType + ', ' + error);
//				}
//			});
//
//		},
//		onFail : function(err) {
//			alert('fail: ' + JSON.stringify(err));
//		}
//	});
//});