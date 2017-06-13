Array.prototype.remove = function (dx) {  
    if (isNaN(dx) || dx > this.length) {  
        return false;  
    }  
    for (var i = 0, n = 0; i < this.length; i++) {  
        if (this[i] != this[dx]) {  
            this[n++] = this[i];  
        }  
    }  
    this.length -= 1;  
};
Array.prototype.removeValue = function (dx) {  
    var c = 0;
    for (var i = 0; i < this.length; i++) {  
        if (this[i] == dx) {  
           c = i;  
        }  
    }  
    for (var n = c; n < this.length; n++) {  
        this[n] == this[n+1];
    }  
    this.length -= 1;  
};

Array.prototype.exist = function (dx) {  
    for (var i = 0, n = 0; i < this.length; i++) {  
        if (this[i] == dx) {  
            return true; 
        }  
    }  
    return false;   
};

Array.prototype.addAll  =function (Array) {
    //if((typeof Array).toUpperCase() == 'ARRAY')return;  
    for (var i = 0; i < Array.length; i++) {  
        this[this.length] = Array[i];  
    }  
};

(function(){
	var screenLock = (function(){
		var screenLock = function(){
			this.setCss();
			this.createMask();
		}
		screenLock.prototype.setCss = function(){
			var style = document.createElement('style');
			style.type = 'text/css';
			style.innerHTML += ' div.ai-mask{overflow:hidden;padding:0;margin:0;z-index:100;width:100%;height:100%;position:absolute;top:0;left:0;background-color:black;opacity:0.6;}';
			style.innerHTML += ' div.ai-mask img{ opacity:1;left:49%;top:48%;position:relative;}';
			style.innerHTML += ' body.ai-lock{overflow-x:hidden;overflow-y:hidden;height:100%;}';
			document.getElementsByTagName('HEAD').item(0).appendChild(style);
		};
		screenLock.prototype.createMask = function() {
			var str = "<div class='ai-mask'>";
				str	+= "<img src='../static/images/loading.gif'/></div>"
			this.target = $(str);
			$(this.target).appendTo('body')
		};
		screenLock.prototype.show = function() {
			$(this.target).show();
			$("body").addClass('ai-lock');
			/*if($.browser.msie && ($.browser.version === "6.0" || $.browser.version === "7.0")){
				document.getElementsByTagName('html')[0].style.overflow = 'hidden';
			}*/
		};
		screenLock.prototype.hide = function() {
			$("body").removeClass('ai-mask');
            /*if($.browser.msie && ($.browser.version === "6.0" || $.browser.version === "7.0")){
                document.getElementsByTagName('html')[0].style.overflow = 'auto';
            }*/
			$(this.target).hide();
		};
		return screenLock;
	})();
	this.screenlock =  screenLock;
}).call(this);

/**
 * 整个页面全屏模式。
 */
function wholePageFullScreen(){
	launchFullScreen(document.documentElement);
}

/**
 * 特定元素启动全屏模式。
 * @param element
 */
function launchFullScreen(element) {  
  if(element.requestFullScreen) {  
	  element.requestFullScreen();
  } else if(element.mozRequestFullScreen) {  
	  element.mozRequestFullScreen();  
  } else if(element.webkitRequestFullScreen) {  
	  element.webkitRequestFullScreen();  
  } else if(element.webkitRequestFullscreen){
	  element.webkitRequestFullscreen();
  }
}