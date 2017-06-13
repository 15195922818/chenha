window.dataForWeixin = {
  	 appid: '', 
  	 img_url: "http://"+window.location.host + $("#baseUrl").val()+"static/images/share.png",
  	 img_width: '500',
 	 img_height: '500',
   	 link: "http://"+window.location.host + $("#baseUrl").val()+"run",
   	 title: "我在西门子宅人族酷跑游戏中突破3000分，马上抽大奖。你也来吧！",
   	 desc: "我在西门子宅人族酷跑游戏中突破3000分，马上抽大奖。你也来吧！"
};
window.dataForWeixins={
	pre:"我在西门子宅人族酷跑游戏中突破",
	suf:"分，马上抽大奖。你也来吧！",
	callback: "http://"+window.location.host + $("#baseUrl").val()+"/inf"
};


// 当微信内置浏览器完成内部初始化后会触发WeixinJSBridgeReady事件。
(function(){
	var onBridgeReady = function(){
		var WJ = WeixinJSBridge;
		// 发送给好友
		WJ.on('menu:share:appmessage', function() {
			WJ.invoke('sendAppMessage', dataForWeixin, function(res) {
				if( !sended && win ){
					sended = true;
					location.href=dataForWeixins.callback;
				}
			});
		});
		
		// 发送到朋友圈
		WJ.on('menu:share:timeline', function() {
			WJ.invoke('shareTimeline', dataForWeixin, function(res) {
				if( !sended && win ){
					sended = true;
					location.href=dataForWeixins.callback;
				}
			});
		});

		// 发送到微博
		WJ.on('menu:share:weibo', function() {
			WJ.invoke('shareWeibo', dataForWeixin, function(res) {
				dataForWeixin.callback();
			});
		});
		
		WJ.call('showOptionMenu');
		function share(){
			$('#goshare').show();
			//WeixinJSBridge.invoke('shareTimeline');
			//显示右上角三个点按钮
			//WJ.call('showOptionMenu');
		}
		
	};

	if (document.addEventListener) {
		document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
	} else if (document.attachEvent) {
		document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
		document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	}

})();