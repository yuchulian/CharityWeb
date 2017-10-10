$(function() {
	util.temp.browserType = util.browserType();
	util.initUpload();
	
	var $body = $("body");
	
	var options = {
		sbl : "sb-l-o", 
		sbr : "sb-r-c",
		collapse : "sb-l-m",
		siblingRope : true
	};
	
	//如果菜单栏存在滚动条且标签固定，则初始化插件
	if ($('.nano.affix').length) {
		$(".nano.affix").nanoScroller({
			preventPageScrolling : true
		});
	}
	$body.addClass('onload-check');
	
	login.initWindow();
	$(window).resize(function() {
		login.initWindow();
	});
	
	
	
	
	
	
	
	
	var panelScroller = $('.panel-scroller');
	if (panelScroller.length) {
		panelScroller.each(function(i, e) {
			var This = $(e);
			var Delay = This.data('scroller-delay');
			var Margin = 5;

			// Check if scroller bar margin is required
			if (This.hasClass('scroller-thick')) {
				Margin = 0;
			}

			// Check if scroller bar is in a dropdown, if so 
			// we initilize scroller after dropdown is visible
			var DropMenuParent = This.parents('.dropdown-menu');
			if (DropMenuParent.length) {
				DropMenuParent.prev('.dropdown-toggle').on('click',
						function() {
							setTimeout(function() {
								This.scroller();
								$('.navbar').scrollLock('on', 'div');
							}, 50);
						});
				return;
			}

			if (Delay) {
				var Timer = setTimeout(function() {
					This.scroller({
						trackMargin : Margin,
					});
					$('#content').scrollLock('on', 'div');
				}, Delay);
			} else {
				This.scroller({
					trackMargin : Margin,
				});
				$('#content').scrollLock('on', 'div');
			}

		});
	}


	login.initMenu($("#sidebar_left ul:first"), location.pathname);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$body.delegate(".request-fullscreen", "click", function(event) {
		var selector = $('html');
	    var ua = window.navigator.userAgent;
	    var old_ie = ua.indexOf('MSIE ');
	    var new_ie = ua.indexOf('Trident/');
	    if ((old_ie > -1) || (new_ie > -1)) {
	    	selector = $('body');
	    }
		
		var fullscreenCheck = $.fullscreen.isNativelySupported();
		if(fullscreenCheck) {
			if ($.fullscreen.isFullScreen()) {
				$.fullscreen.exit();
			} else {
				selector.fullscreen({
					overflow: 'auto'
				});
			}
		} else {
			util.alert("当前浏览器并不支持全屏");
		}
	}).delegate(".checkbox_thead", "change", function() {
		var checked = $(this).is(":checked"), name = $(this).attr("name"), $table = $(this).parents("table");
		$table.find(".checkbox_tbody").prop("checked", checked);
		$batch_delete = $table.parents(".row").find(".batch_delete");
		if($batch_delete.length) {
			if(checked) {
				if($table.find(".checkbox_tbody[name='"+name+"']").length) {
					$batch_delete.removeAttr("disabled");
				}
			} else {
				$batch_delete.attr("disabled", "disabled");
			}
		}
	}).delegate(".checkbox_tbody", "change", function() {
		var name = $(this).attr("name"), $table = $(this).parents("table");
		var checkbox_tbody_length = $table.find(".checkbox_tbody[name='"+name+"']").length, checked_length = $table.find(".checkbox_tbody[name='"+name+"']:checked").length,
			$batch_delete = $table.parents(".row").find(".batch_delete");
		if(checkbox_tbody_length == checked_length) {
			$table.find(".checkbox_thead").prop("checked", "checked");
		} else if(checkbox_tbody_length - 1 == checked_length) {
			$table.find(".checkbox_thead").removeAttr("checked");
		}
		if($batch_delete.length && !checked_length) {
			$batch_delete.attr("disabled", "disabled");
		} else if($batch_delete.length && checked_length == 1) {
			$batch_delete.removeAttr("disabled");
		}
	}).delegate(".selectFile", "click", function(event) {
		util.selectFile(event.target);
	}).delegate(".selectImg", "click", function(event) {
		util.selectImg(event.target);
	}).delegate(".docs-buttons [data-method]", "click", function(event) {
		var $this = $(this);
		var data = $this.data();
		var $target;

		if ($this.prop("disabled") || $this.hasClass("disabled")) {
			return;
		}

		if (util.temp.$SCREENSHOT_IMG.data("cropper") && data.method) {
			data = $.extend({}, data);

			if (typeof data.target !== "undefined") {
				$target = $(data.target);

				if (typeof data.option === "undefined") {
					try {
						data.option = JSON.parse($target.val());
					} catch (e) {
						console.log(e.message);
					}
				}
			}

			if (data.method === "rotate") {
				util.temp.$SCREENSHOT_IMG.cropper("clear");
			}

			util.temp.$SCREENSHOT_IMG.cropper(data.method, data.option, data.secondOption);

			if (data.method === "rotate") {
				util.temp.$SCREENSHOT_IMG.cropper("crop");
			}
			
			switch (data.method) {
			case "scaleX":
			case "scaleY":
				$(this).data("option", -data.option);
				break;
			}

		}
	}).delegate("#SCREENSHOT_FILE", "change", function() {
		var URL = window.URL || window.webkitURL;
		var blobURL;

		if (URL) {
			var files = this.files;
			var file;

			if (!util.temp.$SCREENSHOT_IMG.data("cropper")) {
				return;
			}

			if (files && files.length) {
				file = files[0];
				var _fileType = file.name.match(/\.[^\.][a-zA-Z0-9]+$/)[0];
				if(util.temp.fileType && (","+util.temp.fileType+",").indexOf(_fileType) == -1) {
					util.alert("请选择以("+util.temp.fileType+")为后缀的文件");
					util.temp.$SCREENSHOT_FILE.val("");
					return;
				}
				if (/^image\/\w+$/.test(file.type)) {
					blobURL = URL.createObjectURL(file);
					util.temp.$SCREENSHOT_IMG.one("built.cropper", function() {
						URL.revokeObjectURL(blobURL);
					}).cropper("reset").cropper("replace", blobURL);
					util.temp.$SCREENSHOT_FILE.val("");
				} else {
					util.alert("请选择图片");
				}
			}
		} else {
			util.temp.$SCREENSHOT_FILE.prop("disabled", true).parent().addClass("disabled");
		}
	}).delegate(".sidebar-toggle-mini", "click", function(e) {
		//菜单栏中单击按钮左缩进
		e.preventDefault();
		$body.addClass('sb-l-c');
		login.triggerResize();
		if (!$body.hasClass('mobile-view')) {
			setTimeout(function() {
				$body.toggleClass('sb-l-m sb-l-o');
			}, 250);
		}
	}).delegate("#toggle_sidemenu_l", "click", function() {
		//左菜单收缩或舒张
		if ($body.hasClass('sb-l-c') && options.collapse === "sb-l-m") {
			$body.removeClass('sb-l-c');
		}
		$body.toggleClass(options.collapse).removeClass('sb-r-o').addClass('sb-r-c');
		login.triggerResize();
	}).delegate(".sidebar-menu li a.accordion-toggle", "click", function(e) {
		// Any menu item with the accordion class is a dropdown submenu. Thus we prevent default actions
		e.preventDefault();

		// Any menu item with the accordion class is a dropdown submenu. Thus we prevent default actions
		if ($body.hasClass('sb-l-m') && !$(this).parents('ul.sub-nav').length) {
			return;
		}

		// Any menu item with the accordion class is a dropdown submenu. Thus we prevent default actions
		if (!$(this).parents('ul.sub-nav').length) {
			$('a.accordion-toggle.menu-open').next('ul').slideUp('fast', 'swing', function() {
				$(this).attr('style', '').prev().removeClass('menu-open');
			});
		} else {
			var activeMenu = $(this).next('ul.sub-nav');
			var siblingMenu = $(this).parent().siblings('li').children('a.accordion-toggle.menu-open').next('ul.sub-nav');

			activeMenu.slideUp('fast', 'swing', function() {
				$(this).attr('style', '').prev().removeClass('menu-open');
			});
			siblingMenu.slideUp('fast', 'swing', function() {
				$(this).attr('style', '').prev().removeClass('menu-open');
			});
		}

		if (!$(this).hasClass('menu-open')) {
			$(this).next('ul').slideToggle('fast', 'swing', function() {
				$(this).attr('style', '').prev().toggleClass('menu-open');
			});
		}
	}).delegate(".navbar-search", "click", function(e) {
		//手机端中，单击搜索，突出搜索框

		var This = $(this);
		var searchForm = This.find('input');
		var searchRemove = This.find('.search-remove');

		//如果不是手机模式，什么事都不干
		if (!$('body.mobile-view').length) {
			return;
		}

		// Open search bar and add closing icon if one isn't found
		This.addClass('search-open');
		if (!searchRemove.length) {
			This.append('<div class="search-remove"></div>');
		}

		// Fadein remove btn and focus search input on animation complete
		setTimeout(function() {
			This.find('.search-remove').fadeIn();
			searchForm.focus().one('keydown', function() {
				$(this).val('');
			});
		}, 250)

		// If remove icon clicked close search bar
		if ($(e.target).attr('class') == 'search-remove') {
			This.removeClass('search-open').find('.search-remove').remove();
		}
	
	}).delegate("#skin-toolbox .panel-heading", "click", function() {
		//右下角浮动选项卡
		$('#skin-toolbox').toggleClass('toolbox-open');
	}).delegate(".dropdown-menu.dropdown-persist", "click", function(e) {
		//阻止冒泡
		e.stopPropagation();
	}).delegate(".dropdown-menu .nav-tabs li a", "click", function(e) {
		//阻止冒泡
		e.preventDefault();
		e.stopPropagation();
		$(this).tab('show');
	}).delegate(".dropdown-menu .btn-group-nav a", "click", function(e) {
		//选项卡切换
		e.preventDefault();
		e.stopPropagation();
		$(this).siblings('a').removeClass('active').end().addClass('active').tab('show');
	}).delegate("", "click", function() {
		
	}).delegate("", "click", function() {
		
	}).delegate("", "click", function() {
		
	}).delegate("", "click", function() {
		
	});
});


var login, Login = function() { };
Login.prototype = {
	projectState: {
		1: "草稿",
		2: "报备",
		3: "立项"
	},
	reimburseState: {
		1: "草稿",
		2: "待审批",
		3: "通过",
		4: "不通过",
	},
	initWebRefresh : function() {
		$("#webRefresh").attr("onclick", "");
		//绑定我的信息
		util.modal({
			bind: {
				"#webRefresh": {
					pageHtml: "确定要刷新吗？"
				}
			},
			button : {
				"submit" : {
					name : "确定",
					className : "btn-primary",
					click : function(dataMap) {
						util.call("/Admin/News/webRefresh", {}, function(data) {
							util.closeModal(dataMap);
							util.alert(data["content"]);
						});
					}
				}
			}
		});
	},
	/*初始化我的信息*/
	initMyUserInfo : function() {
		$("#myUserInfo").attr("onclick", "");
		//绑定我的信息
		util.modal({
			pageUrl : "/Admin/Admin/adminEdit",
			title : "我的信息",
			bind: {
				"#myUserInfo": {
					"id" : login.userId
				}
			},
			button : {
				"submit" : {
					name : "确定",
					className : "btn-primary",
					click : function(dataMap) {
						 $('#adminForm').submit();
						 util.hideLoading();
					}
				}
			}
		});
	},
	initExitWarn : function() {
		$("#exit_warn").attr("onclick", "");
		//绑定我的信息
		util.modal({
			pageHtml : "确定要退出系统吗？",
			bind: {
				"#exit_warn": { }
			},
			button : {
				"submit" : {
					name : "确定",
					className : "btn-primary",
					click : function(dataMap) {
						window.location.href = "/Admin/Admin/exit";
					}
				}
			}
		});
	},
	triggerResize : function() {
		var $body = $("body");
		setTimeout(function() {
			$(window).trigger('resize');
			if ($body.hasClass('sb-l-m')) {
				$body.addClass('sb-l-disable-animation');
			} else {
				$body.removeClass('sb-l-disable-animation');
			}
		}, 300)
	},
	initWindow : function() {
		var $body = $("body");
		if ($(window).width() < 1080) {
			if(!$body.hasClass("mobile-view")) {
				$body.removeClass('sb-r-o').addClass('mobile-view sb-l-m sb-r-c');
			}
		} else {
			$body.removeClass('mobile-view sb-r-c');
		}
	},
	initMenu : function($ul, pathname) {
		var a_self = {};
		$ul.find("a").each(function() {
			a_self = $(this);
//			if(pathname.indexOf($.trim(a_self.attr("href"))) > -1) {
			if(pathname == $.trim(a_self.attr("href"))) {
				a_self.parent("li").attr("class", "active");
				var li_parents = {};
				a_self.parents("li").each(function() {
					$(this).find("> a.accordion-toggle").addClass("menu-open");
					li_parents = $(this);
				});
				$("#sidebar_left > div:first").scrollTop(li_parents.index()*37);
				return false;
			}
		});
	}
};
login = new Login();