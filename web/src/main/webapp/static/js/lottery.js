var lottery = lottery||{};
	lottery.lotteries =  new Array(); //参与抽奖总列表
	lottery.prizers = new Array(); //获奖列表
	lottery.prizers_now = 0; //当前获奖人数
	lottery.prizers_target = 0; //目标获奖人数
	lottery.lottery_at = 0; //当前抽到第几个
	lottery.lott_timeout = 0;
	lottery._index =  new Array();
	lottery.winnerBean =  new Array();
	lottery.roundID = Math.uuidCompact();

/**
 * 开始抽奖
 */
function go_lottery(obj){
	$(obj).children('span').html('停止');
	$("#lotteryNumSel").attr({'disabled':'true'});
	obj.onclick = function(){
		lottery.stop_lott.call(null,obj);
	}
	if(lottery.lotteries.length == 0){
//		lottery.lotteries = lottery.user_list;//in test.js
		lottery.lotteries = lottery.lotteryList;
	}
	lottery.prizers_target = $('#lotteryNumSel').val();
	lottery.lott_timeout = setTimeout('lottery.lottery_circle.call();',100);
}

lottery.lottery_circle = function(){
	clearTimeout(lottery.lott_timeout);
	var lotteries = lottery.lotteries.length;
	lottery.lottery_at++;
	lottery.lottery_at = lottery.lottery_at%lotteries
	var tar_at = lottery.lottery_at;
	while(lottery._index.exist(tar_at)){
		lottery.lottery_at++;
		lottery.lottery_at = lottery.lottery_at%lotteries
		tar_at = lottery.lottery_at;
	}
//	$('.lotteryImg')[0].src = lottery.lotteries[tar_at]['avatar'];
//	$('.lotteryName').html(lottery.lotteries[tar_at]['name']);
	$('.lotteryImg')[0].src = 'data:image/png;base64,' +lottery.lotteries[tar_at]['headImgString'];
    $('.lotteryName').html(lottery.lotteries[tar_at]['nickName']);
	lottery.lott_timeout = setTimeout('lottery.lottery_circle.call();',100);
}

lottery.continue_lottery_circle = function(obj){
	clearTimeout(lottery.lott_timeout);
	$(obj).children('span').html('停止');
	obj.onclick = function(){
		lottery.stop_lott.call(null,obj);
	}
	lottery.lott_timeout = setTimeout('lottery.lottery_circle.call();',100);
}

lottery.setPriser = function(){
	clearTimeout(lottery.lott_timeout);
//	var name = lottery.lotteries[lottery.lottery_at]['name'];
//	var _src = lottery.lotteries[lottery.lottery_at]['avatar'];
	var name = lottery.lotteries[lottery.lottery_at]['nickName'];
	var _src = 'data:image/png;base64,' + lottery.lotteries[lottery.lottery_at]['headImgString'];
	lottery.winnerBean = {"activeId":lottery.lotteries[lottery.lottery_at]['activeId'],"openId":lottery.lotteries[lottery.lottery_at]['fakeId'],"round":lottery.roundID};
	lottery.addWinner();
	lottery._index[lottery._index.length] = lottery.lottery_at;
	lottery.prizers[lottery.prizers.length] = lottery.lotteries[lottery.lottery_at];
	lottery.prizers_now++;
	var str = '';
		str += '<li class="clearfix" data-index="'+lottery.lottery_at+'">			';
		str += '	<p class="head-part left">									';     
		str += '    	<span class="num-p winUserRankNum">						';
		str += '        	<em>'+(lottery._index.length)+'</em>				';
		str += '    	</span>          										';
		str += '    	<a href="javascript:void(0);">							';
		str += '      	  	<img width="50" height="50" src="'+_src+'" alt="">	';
		str += '   	 	</a>  													';
		str += '	</p>'    ; 
		str += '	<a href="javascript:;" class="nick-name left winUserName">'+name+'</a>'  ;     
		str += '	<a href="javascript:void(0);" class="del delWinUser" data-index="'+lottery.lottery_at+'" onclick = "lottery.delOnePrize(this);">×</a> '  ;
		str += '</li>';
	$('.winUserList').prepend(str);
	$('.winUserNum').html(lottery._index.length);
}

lottery.stop_lott = function(obj){
	lottery.setPriser.call();
	if(lottery.prizers_target == lottery.prizers_now){
		$(obj).children('span').html('开始抽奖');
		$("#lotteryNumSel").removeAttr('disabled');
		lottery.prizers_now = 0;
		obj.onclick = function(){
			go_lottery.call(null,obj);
		}
	}else{
		obj.onclick = function(){};
		$(obj).children('span').html('开始抽奖('+(lottery.prizers_target-lottery.prizers_now)+')');
		lottery.lott_timeout = setTimeout(function(){lottery.continue_lottery_circle.call(null,obj)},3000);
	}
	$("#lotteryNum").text(lottery.lotteries.length-lottery.prizers.length);
}

lottery.delOnePrize = function(obj){
    var _index = obj.getAttribute('data-index');
    lottery.winnerBean = {"activeId":lottery.lotteries[_index]['activeId'],"openId":lottery.lotteries[_index]['openId'],"round":lottery.roundID};
    lottery.delWinner();
    lottery.delOnePrizeAfter(obj,_index);
    alert(lottery.prizers.length);
	$("#lotteryNum").text(lottery.lotteries.length-lottery.prizers.length);
}
lottery.delOnePrizeAfter=function(obj,_index){
//    var _index = obj.getAttribute('data-index');
    var _Number = $('li[data-index="'+_index+'"] .winUserRankNum em').text();
    lottery._index.removeValue(_index);
    lottery.prizers.remove(_index);
    $('.winUserNum').html(lottery._index.length);
    $('li[data-index="'+_index+'"]').remove();
    $('.winUserList').children('li').each(function(){
        if($(this).find('em').text()-0 > _Number ){
            $(this).find('em').html($(this).find('em').text()-1);
        }
    });
}
lottery.delALLPrize = function(){
    lottery.roundID = Math.uuidCompact();
	lottery._index  = new Array();
	lottery.prizers = new Array();
	$('.winUserNum').html(lottery._index.length);
	$('.winUserList').html('');
	lottery.prizers.length = 0;
	$("#lotteryNum").text(lottery.lotteries.length);
}
lottery.addWinner = function(){
    var base_url = $("#baseURL").val();
    $.ajax({
        url: base_url+'user/lottery/addWinner',
        type: 'post',
        data: lottery.winnerBean,
        success: function (data, status)
       {
           if(typeof(data.error) != 'undefined')
           {
               if(data.error != '')
               {
                alert(data.error);
               }else
               {
//                alert(data.msg);
               }
           }
       },
       error: function (data, status, e)
       {
           alert(e);
       }
    }); 
}
lottery.delWinner = function(){
    var base_url = $("#baseURL").val();
    $.ajax({
        url: base_url+'user/lottery/delWinner',
        type: 'post',
        async: false,
        data: lottery.winnerBean,
        success: function (data, status)
       {
           if(typeof(data.error) != 'undefined')
           {
               if(data.error != '')
               {
                alert(data.error);
               }else
               {
//                alert(data.msg);
               }
           }
       },
       error: function (data, status, e)
       {
           alert(e);
       }
    }); 
}
