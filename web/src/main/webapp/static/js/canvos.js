var canvas_draw = canvas_draw||{};
	canvas_draw.cirProgress = {};
    canvas_draw.cirProgress.endAngle = 0;
    canvas_draw.cirProgress.ctx;
    canvas_draw.cirProgress.spinTimeout = 0;
    canvas_draw.cirProgress.arc = Math.PI/30;
    canvas_draw.cirProgress.pointer = 0;

  	function start_pause(obj){
	var canvas =  document.getElementById('cirProgress');
    if (canvas.getContext) {
    	ctx = canvas.getContext("2d");
    	canvas_draw.cirProgress.ctx = ctx;
    }
    
    document.getElementById('btn-pause').onclick=function(){
    	
    	if(obj.callback != undefined){
    		obj.callback.call(null,canvas_draw.state.call());
    	}
    	if(this.className.indexOf("btn-pause") > 0){
    		this.className = this.className.replace("btn-pause","");
    		canvas_draw.cirProgress.circle_progress.call();
    	}else{
    		this.className += " btn-pause";
    		clearTimeout(canvas_draw.cirProgress.spinTimeout);
    	}
    };

    canvas_draw.cirProgress.circle_progress.call();
}

canvas_draw.state = function(){
	return ($('#btn-pause')[0].className.indexOf("btn-pause") > 0)?"start":"pause";
}

canvas_draw.cirProgress.circle_progress = function (){
	clearTimeout(canvas_draw.cirProgress.spinTimeout);
	canvas_draw.cirProgress.pointer ++;
	canvas_draw.cirProgress.pointer = canvas_draw.cirProgress.pointer%60;
	canvas_draw.cirProgress.draw_circle.call();
	canvas_draw.cirProgress.spinTimeout = setTimeout('canvas_draw.cirProgress.circle_progress.call();',100);
}

canvas_draw.cirProgress.draw_circle = function(){
	ctx.clearRect(0,0,50,50);
	//最外 红
	ctx.beginPath(); 
	ctx.arc(25, 25, 18, 0, Math.PI*2, true); 
	ctx.lineWidth = 2; 
	ctx.strokeStyle = "#fb9fa6"; 
	ctx.shadowOffsetX = 0;
	ctx.shadowOffsetY = 0;
	ctx.shadowBlur    = 0;
	ctx.shadowColor   = "rgb(0,0,0)";
	ctx.stroke();

	ctx.beginPath(); 
	ctx.arc(25, 25, 18, -Math.PI/2, canvas_draw.cirProgress.arc*canvas_draw.cirProgress.pointer-Math.PI/2, false); 
	ctx.lineWidth = 2; 
	ctx.strokeStyle = "#ed4c58"; 
	ctx.shadowOffsetX = 0;
	ctx.shadowOffsetY = 0;
	ctx.shadowBlur    = 0;
	ctx.shadowColor   = "rgb(0,0,0)";
	ctx.stroke();

}





