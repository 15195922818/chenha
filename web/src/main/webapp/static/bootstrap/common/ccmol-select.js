	
	
	
	function appendOptionTo($o,k,v,d){
			var $opt=$("<option>").text(v.district).val(v.district);
			if(v==d){$opt.attr("selected", "selected")}
			$opt.appendTo($o);
		}

	function fourSelect(config){
		var $s1=$("#"+config.s1);
		var $s2=$("#"+config.s2);
		var $s3=$("#"+config.s3);
		var $s4=$("#"+config.s4);
		var v1=config.v1?config.v1:'';
		var v2=config.v2?config.v2:'';
		var v3=config.v3?config.v3:'';
		var v4=config.v4?config.v4:'';

		$.each(fourSelectData,function(k,v){
			appendOptionTo($s1,k,v,v1);
		});

		
		$s1.change(function(){
			$s2.html("");
			if(this.selectedIndex==-1) return;
			var s1_curr_val = this.options[this.selectedIndex].value;
			
			//ajax请求 获得市下属的 市
			$.ajax({
		        url : config.url1,
		         type : 'post',
		         data : {"parentCode":s1_curr_val},
		         async : true,
		         success : function(data) {
		        		$.each(data,function(k,v){
		    				if(s1_curr_val==v.parentCode){
		    					appendOptionTo($s2,k,v,v3);
		    				}
		    			});        
		             },
		         error : function(){
		        	 //top.$.jBox.error("操作失败,请刷新页面重新修改","失败");
		         }
		         
		     });
			
	/* 		$.each(fourSelectData,function(k,v){
				
				if(s1_curr_val==v.code){
					if(v.subDistricts){
						$.each(v.subDistricts,function(k,v){
							appendOptionTo($s2,k,v,v2);
						});
					}
				}
			}); */
			if($s2[0].options.length==0){appendOptionTo($s2,"...","",v2);}
			$s2.change();
		}).change();
		
		
		$s2.change(function(){
			$s3.html("");
			if(this.selectedIndex==-1) return;
			var s2_curr_val = this.options[this.selectedIndex].value;
			
			//ajax请求 获得市下属的 区
			$.ajax({
		        url : config.url2,
		         type : 'post',
		         data : {"parentCode":s2_curr_val},
		         async : true,
		         success : function(data) {
		        		$.each(data,function(k,v){
		    				if(s2_curr_val==v.parentCode){
		    					appendOptionTo($s3,k,v,v3);
		    				}
		    			});        
		             },
		         error : function(){
		        	 //top.$.jBox.error("操作失败,请刷新页面重新修改","失败");
		         }
		         
		     });
		
			if($s3[0].options.length==0){appendOptionTo($s3,"...","",v3);}
			$s3.change();
		}).change();
		$s3.change(function(){
			$s4.html("<option></option>");
			if(this.selectedIndex==-1) return;
			var s3_curr_val = this.options[this.selectedIndex].value;	
			//ajax请求 获得市下属的 街道
			$.ajax({
		        url : config.url3,
		         type : 'post',
		         data : {"parentCode":s3_curr_val},
		         async : true,
		         success : function(data) {
		        		$.each(data,function(k,v){
		    				if(s3_curr_val==v.parentCode){
		    					appendOptionTo($s4,k,v,v3);
		    				}
		    			});        
		             },
		         error : function(){
		        	 //top.$.jBox.error("操作失败,请刷新页面重新修改","失败");
		         }
		         
		     });
			if($s4[0].options.length==0){appendOptionTo($s4,"...","",v4);}
			$s4.change();
		}).change();
		
	}