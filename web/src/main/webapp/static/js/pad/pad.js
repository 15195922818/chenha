(function(){
	window.resizeWindow = function(){
		var width = $(window).width();
		var height = $(window).height();
		var unit = 0;
		var nuit_margin = 0;
		if( width > height ){
			unit = Math.floor(height * 0.9);
			nuit_margin = Math.floor(height * 0.05);
		}else{
			unit = Math.floor(width * 0.9);
			nuit_margin = Math.floor(width * 0.05);
		}
		// 外部wrap的宽高样式
		style = ".wrap_box{width:unitpx;height:unitpx;top:nuit_marginpx;}"
				.replace("nuit_margin",nuit_margin).replace("unit",unit).replace("unit",unit);

		var pic_wrap_height = Math.floor(unit * 0.7);
		var pic_wrap_width = unit - 70;
		//图片wrap的宽高样式 H 70% 宽  margin 0 35px;
		var style_pic_wrap = ".pic_wrap{width:pic_wrap_widthpx;height:pic_wrap_heightpx;}"
				.replace("pic_wrap_height",pic_wrap_height).replace("pic_wrap_width",pic_wrap_width);
		style += style_pic_wrap;

		var pic_title_height =  Math.floor(unit * 0.08);
		var style_pic_title = ".wrap_box .pic_title{height:pic_title_heightpx;line-height:pic_title_heightpx;}"
								.replace("pic_title_height",pic_title_height)
								.replace("pic_title_height",pic_title_height);
		style += style_pic_title;

		var pic_head_comment_height = Math.floor(unit * 0.11);
		var style_pic_head_comment = ".wrap_box > h1{line-height:pic_head_comment_heightpx;}"
									 .replace("pic_head_comment_height",pic_head_comment_height);
			style_pic_head_comment += ".wrap_box .pic_comment{height:pic_head_comment_heightpx;}"
									 .replace("pic_head_comment_height",pic_head_comment_height);
			style_pic_head_comment += ".wrap_box .pic_comment .pic_up{width:pic_head_comment_heightpx;height:pic_head_comment_heightpx;}"
									 .replace("pic_head_comment_height",pic_head_comment_height)
									 .replace("pic_head_comment_height",pic_head_comment_height);
		var comment_width = unit - pic_head_comment_height -1;
			style_pic_head_comment += ".wrap_box .pic_comment .pic_com_detail{height:pic_head_comment_heightpx;line-height:pic_head_comment_heightpx;width:comment_widthpx;}"
									 .replace("pic_head_comment_height",pic_head_comment_height)
									 .replace("pic_head_comment_height",pic_head_comment_height)
									 .replace("comment_width",comment_width);
			style_pic_head_comment += ".wrap_box .pic_comment .pic_com_detail .comment_wrap li.first{width:firstlipx;}"
							.replace("firstli",comment_width-10);
			style += style_pic_head_comment;

		var pic_up_height = Math.floor(pic_head_comment_height * 0.7);
		var pic_up_num_height = Math.floor(pic_head_comment_height * 0.2);
		var style_pic_up = ".wrap_box .pic_comment .pic_up > h1{line-height:pic_up_heightpx;}"
							.replace("pic_up_height",pic_up_height);
			style_pic_up += ".wrap_box .pic_comment .pic_up > p{line-height:pic_up_num_heightpx;}"
							.replace("pic_up_num_height",pic_up_num_height);

			style += style_pic_up;

		$(document.getElementsByTagName('HEAD').item(0))
			.append('<style rel="stylesheet">'+style+'</style>');


	};
}).call(window);

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
			if(parent.is('.pic_selcet_wrap')){
				$(imgObj).height($newheight).width($newwidth);
				parent.height($newheight).width($newwidth).css({'left':left+'px','top':top+'px'});
				var max = ($newheight > $newwidth)?$newwidth:$newheight;
			}else{
				$(imgObj).height($newheight).width($newwidth).css({'margin-left':left+'px','top':top+'px'});
				$('#picTitle').css({'left':(left+20)+'px'});
				$('p.des').width($newwidth).css({'left':(left)+'px'});
				$('div.pic_comment').width($newwidth).css({'left':(left)+'px'});
				$('#picTId').css({'right':(left+20)+'px'});
			}
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
			if( option.id && option.active_id && option.url ){
				this.queryInit();
			}
		};
		prop.queryInit = function(){
			this.queryData();
		};
		prop.queryData = function(){
			console.info("query" + new Date().getTime());
			if(this.timer){
				clearTimeout(this.timer);
			}
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
				}
			}
			var timemillions = new Date().getTime();
			var needRefresh = ( Math.floor(( timemillions - this.option.lastRefresh) / 60) > this.option.refreshTime )? true : false;
			if( this.option.lastRefresh != 0 && needRefresh){
				 this.option.lastRefresh = new Date().getTime();
				this.option.refreshFunc.call(window);
			}
		};
		prop.loadData = function(data){
			if( data.topMsg.length > 0 ){
				this.loadTopMsg(data.topMsg);
			}
			if( data.Msg.length > 0 ){
				this.loadNormalMsg(data);
				this.staticData(data.Msg);
			}
			$('ul.comment_wrap > li').each(function(){
				$(this).attr("ml","-"+$(this).width()+"px")
					   .attr("width",$(this).width());
			});
			
			if( this.option.page == 0 ){
				var self = this;
				var cur = $('ul.comment_wrap > li:eq(0)');
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
			//this.option.page ++;
			
			
			if( data.removedMsg){
				this.removeData(data.removedMsg);
			}

		};
		prop.loadTopMsg = function(data){
			var html = "";
			for( var i = 0 ; i < data.length ;i++ ){
				html += "<li><p from='"+data[i].openid+"'>"+data[i].piccomment+"</p><span>来自："+data[i].openid+"</span></li>";
			}
			$(html).insertAfter($('ul.comment_wrap > li.current'));

		};
		prop.loadNormalMsg = function(datas){
			var html = "";
			var data = datas.Msg;
			for( var i = 0 ; i < data.length ;i++){
				if(data[i].wallstate == "1")
					html += "<li><p from='"+data[i].openid+"'>"+data[i].piccomment+"</p><span>来自："+data[i].openid+"</span></li>";
			}
			$('ul.comment_wrap').append(html);
		};
		prop.removeData = function(data){

		};
		prop.staticData = function(data){

		};
		prop.removeMsg = function(data){

		};
		prop.cicle = function(){
			var self = this;
			var time = (self.option.ajaxSpeed - 0) * 1000;
			console.info(time);
			this.timer = setTimeout(function(){
				self.queryData();
			}, time);	
		};
		prop.swing = function(){
			var self = this;
			var cur = $('ul.comment_wrap > li.current').next();

			$('ul.comment_wrap > li.current').remove();
			cur.addClass("current");
			var time = Math.ceil(cur.attr('width')/self.option.speed) *1000;
			cur.animate(
				{"margin-left":cur.attr('ml')},
				time,'linear',
				function(){
					self.swing();
				}
			);
			
			/*
			setTimeout(function(){
				var cur = $('ul.comment_wrap > li.current');
				var next = cur.next();
				next.addClass("current");
				cur.removeClass("current").css("margin-left",next.attr('ml'));
				cur.prev().remove();
				self.swing();
			}, 3000);*/
		};
		return queue;
	})();

	window.MsgQueue = queue;
}).call(this);





