(function($) {
	var loadMsk = (function(){
		var loadMsk = function(obj){
			this.init(obj);
		};
		loadMsk.prototype.init = function(obj){
			var height = $(document).height();
			var mask = '<div class="addmode" style="height:'+height+'px;display:none;"></div><div class="mask-loading" style="display:none;"><div>正在加载数据,请稍候...</div><div class="mask-close" onclick="loadMsk.hideMsk();" alt="关闭"></div></div>'; 
			$('body').append(mask);
		};
		loadMsk.prototype.showMsk = loadMsk.showMsk = function(){
			$('body>.addmode').show();
			$('body>.mask-loading').show();
		};
		loadMsk.prototype.hideMsk = loadMsk.hideMsk = function(){
			$('body>.addmode').hide();
			$('body>.mask-loading').hide();
		};
		return loadMsk;
	})();
	
	var defaults = {
		tableClass : 'table',
		url : null,
		type : "get",
		async : true,
		dataType : 'json',
		mode : 'remote', //local or remote
		pageSize : 10,
		pageNumber : 1,
		data : null,
		colModel : [],
		onRowSelected : function() {
		},
		onRowDblclick : function() {
		},
		onLoadSuccess : function() {
		},
		onLoadError : function() {
		},
		onInit : function() {
		},
		queryParams : []
	};

	$.ratsGrid = $.ratsGrid || function(){};
	
	var defaultFunc = {
			buildThead : function(opts) {
				
				var showHeader = false;
				var thead = '<thead><tr>';
				var colModel = opts.colModel;
				if (colModel) {
					for ( var j = 0; j < colModel.length; j++) {
						console.log(colModel[j])
						thead += '<th style="' ;
						//宽度
						if (colModel[j].width ) {
							 if (!isNaN(colModel[j].width)) {
								 thead += 'width:' + colModel[j].width + 'px; ';
							} else {
								thead += 'width:' + colModel[j].width + '; ';
							}
						}
                        //是否隐藏
                        if(colModel[j].hide ){
                            thead += 'display:none; ';
                        }
                       
						//对齐
						if(colModel[j].align ){
							thead += 'text-align:' + colModel[j].align + '; ';
						}
						thead += '"' ;
						thead += ' col="'+ colModel[j].field +'"';
						
						
						thead += ' >' ;
						if(colModel[j].header!==undefined){
							showHeader = true;
							thead += colModel[j].header ;
						}
						
						thead += '</th>';
					}
				}
				thead += '</tr></thead>';
				if (showHeader) {
					return thead;
				}
				return "";
			},

			buildTbody : function(rows, opts) {

				var colModel = opts.colModel;
				var row = "";
				if (!rows || rows.length < 1) {
					return '<tbody><tr><td colspan="' + opts.colModel.length + '">没有数据！</td>';
				}

				var tbody = '<tbody>';
				for ( var i = 0; i < rows.length; i++) {
					row = rows[i];
					var tr = '<tr row-index=' + i + '>';
					for ( var j = 0; j < colModel.length; j++) {
						var td = "";
						
						if (colModel[j] && row[colModel[j].field] !== null && row[colModel[j].field] !== undefined) {
							td = row[colModel[j].field];
						}
						if (colModel[j].format) {
							td = colModel[j].format(td, row, i);
						}
                        tr += '<td style="';
                        if (colModel[j].width == undefined) {

                        } else if (!isNaN(colModel[j].width)) {
                            tr += 'width:' + colModel[j].width + 'px; ';
                        } else {
                            tr += 'width:' + colModel[j].width + '; ';
                        }
                        if (colModel[j].hide == true){
                        	tr += 'display:none;';
                        }
                        /*if(colModel[j].align ){
                        	tr += ' text-align:'+ colModel[j].align + ';';
	                    }*/
                        if(colModel[j].style){
                        	tr += colModel[j].style;
                        }
                        tr += '"';
                        if(colModel[j].align ){
                        	tr += ' align="'+ colModel[j].align + '"';
	                    }
                        if(colModel[j].tdClass ){
                        	tr += ' class="'+ colModel[j].tdClass + '"';
	                    }
                        tr +=' >' + td + '</td>';
					}
					tr += '</tr>';
					tbody += tr;
				}

				tbody += '</tbody>';
				return tbody;
			},

			buildTable : function(rt, opts, data) {

				var self = this;
				var head = self.buildThead(opts);
				var body = self.buildTbody(data.rows, opts);
				$(rt).addClass(opts.tableClass);
				$(rt).html(head + body);
				for( var i = 0 ; i < data.rows.length ; i++ ){
					$(rt).find("tbody tr:eq(" + i + ")").data('gridData',data.rows[i]);
				}
				
			},

			buildData : function(rt, opts, callback,pageChangeFlag) {
				
				var self = this;
				self.buildLoading(rt);
				if (opts.mode === 'local') {
					if (!opts.data) {
						return;
					}
					self.buildTable(rt, opts, opts.data);
					self.initEvent(rt, opts, opts.data);
					return;
				}
				
				if (!opts.url) return;
				var param = $.extend({}, opts.queryParams);
				if(pageChangeFlag){
					if (opts.pagination) {
						$.extend(param, {
							page : opts.pageNumber,
							rows : opts.pageSize
						});
					}
				}else{
					if (opts.pagination) {
						$.extend(param, {
							page : "1",
							rows : opts.pageSize
						});
					}
				}
				
				if (opts.sortName) {
					$.extend(param, {
						sort : opts.sortName,
						order : opts.sortOrder
					});
				}
				
				$.ajax({
					url : ""+opts.url,
					data : param,
					async : opts.async,
					type : opts.type,
					dataType : 'json',
					success : function(data) {
						self.clearLoading(rt);
						// var data = eval('(' + data + ')');
						var body = self.buildTbody(data.rows, opts);
						$(rt).addClass(opts.tableClass);
						$(rt).children("tbody").remove();
						$(rt).append(body);
						self.initEvent(rt, opts, data);
						opts.onLoadSuccess.call(rt, opts, data);
						if (callback){
							callback(data);
						}
					},
					error : function(jqXhr,status, error) {
						self.clearLoading(rt);
						jQuery.ajaxSettings.error.call(null,jqXhr,status, error);
						opts.onLoadError.call(rt, opts);
					}
				});
			},
			buildLoading : function(rt){
				var parent = $(rt).parent();
				parent.scrollLeft(0);
				parent.addClass("loading");
				if( $(rt).width() > parent.width() ){
					parent.addClass("padding");
				}
				var mask = '<div class="addmode" style="height:100%;"></div><div class="mask-loading"><div>正在加载数据,请稍候...</div></div>'; 
				parent.children("div.addmode").length > 0 ? '' : parent.append(mask);
			},
			clearLoading : function(rt){
				$(rt).parent().css({"min-height":"0"});
				$(rt).parent().removeClass("padding");
				$(rt).parent().removeClass("loading");
				$(rt).parent().children("div.addmode").remove();
				$(rt).parent().children("div.mask-loading").remove();
			},
			initEvent : function(rt, opts, data) {
				var self = this;
				var $tr = $(rt).find("tr");
				var onRowSelected = opts.onRowSelected;
				var onRowDblclick = opts.onRowDblclick;
				self.clearTimeout = -1;
				var rdata = data;
				$tr.on('click.tr', function(ev) {
					clearTimeout(self.clearTimeoutId);
					$tr.removeClass("row-selected");
					$(this).addClass("row-selected");
					var index = $(this).attr('row-index');
					if (rdata && rdata.rows) {
						self.clearTimeoutId = setTimeout(function(){
							onRowSelected.call(rt, $(this), rdata.rows[index]);
						},300);
					}
				});
				$tr.on('dblclick.tr', function(ev) {
					clearTimeout(self.clearTimeoutId);
					$tr.removeClass("row-selected");
					$(this).addClass("row-selected");
					var index = $(this).attr('row-index');
					if (rdata && rdata.rows) {
						onRowDblclick.call(rt, $(this), rdata.rows[index]);
					}

				});
			},
			firstInit : function (rt,opts){
				if( $(rt).children("thead").length > 0 ) return;
				$(rt).html(this.buildThead.call(rt,opts));
				if(opts.tableClass!='none'){
					$(rt).addClass(opts.tableClass);
					$(rt).addClass("n-table table table-striped table-bordered");//表格边框样式
				}
				$(rt).parent().is(".table_wrap")?"":$(rt).wrap("<div class='table_wrap'></div>");
				var parent = $(rt).parent();
				if(opts.pagination && opts.pageSize) {
					var height = ( opts.pageSize - 0 + 1 ) * 35 + "px";
					parent.css({"min-height":height});
				} 
			},
			changePage : function(rt, opts) {
				this.buildData(rt, opts,null,true);
			},
			init : function(rt, opts) {
				var options = opts;
				this.firstInit(rt, opts);
				this.buildData(rt, options, function(data) {
					options.onInit.call(rt, options, data);
				});
			},
			//create by xusen.
			initNew : function(rt, opts) {
				var options = opts;
				this.firstInit(rt, opts);
				this.buildData(rt, options, function(data) {
					options.onInit.call(rt, options, data);
				},true);
			}

		};
	// 静态方法
	$.extend($.ratsGrid, defaultFunc);
	$.ratsGrid.prototype = {};
	$.extend($.ratsGrid.prototype, defaultFunc);
	$.fn.ratsGrid = function(opts) {
		// 默认参数
		opts = $.extend({},defaults, opts);
		return this.each(function() {
			$.ratsGrid.init(this, opts);
		});

	}; // end

	$.fn.changePage = function(opts, pageNumber) {
		opts = $.extend(defaults, opts);
		opts = $.extend(opts, {
			pageNumber : pageNumber
		});
		return this.each(function() {
			$.ratsGrid.changePage(this, opts,null,true);
		});
	}; // end
	
	//create by xusen.
	$.fn.ratsGridNew = function(opts) {
		// 默认参数
		opts = $.extend({},defaults, opts);
		return this.each(function() {
			$.ratsGrid.initNew(this, opts);
		});

	}; // end

})(jQuery);

(function(){
	
	var GridTool = {};
	/**
	 * @author 70566
	 * name GridAddRow  向目标表格加入一行
	 * param  gridId:表格ID , data：这一行的数据 , formtter : opt (操作列的formatter方法) , isParentGrid : 是否是当前页面 默认是当前页面的父页面  
	 */
	GridTool.parent = ( parent != undefined  && parent.$("#mainFrame").length > 0 )?  parent.$("#mainFrame")[0].contentWindow : parent;
	GridTool.GridAddRow = function( gridId, data ,formatter ,isParentGrid){
	    var current = ( isParentGrid == undefined || isParentGrid == true ) ? GridTool.parent : window;
	    var headLine = current.$("#"+gridId).find("thead tr:first th");
	    var headCol = new Array();
	    var hideLine = new Array();
	    for( var i = 0; i < headLine.length ; i++ ){
	        headCol.push(headLine[i].getAttribute('col'));
	        $(headLine[i]).is(":visible") ? hideLine.push('') : hideLine.push("display:none;");
	    }
	    var tr = "";
	    data.index = current.$("#"+gridId).find("tbody tr").length + 1;
	    for(var i = 0 ; i < headCol.length ; i++){
	        if( headCol[i] != 'opt'){
	            tr += "<td col='"+headCol[i]+"' style='"+hideLine[i]+"'>" + data[headCol[i]] + "</td>";
	        }else if( headCol[i] == 'opt' ){
	            tr += "<td col='opt' style='"+hideLine[i]+"'>" + formatter.call(parent.$("#"+gridId),data) + "</td>";
	        }
	    }
	    tr = "<tr id="+data.index+">"+ tr +"</tr>";
	    current.$("#"+gridId).find("tbody").append(tr);
	};
	/**
	 * @author 70566
	 * name DeleteRow  删除表格当前行
	 * param  obj 当前行下的某一个元素 
	 */
	GridTool.DeleteRow = function( obj){
		var table = $(obj).closest('tbody');
	    $(obj).closest('tr').remove();  
	    if(table.find("td[col='index']").length>0){
	    	var tr = table.find('tr');
	    	for( var i = 0 ; i < tr.length ; i++ ){
	    		table.find('tr:eq('+i+')').find("td[col='index']").text(i+1);
	    	}
	    }
	};
	GridTool.DeleteRowUp = function( obj,gridId,col ,isParentGrid ){
		var table = $(obj).closest('tbody');
		$(obj).closest('tr').remove();  
		if(table.find("td[col='index']").length>0){
			var tr = table.find('tr');
			for( var i = 0 ; i < tr.length ; i++ ){
				table.find('tr:eq('+i+')').find("td[col='index']").text(i+1);
			}
		}
		teamTotalMoney =  GridTool.getColMoney( "contentTable" , "totalPrice" ,isParentGrid );
		totalServiceMoney = GridTool.getColMoney( "preferentialTable" ,"preferentialPrice",isParentGrid );
		serviceMoney = teamTotalMoney*1+totalServiceMoney*(1);
		teamTotalMoney = teamTotalMoney.toFixed(2);
		totalServiceMoney = totalServiceMoney.toFixed(2);
		serviceMoney = serviceMoney.toFixed(2);
		if(gridId=="contentTable"){
			$("#teamTotalMoney").val(teamTotalMoney);
		}
		$("#totalServiceMoney").val(totalServiceMoney*(1));
		$("#serviceMoney").val(serviceMoney);
	};
	/**
	 * @author 70566
	 * name getAllData  获取目标表格的所有数据
	 * param  gridId:表格ID  , isParentGrid : 是否是当前页面 默认是当前页面的父页面  
	 */
	GridTool.getAllData = function( gridId ,isParentGrid ){
	    var current = ( isParentGrid == undefined || isParentGrid == true ) ? GridTool.parent : window ;
	    var headLine = current.$("#"+gridId).find("thead tr:first th");
	    var headCol = new Array();
	    for( var i = 0; i < headLine.length ; i++ ){
	        headCol.push(headLine[i].getAttribute('col'));
	    }
	    var datas = new Array();
	    current.$("#"+gridId).find("tbody tr").each(function(){
	        var tr = $(this);
	        var data = {};
	        for( var i = 0 ; i < headCol.length ; i++ ){
	            data[headCol[i]] = $(tr).find("td:eq("+i+")").text();
	        }
	        datas.push(data);
	    });
	    return datas;
	};
	/**
	 * @author 70566
	 * name getColStr  获取目标表格的某一列的所有数据
	 * param  gridId:表格ID ,col : 这一列的列col属性值 , isParentGrid : 是否是当前页面 默认是当前页面的父页面  
	 */
	GridTool.getColStr = function( gridId , col ,isParentGrid ){
		var data = GridTool.getAllData( gridId ,isParentGrid );
		var result = "";
		for( var i = 0 ; i < data.length ; i++ ){
			result += ":";
			result += data[i][col];
		}
		return result;
	};
	GridTool.getLine = function(obj){
		var tr = $(obj).closest('tr');
		var table = $(obj).closest('table');
		var headLine = table.find("thead tr:first th");
	    var headCol = new Array();
	    for( var i = 0; i < headLine.length ; i++ ){
	        headCol.push(headLine[i].getAttribute('col'));
	    }
	    var data = {};
	    for( var i = 0 ; i < headCol.length ; i++){
	    	data[headCol[i]] = tr.find('td:eq('+i+')').text();
	    }
	    return data;
	};
	GridTool.setLine = function( gridId, data , lineNum ,isParentGrid){
		var current = ( isParentGrid == undefined || isParentGrid == true ) ? GridTool.parent : window;
	    var headLine = current.$("#"+gridId).find("thead tr:first th");
	    var headCol = new Array();
	    for( var i = 0; i < headLine.length ; i++ ){
	        headCol.push(headLine[i].getAttribute('col'));
	    }
	 //   var tds = current.$("#"+gridId).find("tbody tr:eq("+ ( lineNum-1 ) +") td");
	    var tds = current.$("#"+gridId).find("tbody tr[id="+lineNum+"] td");
	    for( var i = 0 ; i < tds.length ; i++ ){
	    	if( data[headCol[i]] != undefined ){
	    		headCol[i] != 'opt' ? $(tds[i]).text(data[headCol[i]]) :'';
	    	}
	    }
	};
	GridTool.getColMoney = function( gridId , col ,isParentGrid ){
		var data = GridTool.getAllData( gridId ,isParentGrid );
		var result = 1*0;
		for( var i = 0 ; i < data.length ; i++ ){
			//result += ":";
			result += (data[i][col])*1;
		}
		return result;
	};
	window.GridTool = GridTool;
})();
