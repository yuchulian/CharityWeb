$(function() {
	util.temp.browserType = util.browserType();
	util.initUpload();
	$("body").delegate(".menu_ul_two > li", "click", function(event) {
		$(".menu_ul_two li.active").removeClass("active");
		$(this).addClass("active").siblings(".active").removeClass("active");
		event.stopPropagation();
	}).delegate(".menu_ul_one > li", "click", function() {
		if(!util.temp.$siblings_finish) return;
		util.temp.$siblings_finish = false;
//		$(this).find(".fa-caret").toggleClass("fa-caret-right").toggleClass("fa-caret-down");
		var $siblings = $(this).addClass("active").siblings(".active").removeClass("active");
		$siblings.find(">a").addClass("collapsed").attr("aria-expanded", false);//.find(".fa-caret").removeClass("fa-caret-down").addClass("fa-caret-right");
		$siblings.find("ul").animate({height: 0}, 300);
		setTimeout(function() {
			$siblings.find("ul").removeClass().addClass("collapse menu_ul_two");
			util.temp.$siblings_finish = true;
		}, 300);
		if(!$(this).find("ul").length) {
			$siblings.find("ul > li").removeClass("active");
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
	}).delegate(".nav:not(.menu_ul_one,.menu_ul_two) > li", "click", function(event) {
		$(this).addClass("active").siblings(".active").removeClass("active");
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
	});
});


var login, Login = function() { };
Login.prototype = {
	userId : "用户Id",
	userName : "用户账号",
	name : "用户姓名",
	userPath : "用户Path",
	userDept : "用户部门",
	
	roleId : "角色Id",
	roleName : "角色名称",
	rolePid : "角色Pid",
	rolePath : "角色Path",
	webId : "子站id",
	rolePrivilege : "角色权限",
	roleChannel : "角色栏目",
	isSuperAdmin : false,
	isAdmin : false,
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
	}
};
login = new Login();