var wall = wall||{};
	wall.setting = new Array();
	wall.newQueue = new Array();
	wall.signers = new Array();
	wall.spinTimeOut = 0; // 轮循定时器
	wall.loadTimeOut = 0;
	wall.titleTimeOut = 0;
	wall.nowAt = 0;
	wall.onWall = 0;
	wall.under_at = 0; 
	wall.HideTimes = 4000; // 轮循隐藏时间
	wall.cycleSwitch = 0; // 轮循开关，0-开启，1-关闭
	wall.displayNum = 3; // 展示消息条数，默认为3
	wall.cycleTime = 5; // 翻页间隔，默认5秒
	wall.titleCycleSwitch = 0; // 标题轮播开关:0-开启；1-关闭
	wall.titleText = ""; // 消息轮播文字
	wall.dataSource = 0; // 数据来源：0-插件；1-接口
	wall.storage = window.localStorage;
	wall.onWallIds = [];
	wall.styles = [
					{"margin-top":"-100%",'opacity':'0'},
					{"margin-left":"-100%",'opacity':'0'},
					{"margin-left":"100%",'opacity':'0'},
					{"margin-top":"200%",'opacity':'0'},
					{"margin-left":"-100%","margin-top":"-100%",'opacity':'0'},
					{"margin-left":"100%","margin-top":"-100%",'opacity':'0'},
					{"margin-left":"100%","margin-top":"200%",'opacity':'0'},
					{"margin-left":"-100%","margin-top":"200%",'opacity':'0'}
				];
	wall.de_style = [
					{"margin-top":'0','opacity':'1'},
					{"margin-left":"0",'opacity':'1'},
					{"margin-left":"0",'opacity':'1'},
					{"margin-top":"0",'opacity':'1'},
					{"margin-left":"0","margin-top":"0",'opacity':'1'},
					{"margin-left":"0","margin-top":"0",'opacity':'1'},
					{"margin-left":"0","margin-top":"0",'opacity':'1'},
					{"margin-left":"0","margin-top":"0",'opacity':'1'}
					] ;

/**
 * 初始将信息全部放到wall-group中。
 */
wall.firstSetInWall = function(){
	wall.signers = message_list.listWxdpShowDTO;
	$(".messageTotal").text(message_list.wxdpShowMessageCount);
	
	if( $('.wall-container div.msg-detail').length == 0 ){
		$('.wall-container').append('<div class="msg-detail"></div><div class="wall-group"></div>');
	}
	
	var firstSetNum = 0;
	if(wall.signers.length > 10+wall.displayNum){
		firstSetNum  = 10+wall.displayNum;
	}else{
		firstSetNum = wall.signers.length ;
	}
	var str = "";
	for (var i = 0; i < firstSetNum; i++) {
		str += wall.setOneInWall.call(null,wall.signers,i);
		wall.nowAt++;
	};
	
	$('.wall-group').append(str);
}

wall.setInWall = function(length){
	
	if(length < 0 || (wall.nowAt - 0) >= wall.signers.length){
		return;
	}
	
	var str = "";
	var num = (wall.nowAt - 0  + (length - 0));
	for (var i = wall.nowAt; i < num; i++) {
		if(i < wall.signers.length){
			str += wall.setOneInWall.call(null,wall.signers,i);
			wall.nowAt++;
		}
	};
	
	$('.wall-group').append(str);
}

/**
 * 将每一条信息拼装到wall-group中。
 */
wall.setOneInWall = function(obj,i){
	var str = "";
	var type = obj[i]['messageType'];
	var content = obj[i]['messageContentText'];
	var contentSHow = obj[i]['messageContentShow'];
	var name = obj[i]['nickName'].replace("</span>","");
	if(type == '1'){
		var size = 45;
		if(null != content) {
			if(content.length > 15){
				size = '25px';
				if(content.length > 50){
					size = '10px';
				}
			}
		}
		if(wall.dataSource == 1){
			var head = obj[i]['headImgUrl'];
			var msgId = obj[i]['messageId'];
			str += "<div class='group-warp' data-id='"+msgId +"' state='"+i+"'>";
			str += "     <img src='"+head+"' class='main'>";
		} else {
			var head = obj[i]['headImgString'];
			var msgWebId = obj[i]['messageWebId'];
			str += "<div class='group-warp' data-id='"+msgWebId +"' state='"+i+"'>";
			str += "     <img src='data:image/png;base64,"+head+"' class='main'>";
		}
		var style = "style='font-size:"+size+";'";
        str += "     ";
        str += "  	 <div class='msg-content'><span class='icon-bubble'></span><div><span>"+name+":</span><p "+style+">"+contentSHow+"</p></div></div>";
		str += "</div>";	
	}else{
		var image = "";
		if(wall.dataSource == 1){
			var head = obj[i]['headImgUrl'];
			var msgId = obj[i]['messageId'];
			str += "<div class='group-warp' data-id='"+msgId +"' state='"+i+"'>";
			str += "     <img src='"+head+"' class='main'>";
	        str += "     ";
	        str += "  	 <div class='msg-content'><span class='icon-bubble'></span><div><img src='"+image+"' /></div></div>";
			str += "</div>";
		} else {
			var head = obj[i]['headImgString'];
			var msgWebId = obj[i]['messageWebId'];
			image = obj[i]['messageContentPhotoString'];
			str += "<div class='group-warp' data-id='"+msgWebId +"' state='"+i+"'>";
			str += "     <img src='data:image/png;base64,"+head+"' class='main'>";
	        str += "     ";
	        str += "  	 <div class='msg-content'><span class='icon-bubble'></span><div><span>"+name+":</span><img src='data:image/png;base64,"+image+"' /></div></div>";
			str += "</div>";
		}

	}
	return str;
}

/**
 * 上墙
 */
wall.setToWall = function(){
	var tar = $('.wall-group').children(".group-warp");
	if(tar.length == 0){
		if(wall.cycleSwitch == 0){
			wall.firstPage.call();
			return;
		}
	} else{
		wall.clear.call();
		if(wall.newQueue.length != 0){
			var str = "";
			for(var i =0; i< wall.newQueue.length; i++){
				str += wall.setOneInWall.call(null,wall.newQueue, i);
			}
			$('.wall-group').append(str);
			wall.newQueue = [];
		} else {
			wall.setInWall.call(null,wall.displayNum);
		}
		wall.onWallIds = [];
		for(var i = 0 ; i < wall.displayNum ; i++){
			wall.under_at ++;
			times = Math.floor( wall.HideTimes * (i+1) / (wall.displayNum+1) );
			wall.goHidden.call(null,tar[i],times);
			wall.onWallIds.push($(tar[i]).attr('data-id'));
		}
		wall.storage.setItem("msg-played-ids-"+$("#form_active_id").val(),wall.onWallIds);
		wall.setToWallShow.call();
	}
	wall.spinTimeOut = setTimeout(function(){wall.setToWall.call();},wall.cycleTime*1000);
}

/**
 * 
 */
wall.goHidden = function(obj,times){
		wall.setToWallHidden.call(null,obj);
		wall.HideAndRemove.call(null,obj,times);
}

/**
 * 
 */
wall.setToWallShow = function(){
	$('.msg-detail').children('.group-warp').each(function(){
		$(this).queue(function(){
			var styles__ = ($(this).attr('state')-0)%wall.styles.length;
			$(this).animate(wall.de_style[styles__],2000);
			$(this).dequeue();
		});
	});	
}

/**
 * 
 */
wall.setToWallHidden = function(obj){
	var styles_ = ($(obj).attr('state')-0)%wall.styles.length;
	$(obj).clone().appendTo('.msg-detail').css(wall.styles[styles_]);
}

/**
 * 
 */
wall.HideAndRemove = function(obj,times){
	$(obj).queue(function(){
		$(obj).animate({"opacity":'0','width':'0px','height':'0px'},
					times,
					function(){		
						$(obj).remove();
					});
		$(this).dequeue();
	});
	$(obj).children('img').queue(function(){
		$(obj).children('img').animate({'margin':'5%',"opacity":'0','width':'0px','height':'0px'},times);
		$(this).dequeue();
	});
}

/**
 * 清空墙上信息元素
 */
wall.clear = function(){
	var _length = $('.msg-detail').children('.group-warp').length;
	if (_length>0){
		var mod = Math.floor(_length/3)*3;
		wall.onWall = 0;
		for(var i = 0 ; i< wall.displayNum;i++){
			$($('.msg-detail').children('.group-warp')[i]).hide(1000,function(){$(this).remove()});
		}
	};
}

/**
 * 微信墙主方法。
 */
function walls(){
	wall.initSetting.call();
	wall.initQrCode.call();
	var onWallIds = wall.storage.getItem("msg-played-ids-"+$("#form_active_id").val());
	if(!(typeof onWallIds == undefined || onWallIds == null ||onWallIds.length == 0)){
		var ids = onWallIds.split(",");
		wall.signers = message_list.listWxdpShowDTO;
		if(wall.signers != null){
			cycle:
			for(var i=0; i< ids.length;i++){
				for(var j = 0; j< wall.signers.length; j++){
					if(ids[i] == wall.signers[j]['messageWebId']){
						wall.nowAt = j;
						$(".messageTotal").text(message_list.wxdpShowMessageCount);
						if( $('.wall-container div.msg-detail').length == 0 ){
							$('.wall-container').append('<div class="msg-detail"></div><div class="wall-group"></div>');
						}
						wall.setInWall.call(null, 13);
						wall.spinTimeOut = setTimeout(function(){wall.setToWall.call();},1000);
						break cycle;
					}
				}
			}
		}
	} else{
		wall.start.call();
	}
	wall.bindClick.call();
	wall.loadTimeOut = setTimeout(function(){wall.loadData.call();},30000);
	wall.titleTimeOut = setTimeout(function(){wall.titleScroll.call();},15000);
}

wall.initQrCode = function(){
	var wxInfo = message_list.publicWxInfo;
	if(wxInfo != null){
		if(wxInfo.pubOpenQrCodeStr != null){
			var str = "<img src='data:image/png;base64,"+wxInfo.pubOpenQrCodeStr + "' style='max-width:126px;max-height:96px;' />"; 
			$("#qrCode").append(str);
			var codeStr = "<li class='img-b'><img src='data:image/png;base64," + wxInfo.pubOpenQrCodeStr + "'/></li>";
			codeStr += "<li class='img-d'><img src='data:image/png;base64," + wxInfo.pubOpenQrCodeStr + "'/></li>";
			codeStr += "<li class='img-s'><img src='data:image/png;base64," + wxInfo.pubOpenQrCodeStr + "'/></li>";
			$(".img-list").append(codeStr);
			$(".js_weixinAccountName").text(wxInfo.pubOpenWxId);
		}
	}
}

/**
 * 微信墙启动方法。
 */
wall.start = function(){
	wall.firstSetInWall.call();
	wall.spinTimeOut = setTimeout(function(){wall.setToWall.call();},1000);
}

/**
 * 轮转暂停/播放
 * @param state
 */
function StopWall(state){
	if(state == "pause"){
		clearTimeout(wall.spinTimeOut);
	}else{
		wall.spinTimeOut = setTimeout(function(){wall.setToWall.call();},1000);
	}
}

/**
 * 停止正在运行的动画。
 */
wall.stopAnimate= function(){
	$('.wall-group').children('.group-warp').stop(true,true);
	$('.wall-group').stop(true,false);
	$('.msg-detail').children('.group-warp').stop(true,true);
}

/**
 * 为操作按钮绑定事件。
 */
wall.bindClick = function(){
	$("#prepage").click(function(){
		wall.prePage.call();
	});
	$("#nextpage").click(function(){
		wall.nextPage.call();
	});
	$("#firstpage").click(function(){
		wall.firstPage.call();
	});
	$("#lastpage").click(function(){
		wall.lastPage.call();
	});
}

/**
 * 上一屏。
 */
wall.prePage = function(){
	wall.stopAnimate.call();
	clearTimeout(wall.spinTimeOut);
	var at = wall.under_at;
	wall.under_at = wall.under_at-6;
	if(at <= 3){
		wall.setToWall.call();
		return;
	}
	var tar = $('.wall-group').children(".group-warp");
	var str = "";
	for(var i = 0;i < 6;i++){
		at--;
		str = wall.setOneInWall.call(null,wall.signers,at) + str;
	}
	$(str).insertBefore($('.wall-group').children('.group-warp:first'));
	
	wall.clear.call();
	var tar = $('.wall-group').children(".group-warp");
	wall.onWallIds = [];
	for(var i = 0 ; i < wall.displayNum ; i++){
		wall.under_at ++;
		times = Math.floor( wall.HideTimes * (i+1) / (wall.displayNum+1) );
		wall.goHidden.call(null,tar[i],times);
		wall.onWallIds.push($(tar[i]).attr('data-id'));
	}
	wall.storage.setItem("msg-played-ids-"+$("#form_active_id").val(),wall.onWallIds);
	wall.setToWallShow.call();
	
	wall.spinTimeOut = setTimeout(function(){wall.setToWall.call();},wall.cycleTime*1000);
}

/**
 * 下一屏
 */
wall.nextPage = function(){
	clearTimeout(wall.spinTimeOut);
	wall.stopAnimate.call();
	wall.setToWall.call();
}

/**
 * 最旧一屏
 */
wall.firstPage = function(){
	clearTimeout(wall.spinTimeOut);
	wall.stopAnimate.call();
	$('.wall-group').html('');
	wall.nowAt = 0;
	wall.start.call();
}

/**
 * 最新一屏
 */
wall.lastPage = function(){
	clearTimeout(wall.spinTimeOut);
	wall.stopAnimate.call();
	var last = (wall.signers.length%3==0)?3:wall.signers.length%3;
	wall.nowAt = wall.signers.length - last;
	$('.wall-group').html('');
	wall.setInWall.call(null,last);
	wall.spinTimeOut = setTimeout(function(){wall.setToWall.call();},1000);
}

/**
 * 操作按钮鼠标滑过隐藏和显示。
 */
var menu_control =  menu_control||{};
menu_control.hover = function(){
	$("#control_menu").hover(function(){
		$(this).animate({'opacity':'1'},3000);
	},function(){
		$(this).animate({'opacity':'0'},3000);
	});
}

/**
 * 底部操作按钮展示。
 */
function setVisiable(){
	//menu_control.hover.call();
	$("#control_menu").animate({'opacity':'1'});
}

wall.titleScroll = function(){
	if(wall.titleCycleSwitch == 0){
		if(wall.titleText.length != 0){
			$("#active_name").toggle("slow");
			$("#title_text").toggle("slow");
		}
	}
	wall.titleTimeOut = setTimeout(function(){wall.titleScroll.call();},15000);
}

/**
 * 加载后台数据。
 */
wall.loadData = function(){
	var baseUrl = $("#baseURL").val();
	var activeId = $("#form_active_id").val();
	var createTime = "";
	if(wall.signers.length != 0){
		createTime = wall.signers[wall.signers.length - 1]['creatTimeString'];
	}
	$.ajax({
        url : baseUrl + 'WxdpShow/queryData',
        type : 'post',
        data : {
        	createTime : createTime,
            activeId : activeId
        },
        async : true,
        success : function(data) {
            if(typeof(data) != 'undefined'){
            	if(data.error == ""){
            		var list = [[data.msg]];
            		message_list.wxdpShowMessageCount = list[0][0].wxdpShowMessageCount;
            		$(".messageTotal").text(message_list.wxdpShowMessageCount);
            		if(null != list[0][0].listWxdpShowDTO){
                		message_list.listWxdpShowDTO.addAll(list[0][0].listWxdpShowDTO);
                		wall.newQueue = list[0][0].listWxdpShowDTO;
            		}
            		wall.loadTimeOut = setTimeout(function(){wall.loadData.call();},30000);
            	}
            }
        }
	});
}

wall.initSetting = function(){
	wall.setting = message_list.listTwFActiveItem;
	if(wall.setting != null){
		for(var i = 0; i< wall.setting.length; i ++){
			var param = wall.setting[i]['attrValue'];
			switch(wall.setting[i]['attrCode']){
			case "auto_scroll_time": //翻页间隔
				wall.cycleTime = param-0;
				$("#autoScrollTime_"+ param).attr('checked', 'checked');
				break;
			case "is_cycle_scroll": // 循环滚动
				wall.cycleSwitch = param-0;
				$("#isCycleScroll_"+param).attr('checked', 'checked');
				break;
			case "visible_message_num": //消息条数
				if(wall.displayNum > (param-0)){
					wall.clear.call();
				}
				wall.displayNum = param-0;
				$("#visibleMessageNum_"+param).attr('checked', 'checked');
				break;
			case "display_name_type": //用户名称
				$("#displayNameType_"+param).attr('checked', 'checked');
				break;
			case "active_name": //活动主题
				$("#setting_activeName").val(param);
				$("#active_name").text(param);
				break;
			case "scroll_text": //消息轮播文字
				wall.titleText = param;
				$("#title_text").text(param);
				$("#setting_scrollText").val(param);
				break;
			case "is_scroll_text_scroll":  //轮播文字开关
				wall.titleCycleSwitch = param-0;
				$("#isTitleScroll_"+param).attr('checked','checked');
				break;
			case "message_time_type": // 消息时段
				$("#messageTimeType_"+param).attr('checked', 'checked');
				break;
			case "latest_hour": //最近时间内
				$("#lastestHour").val(param);
				break;
			case "start_date": //最小消息日期
				$("#startDate").val(param);
				break;	
			case "start_hour": //最小消息日期
				$("#startHour").val(param);
				break;
			case "end_date": //最小消息日期
				$("#endDate").val(param);
				break;
			case "end_hour": //最小消息日期
				$("#endHour").val(param);
				break;	
			case "is_before_behind": //数据来源
				wall.dataSource = param-0;
				$("#isBeforeBehind_"+param).attr('checked', 'checked');
				break;
			}
		}
	}
	console.log(wall.displayNum);
}
