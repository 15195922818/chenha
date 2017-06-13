/**
 * 为下拉框初始化Hours下拉选项。
 * @param ids 下拉框ID，支持多个，如["startHours","endHours"]
 */
function initHoursOption(ids){
	if(ids.length == 0){
		return;
	}
	for(var i = 0; i < ids.length; i++){
		var hourOptionstr = "";
		var temp = "";
		for(var j = 0; j < 24; j++ ){
			temp = j;
			if(j < 10){
				temp = "0"+j;
			}
			hourOptionstr += "<option value= '" + temp + "'>" + temp + "</option>";
		}
		$("#" + ids[i]).append(hourOptionstr);
	}
}

/**
 * 为下拉框初始化Minutes下拉选项。
 * @param ids 下拉框ID，支持多个，如["startMinutes","endMinutes"]
 */
function initMinuteOption(ids){
	if(ids.length == 0){
		return;
	}
	for(var i = 0; i < ids.length; i++){
		var minuteOptionstr = "";
		var temp = "";
		for(var j = 0; j < 60; j++ ){
			temp = j;
			if(j < 10){
				temp = "0"+j;
			}
			minuteOptionstr += "<option value= '" + temp + "'>" + temp + "</option>";
		}
		$("#" + ids[i]).append(minuteOptionstr);
	}
}

function setStar(objarray){  
	if(objarray == null || objarray.length == 0){
		return;
	}
	
    for(var i=0;i<objarray.length;i++){  
        var object=document.getElementById(objarray[i]);
        if(object != null){
        	object.innerHTML="<font color='red'>*</font>&nbsp;"+object.innerHTML;
        }
    }  
}  