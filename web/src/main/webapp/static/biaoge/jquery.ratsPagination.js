(function($) {

	var defaults = {
		total : 1, // 总条数
		pageSize : 10, // 每页多少条
		pageNumber : 1, // 当前页
		pageList : [ 10, 20, 30, 50 ],
		showPageNumber : 10,// 每次显示页码数量
		loading : false,
		displayMsg : '共{total}条/{pageCount}页',
		Select : function(opts, pageNumber, pageSize) {
		}

	};

	$.ratsPagination = $.ratsPagination || {};

	// 静态方法
	$.extend($.ratsPagination, {

		buildDisplay : function(opts) {

			var display = opts.displayMsg.replace(/{total}/, opts.total);
			display = display.replace(/{pageCount}/, Math.ceil(opts.total / opts.pageSize));
			return '<div class="display"> ' + display + '</div>';
		},
		buildPagination : function(opts, pageShowMin, pageShowMax) {
			
			var pagination = '<ul class="pagination-ul">';
			//if(pageShowMin<pageShowMax){
				pagination += '<li><a href="javascript:void(0);" class="first" data-action="first" >&laquo;</a></li>';
				pagination += '<li><a href="javascript:void(0);" class="previous" data-action="previous" >&lsaquo;</a></li>';
				
				
				for ( var i = pageShowMin; i <= pageShowMax; i++) {
					var active = i === opts.pageNumber ? 'active' : '';
					var li = '<li><a href="javascript:void(0);" class="pageNum {active}" data-action="{pageno}" >{pageno}</a></li>';
					li = li.replace(/{active}/, active);
					li = li.replace(/{pageno}/g, i);
					pagination += li;
				}
				pagination += '<li><a href="javascript:void(0);" class="next" data-action="next" >&rsaquo;</a></li>';
				pagination += '<li><a href="javascript:void(0);" class="last" data-action="last" >&raquo;</a></li>';
			//}
			pagination += '</ul>';
			return pagination;
		},
		initEvent : function(rt, opts) {
			var options = jQuery.extend(true, {}, opts);
			var self = jQuery.extend(true, {}, this);
			var tagert = rt;
			var $el = $(rt);
			var pageCount = Math.ceil(options.total / options.pageSize);
			$el.find('a.first').off('.page').on('click.page', function() {
				if (options.pageNumber > 1)
					self.selectPage(tagert, options, 1);
			});

			$el.find('a.previous').off('.page').on('click.page', function() {
				if (options.pageNumber > 1)
					self.selectPage(tagert, options, options.pageNumber - 1);
			});

			$el.find('a.next').off('.page').on('click.page', function() {
				if (options.pageNumber < pageCount)
					self.selectPage(tagert, options, options.pageNumber + 1);
			});

			$el.find('a.last').off('.page').on('click.page', function() {
				if (options.pageNumber < pageCount)
					self.selectPage(tagert, options, pageCount);
			});

			$el.find('a.pageNum').off('.page').on('click.page', function() {
				var pageNumber = parseInt($(this).attr('data-action')) || 1;
				self.selectPage(tagert, options, pageNumber);
			});

		},

		selectPage : function(rt, opts, pageNumber) {
			opts.pageNumber = pageNumber;
			var pageCount = Math.ceil(opts.total / opts.pageSize);
			var pageShowMax = opts.showPageNumber % 2 == 0 ? opts.pageNumber - 1 : opts.pageNumber;
			var pageShowMin = opts.pageNumber;
			for ( var i = 0; i < Math.floor(opts.showPageNumber / 2); i++) {
				pageShowMax++;
				pageShowMin--;
				if (pageShowMax > pageCount) {
					pageShowMax = pageCount;
					if (pageShowMin > 1) {
						pageShowMin--;
					}
				}
				if (pageShowMin < 1) {
					pageShowMin = 1;
					if (pageShowMax < pageCount) {
						pageShowMax++;
					}
				}
			}
			$(rt).addClass('pagination');
			$(rt).empty();
			$(rt).append(this.buildDisplay(opts));
			$(rt).append(this.buildPagination(opts, pageShowMin, pageShowMax));
			this.initEvent(rt, opts);
			opts.onPageSelected.call(rt, opts, pageNumber, opts.pageSize);
		},
		init : function(rt, opts) {
			opts.pageNumber = 1;
			var pageCount = Math.ceil(opts.total / opts.pageSize);
			var pageShowMax = opts.showPageNumber % 2 == 0 ? opts.pageNumber - 1 : opts.pageNumber;
			var pageShowMin = opts.pageNumber;

			for ( var i = 0; i < Math.floor(opts.showPageNumber / 2); i++) {
				pageShowMax++;
				pageShowMin--;
				if (pageShowMax > pageCount) {
					pageShowMax = pageCount;
					if (pageShowMin > 1) {
						pageShowMin--;
					}
				}
				if (pageShowMin < 1) {
					pageShowMin = 1;
					if (pageShowMax < pageCount) {
						pageShowMax++;
					}
				}
			}
			$(rt).addClass('pagination');
			$(rt).empty();
			$(rt).append(this.buildDisplay(opts));
			$(rt).append(this.buildPagination(opts, pageShowMin, pageShowMax));
			this.initEvent(rt, opts);
		}

	});

	$.fn.ratsPagination = function(opts) {
		opts = $.extend(defaults, opts);
		return this.each(function() {
			$.ratsPagination.init(this, opts);
		});

	}; // end flexigrid

})(jQuery);
