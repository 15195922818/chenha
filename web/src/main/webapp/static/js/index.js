$(function(){
	bindScroll();
	BindGoTop();
	//introduct();
	product();
	$('.special .main  .menu')[0].click();
	$('.div-outer.introduct-outer').bind('mouseover',function(){
		if(introduct.first == 0){
			introduct();
		}
	});
});

var product = function(){
	$('.special .main  .menu').click(function(){
		var src = $(this)[0].getAttribute('src');
		$('.special .main .middle >img.big').hide().css({'opacity':'0','display':'block'});
		var newImg = "<img src='"+src+"' style='position:absolute;top:100%;z-index:2;left:100%;'/>";
		$(newImg)   .appendTo($('.special .main .middle .wrap_pic'))
					.animate(
							{'top':'0','left':'0'},
							500,
							function(){
								$('.special .main .middle .wrap_pic img:first').remove();
							}
					);
		var id = $(this)[0].getAttribute('id');
		var fdj_img_src = src.substring(0,src.lastIndexOf("/")+1) + "fdj_" + id+".png";
		$('.special .main .middle >img.big')[0].src = fdj_img_src;
		$('.special .main .middle >img.big').animate({"opacity":"1"},1300);
		$(".special .main  .menu").removeClass('active');
		$(this).addClass('active');
	});
}

var introduct = function(){
	$(".introduct div.detail").animate({"margin-left":"0"},2000);
	$(".introduct div.detail-right").animate({"margin-left":"0"},2000);
	introduct.first == 1;
};
introduct.first = 0;

var bindScroll = function (){
	var top = document.body.scrollTop;
	window.onscroll = function(){
		var top = bindScroll.getPageScroll.call()-0;
		if(top == 0 && $('.nav-haimi')[0].className.indexOf('notop')>-1){
			$('.nav-haimi').removeClass('notop');
		}
		if(top > 0 && $('.nav-haimi')[0].className.indexOf('notop')==-1){
			$('.nav-haimi').addClass('notop');
		}
		if(top == 0){
			$("div.shares").children('a.top').css({'opacity':'0'});
		}else{
			$("div.shares").children('a.top').css({'opacity':'1'});
		}
	};
}
bindScroll.getPageScroll =  function (){ 
	var yScroll; 
	if (self.pageYOffset) { 
		yScroll = self.pageYOffset; 
	} else if (document.documentElement && document.documentElement.scrollTop){ 
		yScroll = document.documentElement.scrollTop; 
	} else if (document.body) { 
		yScroll = document.body.scrollTop; 
	} 
	return yScroll; 
};

var BindGoTop = function(){
	$('.shares .top').click(function(){
		$("html, body").animate({ scrollTop: 0 }, 120);
	});
}
