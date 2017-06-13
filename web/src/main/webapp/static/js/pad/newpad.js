/*图片resize方法:ImageResize*/
(function(){
	var ImageResize = (function(){
		var ImageResize = function(option){
			this.imgObj = option;
			this.init();
		};
		ImageResize.prototype.init = function(){
			var _this = this;
		    $(_this.imgObj)[0].onload = function(){
					_this.resize();
		    	 	this.onload = null;
		    	 	_this = null;
		    };
		};
		ImageResize.prototype.resize = function (){
			var _this = this;
			var imgObj = $(_this.imgObj);
			var top = 0; 
			var left = 0;
			var parent = $(imgObj).parent();
			this.picW  = $(imgObj).width();/*图原始宽*/
			var $newwidth = this.picW;
			this.picH = $(imgObj).height();/*图原始高*/
			var $newheight = this.picH;
			var $w = $(parent).width();/*最小宽度*/
			var $h = $(parent).height();/*最大宽度*/
			/*计算图片比例高度*/
			var $A = $w/$h;
			var $A1 = $newwidth/$newheight;
			if( $A1 > $A ){
				$newwidth = $w;
				$newheight = $w/$A1;
				top = parseInt(($h-$newheight)/2);
			}else if( $A1 < $A ){
				$newheight = $h;
				$newwidth = $h*$A1;
				left = parseInt(($w-$newwidth)/2);
			}else if( $A1 == $A ){
				$newwidth = $w;
				$newheight = $h;
			}
			$newwidth = parseInt($newwidth);/*得到的自适应宽度*/
			$newheight = parseInt($newheight);/*得到的自适应高度*/

			$(imgObj).height($newheight).width($newwidth);
			$(imgObj).parent('.pic_wrap').height($newheight).width($newwidth).css({'margin-left':left+'px','top':top+'px'});
			$(imgObj).parent().find('.picTitle').css({'left':'20px'});
			$(imgObj).parent().find('p.des').width($newwidth).css({'left':(left)+'px'});
			//$(imgObj).parent().find('div.pic_comment').width($newwidth).css({'left':(left)+'px'});
			$(imgObj).parent().find('.picTId').css({'right':'20px'});
		};
		return ImageResize;
	})();
	this.ImageResize = ImageResize;
}).call(window);
$(function(){
	$.fn.ImageResize = function(){
		var arr = new Array();
		$(this).each(function(){
			arr.push(new ImageResize(this));
		});
		return arr;
	};
});

/*大屏滚动方法*/
(function(){
	var queue = (function(){
		var queue = function(option){
			this.option = $.extend({},queue.defaults,option);
			this.option.lastRefresh = new Date().getTime();
			this.init();
		};
		queue.defaults = {
			page:0,
			pageSize:20,
			speed:20,
			none:false,
			refreshTime:5,
			lastRefresh:0,
			ajaxSpeed:5
		};
		var prop = queue.prototype;
		prop.init = function() {
			var option = this.option;
			this.stop = true;
			this.id = this.option.id;
			if( option.id && option.active_id && option.url ){
				this.queryInit();
			}
		};
		prop.queryInit = function(){
			this.queryData();
		};
		prop.queryData = function(){
			//console.info("query" + new Date().getTime());
			if(this.timer){
				clearTimeout(this.timer);
			}
			if(this.stop) return;
			var id = this.option.id;
			var active_id = this.option.active_id;
			var url = this.option.url;
			var self = this;
			$.ajax({
				'url':url,
				'type':'post',
				data:{
					'activyId':active_id,
					'picId':id,
					'page':self.option.page,
					'rows':self.option.pageSize
				},
				'dataType':'json',
				success:function(data){
					self.initParam(data.staticParam);
					if( data.topMsg.length > 0 || data.Msg.length > 0 ){
						if( self.none == true ){
							self.none = false;
						}
						self.loadData(data);
						self.option.page += data.Msg.length;
						self.cicle();
					}else{
						self.option.page = 0;
						if( self.none == false ){
							self.none = true;
							self.queryData();
							return;
						}else{
							self.cicle();
						}
					}
					
				},
				error:function(jqXhr,status, error){
				}
			});
		};
		prop.initParam = function(arr){
			for(var i = 0 ; i < arr.length ; i ++){
				if( arr[i].param_attr == "1001" ){//查询页面图片是否改变
					this.option.refreshTime = arr[i].para_code1;
				}else if( arr[i].param_attr == "1002" ){//播放速度
					this.option.speed = arr[i].para_code1;
				}else if( arr[i].param_attr == "1003" ){//请求间隔
					this.option.ajaxSpeed = arr[i].para_code1;
				}else if( arr[i].param_attr == "1004" ){//图片切换时间
					this.option.parent.option.screenRefresh = arr[i].para_code1;
				}
			}
			this.option.parent.askFresh();
			var timemillions = new Date().getTime();
			var needRefresh = ( Math.floor(( timemillions - this.option.lastRefresh) / 1000) > this.option.refreshTime )? true : false;
			/*if( this.option.lastRefresh != 0 && needRefresh){
				 this.option.lastRefresh = new Date().getTime();
				this.option.refreshFunc.call(window);
			}*/
		};
		prop.loadData = function(data){
			if( data.topMsg.length > 0 ){
				this.loadTopMsg(data.topMsg);
			}
			if( data.Msg.length > 0 ){
				this.loadNormalMsg(data);

			}
			$('#'+this.id+' ul.comment_wrap > li').each(function(){
				$(this).attr("ml","-"+($(this).width()+60)+"px")
					   .attr("width",($(this).width()+60));
			});
			
			if( this.option.page == 0 ){
				var self = this;
				var cur = $('#'+this.id+' ul.comment_wrap > li:eq(0)');
				cur.addClass("current");
				var time = Math.ceil(cur.attr('width')/50) *1000;
				cur.animate(
					{"margin-left":cur.attr('ml')},
					time,'linear',
					function(){
						self.swing();
					}
				);
			}

			if( data.removedMsg){
				this.removeData(data.removedMsg);
			}

		};
		prop.loadTopMsg = function(data){
			var html = "";
			for( var i = 0 ; i < data.length ;i++ ){
				html += "<li><p from='"+data[i].openid+"'>"+data[i].piccomment+"</p><span>来自："+data[i].openid+"</span></li>";
			}
			$(html).insertAfter($('#'+this.id+' ul.comment_wrap > li.current'));

		};
		prop.loadNormalMsg = function(datas){
			var html = "";
			var data = datas.Msg;
			for( var i = 0 ; i < data.length ;i++){
				if(data[i].wallstate == "1")
					html += "<li><p from='"+data[i].openid+"'>"+data[i].piccomment+"</p><span>来自："+data[i].openid+"</span></li>";
			}
			$('#'+this.id+' ul.comment_wrap').append(html);
		};
		prop.cicle = function(){
			if( this.stop ) {
				clearTimeout(this.timer);
				return;
			}
			var self = this;
			var time = (self.option.ajaxSpeed - 0) * 1000;
			//console.info(time);
			this.timer = setTimeout(function(){
				self.queryData();
			}, time);	
		};
		prop.swing = function(){
			//var styleStr = " -webkit-transition: all times linear 0s;-moz-transition: all times linear 0s;-ms-transition: all times linear 0s;-o-transition: all times linear 0s;transition: all times linear 0s;"
			var self = this;
			var cur = $('#'+this.id+' ul.comment_wrap > li.current').next();
			$('ul.comment_wrap > li.current').remove();
			cur.addClass("current");
			var time = Math.ceil(cur.attr('width')/self.option.speed) *1000;
			if( !this.stop && cur.length > 0 ){
				/*cur[0].style.csstext += styleStr.replace("time",time).replace("time",time).replace("time",time).replace("time",time);
				cur.css({"margin-left":cur.attr('ml')});
				setTimeout(function(){
					self.swing();
				},time);*/
				cur.animate(
					{"margin-left":cur.attr('ml')},
					time,'linear',
					function(){
						self.swing();
					}
				);
			}

		};
		prop.Stop = function(){
			this.stop = true;
		};
		prop.start = function(){
			this.stop = false;
			this.swing();
			this.cicle();
		};
		return queue;
	})();

	window.MsgQueue = queue;
}).call(this);

(function(){
	var newQueue = (function(){

		var newQueue = function(option){
			this.option = $.extend({},newQueue.defaults,option);
			this.option.lastScreenRefresh = new Date();
			this.cur = 0;
			this.init();
		};
		newQueue.defaults = {
			screenData:{},
			page:0,//当前页数
			pageSize:20,//每页多少条
			speed:20,//播放速度
			none:false,
			refreshTime:5,//
			lastRefresh:0,//上次刷新时间
			ajaxSpeed:5,//ajax请求间隔
			screenRefresh:20,
			lastScreenRefresh:0
		};
		var prop = newQueue.prototype;
		prop.init = function(){
			var self = this;
			var list = $(this.option.target);
			var queueList = new Array();
			for( var i = 0 ; i < this.option.screenData.weixinScreenPicList.length ; i++ ){
				var data = this.option.screenData.weixinScreenPicList[i];
				queueList.push(new MsgQueue({
					url:this.option.url,
					id:data.picTypeId,
					active_id:'1',
					parent:self
				}));
			}
			this.queueList = queueList;
			var cur = this.cur;
			$("#"+queueList[cur].id).addClass('active');
			queueList[cur].start();

		};
		prop.next = function(){
			var cur = this.cur;
			var next = (cur < this.queueList.length - 1) ? ( cur + 1 ) : 0 ;
			console.info("cur:"+cur);
			console.info("next:"+next);
			$(this.option.target).children('li').removeClass('active');
			this.queueList[cur].Stop();
			this.queueList[next].start();
			$("#"+this.queueList[next].id).addClass('active');
			this.cur = next;
		};
		prop.askFresh = function(){
			var timemillions = new Date().getTime();
			var needRefresh = ( Math.floor(( timemillions - this.option.lastScreenRefresh) / 1000) > this.option.screenRefresh )? true : false;
			if( needRefresh ){
				this.option.lastScreenRefresh = new Date();
				this.next();
			}

		};
		return newQueue;
	})();

	window.newQueue = newQueue;
})();




