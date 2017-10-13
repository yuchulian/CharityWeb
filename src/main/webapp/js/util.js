
/**
 * 集成增删查改，封装一些常用方法
 * @author LiQiRan
 * @QQ 243736993
 * @date 2017-5-18 16:20:04
 */
var util, Util = function() {};
Util.prototype = {
	temp : {
		$siblings_finish : true,
		browserType : "",
		dateId : {},
		fileSuffixRegular : /\.[a-zA-Z0-9]+$/g,
		_pageNumberLimitTimeout : {},
		callBack : "", fileType : "", $SCREENSHOT_IMG : {}, $SCREENSHOT_FILE : {},
		uploadHTML : '<form id="_uploadForm" style="display: none;"> <input type="file" name="filePath" onchange="util.uploadFile(this);" id="_filePath"> </form>',
		loadingHTML : '<div class="modal-backdrop fade in" id="_loading_id" style="z-index: zIndex"><div style="position: absolute; height: 100%; width: 100%; background: url(\'../img/loading.gif\') no-repeat center center"></div></div>',
		modalHTML : '<div class="modal fade" id="_MYMODALID" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> <div class="modal-dialog" style="width: _MODALWIDTH"> <div class="modal-content"> <div class="modal-header"> <span class="close" data-dismiss="modal" aria-hidden="true">×</span> <h4 class="modal-title"> _MYMODALTITLE </h4> </div> <div class="modal-body" style="height: _MODALHEIGHT"> _MAINHTML </div> <div class="modal-footer"> <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> _MYMODALBUTTON </div> </div> </div> </div>',
		selectImgHTML : '<div class="form-horizontal"> <div class="col-md-9"> <div class="img-container"> <img id="SCREENSHOT_IMG" src="/downloadData/img?filePath=" alt="请选择图片"> </div> </div> <div class="col-md-3"> <div class="col-md-12 docs-buttons"> <div class="btn-group"> <label class="btn btn-primary btn-upload" for="SCREENSHOT_FILE" style="background-color: white; border-color: white; color: #2D79AB; font-size: 50px;"> <input type="file" class="sr-only" id="SCREENSHOT_FILE" name="file" accept=".jpg,.jpeg,.png,.gif,.bmp,.tiff"> <span class="docs-tooltip" data-toggle="tooltip" title="选择图片"> <span class="icon-upload-alt"></span> </span> </label> <button type="button" class="btn btn-primary" data-method="reset" style="background-color: white; border-color: white; color: #2D79AB; font-size: 50px; margin-left: 50px;"> <span class="docs-tooltip" data-toggle="tooltip" title="刷新"> <span class="icon-refresh"></span> </span> </button> </div> </div> <div class="docs-preview clearfix"> <div class="img-preview preview-lg"></div> <div class="img-preview preview-md"></div> <div class="img-preview preview-sm"></div> <div class="img-preview preview-xs"></div> </div> <div class="col-md-12 docs-buttons" style="margin-top: 20px;"> <div class="btn-group"> <button type="button" class="btn btn-primary" data-method="disable"> <span class="docs-tooltip" data-toggle="tooltip" title="锁定"> <span class="icon-lock"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="enable"> <span class="docs-tooltip" data-toggle="tooltip" title="解锁"> <span class="icon-unlock"></span> </span> </button> </div> <div class="btn-group"> <button type="button" class="btn btn-primary" data-method="crop"> <span class="docs-tooltip" data-toggle="tooltip" title="开启截图"> <span class="icon-ok"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="clear"> <span class="docs-tooltip" data-toggle="tooltip" title="关闭截图"> <span class="icon-off"></span> </span> </button> </div> <div class="btn-group"> <button type="button" class="btn btn-primary" data-method="setDragMode" data-option="move"> <span class="docs-tooltip" data-toggle="tooltip" title="拖动"> <span class="icon-move"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="setDragMode" data-option="crop"> <span class="docs-tooltip" data-toggle="tooltip" title="裁剪"> <span class="icon-cut"></span> </span> </button> </div> <div class="btn-group"> <button type="button" class="btn btn-primary" data-method="zoom" data-option="0.1"> <span class="docs-tooltip" data-toggle="tooltip" title="放大"> <span class="icon-zoom-in"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="zoom" data-option="-0.1"> <span class="docs-tooltip" data-toggle="tooltip" title="缩小"> <span class="icon-zoom-out"></span> </span> </button> </div> <div class="btn-group"> <button type="button" class="btn btn-primary" data-method="rotate" data-option="-45"> <span class="docs-tooltip" data-toggle="tooltip" title="向左旋转"> <span class="icon-undo"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="rotate" data-option="45"> <span class="docs-tooltip" data-toggle="tooltip" title="向右旋转"> <span class="icon-repeat"></span> </span> </button> </div> <div class="btn-group"> <button type="button" class="btn btn-primary" data-method="scaleX" data-option="-1"> <span class="docs-tooltip" data-toggle="tooltip" title="水平反射"> <span class="icon-resize-horizontal"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="scaleY" data-option="-1"> <span class="docs-tooltip" data-toggle="tooltip" title="垂直反射"> <span class="icon-resize-vertical"></span> </span> </button> </div> <div class="btn-group"> <button type="button" class="btn btn-primary" data-method="move" data-option="-10" data-second-option="0"> <span class="docs-tooltip" data-toggle="tooltip" title="向左移动"> <span class="icon-arrow-left"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="move" data-option="10" data-second-option="0"> <span class="docs-tooltip" data-toggle="tooltip" title="向右移动"> <span class="icon-arrow-right"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="move" data-option="0" data-second-option="-10"> <span class="docs-tooltip" data-toggle="tooltip" title="向上移动"> <span class="icon-arrow-up"></span> </span> </button> <button type="button" class="btn btn-primary" data-method="move" data-option="0" data-second-option="10"> <span class="docs-tooltip" data-toggle="tooltip" title="向下移动"> <span class="icon-arrow-down"></span> </span> </button> </div> </div> </div> </div>'
	},
	/**
	 * 初始化页面的数据，集成增删查改
	 * @param dataUrl 数据访问路径
	 * @param condition 增删查改的配置信息
	 * @param callbackFn 回调函数
	 */
	initPage : function(dataUrl, condition, callbackFn) {
		util.showLoading();
		var paginate = condition["paginate"] = condition["paginate"] || {},
			search = condition["search"] = condition["search"] || {},
			searchUrl = condition["searchUrl"] = condition["searchUrl"] || "",
			modal = condition["modal"] = condition["modal"] || {},
			_page = condition["_page"] = condition["_page"] || this.randomLetter(),
			isPaginate = this._isPaginate(condition),
			callbackMap = {};
		callbackMap["refresh"] = this._isRefresh(condition);
//		if("{}" != JSON.stringify(search)) {
			callbackMap["search"] = search["search"] = search["search"] || this.randomLetter();
			callbackMap["reset"] = search["reset"] = search["reset"] || this.randomLetter();
//		}
		
		if(isPaginate) {
			callbackMap["paginate"] = paginate["paginate"] = paginate["paginate"] || this.randomLetter();
			callbackMap["pageSize"] = paginate["pageSize"] = paginate["pageSize"] || this.pageSize() || 10;
		}
		
		if(dataUrl) {
			callbackMap["searchUrl"] = searchUrl = searchUrl || this._search({ dataUrl: dataUrl.replace(/\/+$/g,""), condition: condition, callbackFn: callbackFn }, false, false);

			if(isPaginate) {
				dataUrl += "/";
				var paginateId = paginate["paginateId"] || "",
					pageNumber = paginate["pageNumber"] || 1,
					pageSize = paginate["pageSize"];
					callbackMap["pageNumber"] = pageNumber;
					callbackMap["pageSize"] = pageSize,
					$checkbox_thead = $(paginateId).parents(".row").find(".checkbox_thead");
				if($checkbox_thead.length) {
					$checkbox_thead.removeAttr("checked").change();
				}
				if(pageNumber > 0 || pageSize > 0) {
//					console.info("分页路径为："+dataUrl+pageNumber+"-"+pageSize+searchUrl);
					$.post(dataUrl+pageNumber+"-"+pageSize, searchUrl, function(pageData) {
						if(util.hasSession(pageData)) {
							$(paginateId).html(util._pageHTML({
								pageData: pageData,
								
								dataUrl: dataUrl.replace(/\/+$/g,""),
								condition: condition,
								callbackFn: callbackFn
							}));
							callbackFn($.extend({list: pageData["list"] || pageData || []}, callbackMap));
							util.hideLoading();
							if(!(pageData["list"] || []).length && pageNumber > 1) {
								condition["paginate"]["pageNumber"] = pageNumber - 1;
								util.initPage(dataUrl.replace(/\/+$/g,""), condition, callbackFn);
							}
//								$(paginateId).html("<div class='pull-right pagination'></div>");
//								$(paginateId).html("<div class='pull-right pagination'>暂无数据……</div>");
							
							util._scrollTop();
						}
					});
				}
			} else {
//				console.info("列表路径为："+dataUrl+searchUrl);
				$.post(dataUrl, searchUrl, function(listData) {
					if(util.hasSession(listData)) {
						callbackFn($.extend({list: listData || []}, callbackMap));
						util.hideLoading();
						util._scrollTop();
					}
				});
			}
			
			
		}
			
		if("{}" != JSON.stringify(modal)) {
			var modalKey = "", modalObj = {};
			for(modalKey in modal) {
				modalObj = modal[modalKey];
				if(!modalObj["modalFn"]) {
					callbackMap[modalKey] = modalObj["modalFn"] = modalObj["modalFn"] || this.randomLetter();
					modalObj["_page"] = _page;
					this.modal(modalObj);
				} else {
					callbackMap[modalKey] = modalObj["modalFn"] = modalObj["modalFn"] || this.randomLetter();
				}
			}
		}
		callbackMap["_page"] = _page;
		util.temp[_page] = callbackMap;
		return callbackMap;
	},
	randomMethod : function(callbackFn, randomLetter) {
		randomLetter = randomLetter ? randomLetter : this.randomLetter();
		if(!this.exist(randomLetter)) {
			Util.prototype[randomLetter] = function(para1, para2, para3, para4, para5) { callbackFn(para1, para2, para3, para4, para5); }
		}
		return randomLetter;
	},
	randomLetter : function() {
		//76 81 82
		//0-9 48-57
		//A-Z 65-90
		//a-z 97-122
		var foreverList = [76, 81, 82], randomList = [[97,122], [65,90], [48,57]];
		var range = [], randomLetter = [], differ = 0, charIndex = 0;
		for(var i = 0; i < foreverList.length; i++) randomLetter.push(String.fromCharCode(foreverList[i]));
		for(var i = 0; i < randomList.length; i++) {
			range = randomList[i];
			differ = range[1] - range[0];
			for(var j=0; j<Math.ceil(differ/10); j++) {
				charIndex = Math.round(Math.random()*differ) + range[0];
				randomLetter.push(String.fromCharCode(charIndex));
			}
		}
		return util[randomLetter.join("")] ? this.randomLetter() : randomLetter.join("");
	},
	getPageUrl : function() {
		var pageUrl = location.pathname || "";
		return pageUrl.replace(/\/+((\d+[\d\s\-a-zA-Z]*)|)$/g, "");
	},
	_pageHTML : function(dataMap) {
		var pageUrl = util.getPageUrl() + "/",
			pageData = dataMap["pageData"] || {},
			
			dataUrl = dataMap["dataUrl"],
			condition = dataMap["condition"],
			callbackFn = dataMap["callbackFn"];
		var pageSize = condition["paginate"]["pageSize"];
		
		var refresh = this._isRefresh(condition), randomLetter = condition["paginate"]["paginate"];
		if(!refresh) {
			this.randomMethod(function(_pageNumber, _paginate) {
				condition["paginate"] = $.extend(condition["paginate"], _paginate || {});
				condition["paginate"]["pageNumber"] = _pageNumber;
				util.initPage(dataUrl, condition, callbackFn);
			}, randomLetter);
		}
		
		var pageNumber = pageData["pageNumber"], totalPage = pageData["totalPage"], pageHTML = [], pageNumberInput = this.randomLetter();

		if(!pageNumber || !totalPage || totalPage <=0 || pageNumber > totalPage) return pageHTML.join("");

		var startPage = pageNumber - 4;
		if(startPage < 1) startPage = 1;

		var endPage = pageNumber + 4;
		if(endPage > totalPage) endPage = totalPage;

		pageHTML.push('<div class="pull-right pagination">');
		pageHTML.push('	<ul class="pagination">');
		if(pageNumber <= 8) startPage = 1;
		if(totalPage - pageNumber < 8) endPage = totalPage;

//		if(pageNumber == 1) pageHTML.push('<span class="disabled prev_page">上页</span>');
		if(pageNumber == 1) pageHTML.push('<li class="ng-scope"><a href="javascript:void(0);" class="ng-binding" style="cursor: not-allowed;">上页</a></li>');
		else pageHTML.push(this._pageHTML_a({
			href : pageUrl+(pageNumber - 1)+"-"+pageSize,
			content : "上页",
			className : "prev_page",
			
			pageNumber : pageNumber - 1,
			randomLetter : randomLetter,
			refresh : refresh
		}));

		if(pageNumber > 8) {
			pageHTML.push(this._pageHTML_a({
				href : pageUrl+1+"-"+pageSize,
				content : 1,
				
				pageNumber : 1,
				randomLetter : randomLetter,
				refresh : refresh
			}));
			pageHTML.push(this._pageHTML_a({
				href : pageUrl+2+"-"+pageSize,
				content : 2,
				
				pageNumber : 2,
				randomLetter : randomLetter,
				refresh : refresh
			}));
//			pageHTML.push('<span class="gap">…</span>');
			pageHTML.push('<li class="ng-scope"><a href="javascript:void(0);" class="ng-binding">…</a></li>');
		}

		for(var i=startPage; i<=endPage; i++) {
//			if(pageNumber == i) pageHTML.push('<span class="current">',i,'</span>');
			if(pageNumber == i) pageHTML.push('<li class="ng-scope active"><a href="javascript:void(0);" class="ng-binding">',i,'</a></li>');
			else pageHTML.push(this._pageHTML_a({
				href : pageUrl+i+"-"+pageSize,
				content : i,
				
				pageNumber : i,
				randomLetter : randomLetter,
				refresh : refresh
			}));
		}

		if(totalPage - pageNumber >= 8) {
//			pageHTML.push('<span class="gap">…</span>');
			pageHTML.push('<li class="ng-scope"><a href="javascript:void(0);" class="ng-binding">…</a></li>');
			pageHTML.push(this._pageHTML_a({
				href : pageUrl+(totalPage - 1)+"-"+pageSize,
				content : totalPage - 1,
				
				pageNumber : totalPage - 1,
				randomLetter : randomLetter,
				refresh : refresh
			}));
			pageHTML.push(this._pageHTML_a({
				href : pageUrl+totalPage+"-"+pageSize,
				content : totalPage,
				
				pageNumber : totalPage,
				randomLetter : randomLetter,
				refresh : refresh
			}));
		}

//		if(pageNumber == totalPage) pageHTML.push('<span class="disabled next_page">下页</span>');
		if(pageNumber == totalPage) pageHTML.push('<li class="ng-scope"><a href="javascript:void(0);" class="ng-binding" style="cursor: not-allowed;">下页</a></li>');
		else pageHTML.push(this._pageHTML_a({
			href : pageUrl+(pageNumber + 1)+"-"+pageSize,
			content : "下页",
			className : "next_page",
			attribute : "rel=\"next\"",
			
			pageNumber : pageNumber + 1,
			randomLetter : randomLetter,
			refresh : refresh
		}));

		pageHTML.push('<li class="ng-scope"><a href="javascript:void(0);" class="ng-binding" style="border-top: none; border-bottom: none;">&nbsp;</a></li>');
		
		pageHTML.push('<li class="ng-scope"><a href="javascript:void(0);" class="ng-binding" style="padding: 0;"><select style="border: none; width: 38px; height: 32px; text-align: center;" onchange="util.execute(\''+randomLetter+'\', 1, {pageSize: $(this).val()});">');
		var pageSizeList = [10, 15, 20, 30, 50];
		if(("#"+pageSizeList.join("#")+"#").indexOf("#"+pageSize+"#") == -1) {
			pageSizeList.push(pageSize*1);
			pageSizeList.sort(function(a, b){ return a-b; });
		}
		for(var i=0; i<pageSizeList.length; i++) {
			if(pageSize == pageSizeList[i]) {
				pageHTML.push('<option value="'+pageSizeList[i]+'" selected="selected">',pageSizeList[i],'</option>');
			} else {
				pageHTML.push('<option value="'+pageSizeList[i]+'">',pageSizeList[i],'</option>');
			}
		}
		pageHTML.push('</select></a></li>');
		
		pageHTML.push('<li class="ng-scope"><a href="javascript:void(0);" class="ng-binding" style="padding: 0;"><input type="number" style="border: none; width: 88px; height: 32px; text-align: center;" min="1" max="'+totalPage+'" value="'+pageNumber+'" onclick="$(this).focus().select();" onkeyup="util._pageNumberLimit(this,\''+randomLetter+'\');" onchange="util._pageNumberLimit(this,\''+randomLetter+'\');" id="'+pageNumberInput+'"/></a></li>');
		pageHTML.push('<li class="ng-scope"><a href="javascript:void(0);" class="ng-binding" onclick="util._pageNumberLimit(\'#'+pageNumberInput+'\', \''+randomLetter+'\', 13);">跳转</a></li>');
		
		pageHTML.push('	</ul>');
		pageHTML.push('</div>');

		return pageHTML.join(" ");
	},
	_pageHTML_a : function(dataMap) {
		var href = dataMap["href"],
			content = dataMap["content"],
			className = dataMap["className"] || "",
			attribute = dataMap["attribute"] || "",
			
			pageNumber = dataMap["pageNumber"],
			randomLetter = dataMap["randomLetter"],
			refresh = dataMap["refresh"];
		
		if(!href) {
			href = "javascript:void(0);";
		}
		if(refresh) {
//			return '<a href="'+href+'" class="'+className+'" '+attribute+'>'+content+'</a>';
			return '<li class="ng-scope"><a href="'+href+'" class="'+className+' ng-binding" '+attribute+'>'+content+'</a></li>';
		} else {
		return '<li class="ng-scope"><a href="javascript:void(0);" class="'+className+' ng-binding" '+attribute+' onclick="util.execute(\''+randomLetter+'\','+pageNumber+');">'+content+'</a></li>';
//		return '<a href="javascript:void(0);" class="'+className+'" '+attribute+' onclick="util.execute(\''+randomLetter+'\','+pageNumber+');">'+content+'</a>';
		}
	},
	_pageNumberLimit : function(self, callbackFn, keyCode) {
		keyCode = keyCode || event.keyCode;
		clearTimeout(this.temp._pageNumberLimitTimeout);
		this.temp._pageNumberLimitTimeout = setTimeout(function() {
			var min = $(self).attr("min"),
			max = $(self).attr("max"),
			pageNumber = $.trim($(self).val());
			if(!(/^[0-9]+$/.test(pageNumber))) {
				pageNumber = 1;
			}
			pageNumber = pageNumber*1;
			if(pageNumber < min) pageNumber = min;
			if(pageNumber > max) pageNumber = max;
			$(self).val(pageNumber);
			if(13 == keyCode) {
				util.execute(callbackFn, pageNumber);
			}
		}, 200);
	},
	_search : function(dataMap, isSearch, isReset) {
		var	dataUrl = dataMap["dataUrl"],
			condition = dataMap["condition"],
			callbackFn = dataMap["callbackFn"];
		var search = condition["search"] || {},
			sort = condition["sort"] || {},
			table = condition["table"] || {};
		sort = this._mergeSort(sort);
		var bind = search["bind"] || {}, columnMap = search["column"] || {},
			searchUrl = {}, columnKey = "", columnValue = [],
			refresh = this._isRefresh(condition);
		if(!isSearch && !isReset) {
			this.randomMethod(function() {
				var _searchUrl = util._search(dataMap, true, false);
				if(refresh) {
					var pageUrl = util.getPageUrl();
					location.href = pageUrl + (_searchUrl?"?"+_searchUrl:"");
				} else {
					condition["searchUrl"] = _searchUrl;
					if(util._isPaginate(condition)) {
						condition["paginate"]["pageNumber"] = 1;
					}
					util.initPage(dataUrl, condition, callbackFn);
				}
			}, search["search"]);
			if(bind["searchId"]) {
				this.setAttr(bind["searchId"], "onclick", "util.execute('"+search["search"]+"');");
			}
			if(bind["resetId"]) {
				this.randomMethod(function() {
					var _searchUrl = util._search(dataMap, false, true);
					if(refresh) {
						var pageUrl = util.getPageUrl();
						location.href = pageUrl + (_searchUrl?"?"+_searchUrl:"");
					} else {
						condition["searchUrl"] = _searchUrl;
						if(util._isPaginate(condition)) {
							condition["paginate"]["pageNumber"] = 1;
						}
						util.initPage(dataUrl, condition, callbackFn);
					}
				}, search["reset"]);
			
				this.setAttr(bind["resetId"], "onclick", "util.execute('"+search["reset"]+"');");
			}
		}
		var searchValue = "";
		for(columnKey in columnMap) {
			searchValue = "";
			columnValue = columnMap[columnKey] || [];
			searchValue = this.getValue(columnValue[1], columnValue[2]);
			if(isReset) {
				if(columnValue[3] != false) {
					this.setValue(columnValue[1], "");
					searchValue = columnValue[2] || "";
				}
			}
			/*if(isSearch) {
				searchValue = this.getValue(columnValue[1], columnValue[2]);
			} else if(isReset) {
				if(columnValue[3] == false) {
					searchValue = this.getValue(columnValue[1], columnValue[2]);
				} else {
					this.setValue(columnValue[1], "");
					searchValue = columnValue[2] || "";
				}
			} else {
				searchValue = this.getValue(columnValue[1], columnValue[2]);
			}*/
			searchUrl[columnKey] = '('+columnValue[0]+')'+this.encodeURI(searchValue);
		}
		
		//需要根据前缀序号排序
		var sortUrl = [], sortKey = "";
		for(sortKey in sort) {
			sortUrl.push(sortKey+" "+sort[sortKey]);
		}
		if(sortUrl.length) {
			searchUrl["sort"] = sortUrl.join(",");
		}
		
		var columnUrl = table["column"] || [];
		if(columnUrl.length) {
			searchUrl["column"] = columnUrl.join(",");
		}
		
		return searchUrl;
	},
	_isPaginate : function(condition) {
		var paginate = condition["paginate"] || {};
		return "{}" != JSON.stringify(paginate) && paginate["paginateId"]
	},
	_isRefresh : function(condition) {
		return false;
//		return condition["refresh"] == false ? false : true;
	},
	_mergeSort : function(sort) {
		var sortList = [], sortKeyValue = [], sortMap = {};
		if(sort.hasOwnProperty("sort")) {
			if(sort["sort"]) {
				if("asc" == sort["sort"] || "desc" == sort["sort"]) {
					sortMap["sort"] = sort["sort"];
				} else {
					sortList = this.decodeURI(sort["sort"]).split(",");
					for(var i=0; i<sortList.length; i++) {
						sortKeyValue = $.trim(sortList[i]).split(" ");
						if(sortKeyValue.length == 2) {
							sortMap[$.trim(sortKeyValue[0])] = $.trim(sortKeyValue[1]);
						}
					}
				}
			}
			delete sort["sort"];
		}
		return $.extend(sort, sortMap);
	},
	/**
	 * 根据标签id获取的值
	 * @param id 标签id
	 * @param value 标签的默认值
	 */
	getValue : function(id, value) {
		var idList = id.split(","), thisValue = [];
		for(var i=0; i<idList.length; i++) {
			switch (($(idList[i])[0] || {tagName:""})["tagName"]) {
			case "INPUT":
				thisValue.push($.trim($(idList[i]).val()));
				break;
			case "SELECT":
				thisValue.push($.trim($(idList[i]).val()));
				break;
			}
		}
		return $.trim(thisValue.join(",")+"") || value || "";
	},
	/**
	 * 根据标签id填写值
	 * @param id 标签id
	 * @param value 填写的值
	 */
	setValue : function(id, value) {
		var idList = id.split(",");
		for(var i=0; i<idList.length; i++) {
			switch (($(idList[i])[0] || {tagName:""})["tagName"]) {
			case "INPUT":
				$(idList[i]).val(value);
				break;
			case "SELECT":
				$(idList[i]).val(value);
				break;
			}
		}
	},
	/**
	 * 将String转换为int
	 * @param para 要转换的值
	 * @param defaultValue 默认值
	 */
	getParaToInt : function(para, defaultValue) {
		defaultValue = defaultValue || 0;
		if(para && para*1) {
			defaultValue = para*1;
		}
		return defaultValue;
	},
	/**
	 * 如果字符串，则添加默认值
	 * @param para 字符串
	 * @param defaultValue 默认值
	 */
	getPara : function(para, defaultValue) {
		return para || defaultValue || "";
	},
	/**
	 * 解码
	 * @param return 如：张三
	 */
	encodeURI : function(url) {
		if("IE" == this.temp.browserType) {
			return url?encodeURIComponent(url):"";
		} else {
			return url || "";
		}
	},
	/**
	 * 编码
	 * @param return 如：%E5%BC%A0%E4%B8%89
	 */
	decodeURI : function(url) {
		return url?decodeURIComponent(url):"";
	},
	/**
	 * 判断当前使用的是什么浏览器
	 */
	browserType : function() {
		var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
		
		//判断是否Opera浏览器
		var isOpera = userAgent.indexOf("Opera") > -1;
		if (isOpera) {
			return "Opera";
		}
		
		//判断是否Firefox浏览器
		if (userAgent.indexOf("Firefox") > -1) {
			return "Firefox";
		}
		
		//判断是否Chrome浏览器
		if (userAgent.indexOf("Chrome") > -1) {
			return "Chrome";
		}
		
		//判断是否Safari浏览器
		if (userAgent.indexOf("Safari") > -1) {
			return "Safari";
		}
		
		//判断是否IE浏览器
		if (!!window.ActiveXObject || "ActiveXObject" in window) {
			var ie = !-[1,];//如果是ie8或ie8以下，就会返回true，否则返回false
			return "IE";
		}
	},
	pageSize : function() {
		var trHeight = 37,
			differ = 300,
			pageTop = $(".pageTop").height(),
			windowHeight = $(window).height();
		var pageSize = Math.floor((windowHeight-differ-pageTop)/37);
		pageSize = pageSize>0?pageSize:1;
		return pageSize;
	},
	/**
	 * 初始化modal
	 * @param dataMap 初始化modal的配置信息
	 * @param executeMap 初始化modal后立即执行
	 */
	modal : function(dataMap, executeMap) {
		dataMap = dataMap || {};
		var randomLetter = dataMap["modalFn"] = dataMap["modalFn"] || this.randomLetter(), bind = dataMap["bind"], bindKey = "";
		this.randomMethod(function(_executeMap) {
			_executeMap = _executeMap || {};
			util.showLoading();
			dataMap = $.extend(dataMap, _executeMap);
			if(dataMap["pageUrl"]) {
				$.post(dataMap["pageUrl"], _executeMap, function(pageHtml) {
					if(util.hasSession(pageHtml)) {
						delete _executeMap["pageHtml"];
						delete _executeMap["pageScript"];
						pageHtml = util.temp.modalHTML.replace("_MAINHTML", pageHtml);
						util.hideLoading();
						util._modalShow(pageHtml, dataMap, _executeMap);
					}
				});
			} else if(dataMap["pageHtml"]) {
				var _pageHtml = dataMap["pageHtml"];
				delete _executeMap["pageHtml"];
				delete _executeMap["pageScript"];
				_pageHtml = util.temp.modalHTML.replace("_MAINHTML", _pageHtml);
				util.hideLoading();
				util._modalShow(_pageHtml, dataMap, _executeMap);
			}
		}, randomLetter);
		if(executeMap && "{}" != JSON.stringify(executeMap)) {
			this.execute(randomLetter, executeMap);
		}
		if(bind && "{}" != JSON.stringify(bind)) {
			for(bindKey in bind) {
				this.setAttr(bindKey, "onclick", "util.execute('"+randomLetter+"', "+this.mapToString(bind[bindKey] || {},"'")+");");
				$(bindKey).show();
			}
		}
		return randomLetter;
	},
	/**
	 * 系统提示
	 * @param alertMsg 提示内容
	 * @param callbackFn 回调函数
	 */
	alert : function(alertMsg, callbackFn) {
		if(!this.temp.reminder)
			this.temp.reminder = util.modal();
		if(alertMsg)
			util.execute(this.temp.reminder, {pageHtml: alertMsg});
	},
	/**
	 * 根据id添加标签属性
	 * @param id 标签id
	 * @param attrKey 标签属性
	 * @param _attrValue 标签属性值
	 */
	setAttr : function(id, attrKey, _attrValue) {
		var attrValue = $(id).attr(attrKey) || "";
		if(attrValue) attrValue = attrValue.replace(/;+$/g, "") + ";";
		if(_attrValue) attrValue += _attrValue;
		$(id).attr(attrKey, attrValue);
	},
	_modalShow : function(pageHtml, dataMap, _executeMap) {
		var buttonHTML = [], buttonKey = "", buttonObj = {},
			attributeHTML = [], attributeKey = "", attributeValue = "",
			button = dataMap["button"], randomLetter = dataMap["modalFn"], buttonFn = "";
		for(buttonKey in button) {
			buttonObj = button[buttonKey];
			
			attributeHTML = [];
			for(attributeKey in buttonObj["attribute"]) {
				attributeValue = buttonObj["attribute"][attributeKey];
				attributeHTML.push(attributeKey+'="'+attributeValue+'" ');
			}
			
			buttonHTML.push(this._modalButton(randomLetter, buttonKey, buttonObj, $.extend(_executeMap, {
				_page: dataMap["_page"],
				_modal: randomLetter,
				_button: buttonFn
			}), attributeHTML));
		}
		if(dataMap["width"]) {
			if(/[\d]$/g.test(dataMap["width"])) {
				dataMap["width"] += "px";
			}
		} else {
			dataMap["width"] = "600px";
		}
		if(dataMap["height"]) {
			if("cover" == dataMap["height"]) {
				var windowHeight = $(window).height();
				dataMap["height"] = windowHeight < 800 ? 578 : windowHeight*1-222;
			}
			if(/[\d]$/g.test(dataMap["height"])) {
				dataMap["height"] += "px; overflow: auto;";
			}
		} else {
			dataMap["height"] = "auto";
		}
		pageHtml = pageHtml.replace("_MODALWIDTH", dataMap["width"]).replace("_MODALHEIGHT", dataMap["height"]).replace("_MYMODALID", randomLetter).replace("_MYMODALTITLE", dataMap["title"] || "系统提示").replace("_MYMODALBUTTON", buttonHTML.join(""));
		
		$("#"+randomLetter).next(".modal-backdrop").remove();
		$("#"+randomLetter).remove();
		$("body").append(pageHtml);
		$("#"+randomLetter).modal({
	        keyboard: false,
	        id: randomLetter
	    });
		
		if("function" == typeof dataMap["pageScript"]) {
			dataMap["pageScript"](dataMap);
		}
		
	},
	_modalButton : function(randomLetter, buttonKey, buttonObj, _executeMap, attributeHTML) {
		var buttonHTML = [];
		if(buttonObj["click"]) {
			var buttonFn = randomLetter+"_"+buttonKey;
			this.randomMethod(function(_modalMap) {
				util.showLoading();
				buttonObj["click"]($.extend(_executeMap, _modalMap));
			}, buttonFn);
			buttonHTML.push('<button type="button" class="btn ',buttonObj["className"],'" ',attributeHTML.join(""),' onclick="util.execute(\'',buttonFn,'\',',this.mapToString(_executeMap,"\'"),');">',buttonObj["name"],'</button>');
		} else {
			buttonHTML.push('<button type="button" class="btn ',buttonObj["className"],'" ',attributeHTML.join(""),'>',buttonObj["name"],'</button>');
		}
		return buttonHTML.join("");
	},
	/**
	 * 刷新列表
	 * @param dataMap modal默认信息
	 */
	refreshPage : function(dataMap) {
		var pageTemp = this.temp[dataMap["_page"]];
		if(pageTemp["paginate"]) {
			if(pageTemp["refresh"]) {
				location.reload();
			} else {
				util.execute(pageTemp["paginate"], pageTemp["pageNumber"]);
			}
		} else {
			if(pageTemp["refresh"]) {
				location.reload();
			} else {
				util.execute(pageTemp["search"]);
			}
		}
	},
	/**
	 * 获取form表单的信息
	 * @param dataMap modal默认信息或form表单id
	 */
	getModal : function(dataMap) {
		var formMap = {}, formId = "";
		switch (typeof dataMap) {
		case "object":
			formId = "#"+dataMap["_modal"]+" form";
			break;
		case "string":
			formId = dataMap;
			break;
		}
		var formData = $(formId).serializeArray();
		$.each(formData, function() {
			formMap[this.name] = this.value;
		});
		return formMap;
	},
	/**
	 * 关闭modal
	 * @param dataMap modal默认信息或form表单id
	 */
	closeModal : function(dataMap) {
		switch (typeof dataMap) {
		case "object":
			$("#"+dataMap["_modal"]).modal('hide');
			break;
		case "string":
			$(dataMap).parents(".modal").modal('hide');
			break;
		}
	},
	/**
	 * 将map转换成字符串
	 * @param map 要转换的map
	 * @param symbol 间隔
	 */
	mapToString : function(map, symbol) {
		var mapInfo = [], mapKey = "";
		if(map) {
			for(mapKey in map) {
				mapInfo.push(symbol+mapKey+symbol+":"+symbol+map[mapKey]+symbol);
			}
		}
		return "{"+mapInfo.join(",")+"}";
	},
	exist : function(randomLetter) {
		return util[randomLetter] ? true : false;
	},
	/**
	 * 执行初始化的方法
	 * @param randomLetter 初始化唯一标示
	 * @param para1, para2, para3, para4, para5 执行参数，可不传
	 */
	execute : function(randomLetter, para1, para2, para3, para4, para5) {
		if(randomLetter && this.exist(randomLetter)) {
			util[randomLetter](para1, para2, para3, para4, para5);
		} else {
			console.info("不存在"+randomLetter+"这个方法");
		}
	},
	/**
	 * 访问服务器获取数据
	 * @param url 服务器路径
	 * @param urlData 访问参数
	 * @param callbackFn 回调函数
	 */
	call : function(url, urlData, callbackFn) {
		util.showLoading();
		$.post(url, urlData, function(returnData) {
			if(util.hasSession(returnData)) {
				util.hideLoading();
				callbackFn(returnData);
			}
		});
	},
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*格式化日期 start*/
	/**
	 * 格式化年月日
	 * @param dateStr 例如：2017-04-20 14:18:44或1492432830000
	 * @param formatStr 例如：yyyy-mm-dd hh:ii:ss
	 */
	formatDate : function(dateStr, formatStr) {
		if(!dateStr) {
			return "";
		}
		if(/[\d]$/g.test(dateStr)) {
			dateStr = dateStr*1;
		}
		
		if(!formatStr)
			formatStr = "yyyy-mm-dd";
		return this.getNowDate(new Date(dateStr), formatStr);
	},
	/**
	 * 获取当前年月日
	 * @param dateObj 日期对象Date()
	 * @param formatStr 例如：yyyy-mm-dd hh:ii:ss
	 */
	getNowDate : function(dateObj, formatStr) {
		if(!dateObj)
			dateObj = new Date();
		if(!formatStr)
			formatStr = "yyyy-mm-dd";
		var formatObj = $.fn.datetimepicker.DPGlobal.parseFormat(formatStr, "standard");
		
		var dateLong = this.formatDateToLong(dateObj) + 8 * 60 * 60 * 1000;
		return $.fn.datetimepicker.DPGlobal.formatDate(new Date(dateLong), formatObj, "zh-CN", "standard");;
	},
	/**
	 * 将日期字符串转换为long类型
	 * @param dateStr 例如：2017-04-20 14:18:44
	 */
	formatDateToLong : function(dateStr) {
		if(dateStr) {
			if("string" == (typeof dateStr)) {
				dateStr = dateStr.replace(/-/g, "/");
			}
			return (new Date(dateStr)).getTime();
//			return (new Date(dateStr)).valueOf();
		} else {
			return 0;
		}
	},
	/*格式化日期 end*/
	
	
	
	
	
	
	/*手机、固话号码验证 start*/
	isPhone : function(phoneNumber) {
		phoneNumber = $.trim(phoneNumber || "");
		var phoneRegular ={
			MOBILE : [/^1(3[4-9]|4[7]|5[0-27-9]|7[08]|8[2-478])\d{8}$/g, "中国移动"],
			CM : [/^1(3[0-2]|4[5]|5[56]|7[0156]|8[56])\d{8}$/g, "中国联通"],
			CU : [/^1(3[3]|4[9]|53|7[037]|8[019])\d{8}$/g, "中国电信"],
			PHS : [/^0(10|2[0-5789]|\d{3})[-_]?\d{7,8}$/g, "固话"]
		}, regularKey = "";
		for(regularKey in phoneRegular) {
			if(phoneRegular[regularKey][0].test(phoneNumber)) {
				return { isPhone : true, phoneType : phoneRegular[regularKey][1] };
			}
		}
		return { isPhone : false };
	},

	/*手机、固话号码验证 end*/
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*身份证号码验证 start*/
	powers : new Array("7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"),
	parityBit : new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"),
	sex : "male",
	isIdCard : function(_id) {
		_id = $.trim(_id || "");
		if (_id == "")
			return;
		var _valid = false;
		if (_id.length == 15) {
			_valid = this.validId15(_id);
		} else if (_id.length == 18) {
			_valid = this.validId18(_id);
		}
		return _valid;
	},
	validId18 : function(_id) {
		_id = _id + "";
		var _num = _id.substr(0, 17);
		var _parityBit = _id.substr(17);
		var _power = 0;
		for (var i = 0; i < 17; i++) {
			//校验每一位的合法性   

			if (_num.charAt(i) < '0' || _num.charAt(i) > '9') {
				return false;
				break;
			} else {
				//加权   

				_power += parseInt(_num.charAt(i)) * parseInt(this.powers[i]);
				//设置性别   

				if (i == 16 && parseInt(_num.charAt(i)) % 2 == 0) {
					this.sex = "female";
				} else {
					this.sex = "male";
				}
			}
		}
		//取模   

		var mod = parseInt(_power) % 11;
		if (this.parityBit[mod] == _parityBit) {
			return true;
		}
		return false;
	},
	validId15 : function(_id) {
		_id = _id + "";
		for (var i = 0; i < _id.length; i++) {
			//校验每一位的合法性   

			if (_id.charAt(i) < '0' || _id.charAt(i) > '9') {
				return false;
				break;
			}
		}
		var year = _id.substr(6, 2);
		var month = _id.substr(8, 2);
		var day = _id.substr(10, 2);
		var sexBit = _id.substr(14);
		//校验年份位   

		if (year < '01' || year > '90')
			return false;
		//校验月份   

		if (month < '01' || month > '12')
			return false;
		//校验日   

		if (day < '01' || day > '31')
			return false;
		//设置性别   

		if (sexBit % 2 == 0) {
			this.sex = "female";
		} else {
			this.sex = "male";
		}
		return true;
	},
	/*身份证号码验证 end*/
	

	/*邮政编码验证 start*/
	isPostcode : function(postcode) {
		postcode = $.trim(postcode || "");
		var postcodeRegular = /^[1-9]\d{5}(?!\d)$/g;
		return postcodeRegular.test(postcode)
	},
	/*邮政编码验证 end*/

	/*邮箱验证 start*/
	isEmail : function(email) {
		email = $.trim(email || "");
		var emailRegular = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/g;
		return emailRegular.test(email)
	},
	/*邮箱验证 end*/
	
	/**
	 * 切换功能模块页面。只能用于系统功能模块的切换
	 * @param href 访问路径
	 * @param parentId 页面要显示的位置id
	 * @param dataMap 访问参数
	 */
	switchPage : function(href, parentId, dataMap) {
		util.showLoading();
		for(var dateId in this.temp.dateId) {
			$(dateId).datetimepicker('remove');
		}
		$(".datetimepicker-dropdown-bottom-right").remove();
		this.temp.dateId = {};
		dataMap = dataMap || {};
		$.getScript("/Public/js/common/util.js", function(){
			$.post(href, dataMap, function(returnHTML) {
				util.hideLoading();
				if(util.hasSession(returnHTML)) {
					login.initWebRefresh();
					login.initMyUserInfo();
					login.initExitWarn();
					$(parentId).html(returnHTML);
					util._scrollTop();
					$("body").scrollLeft(0);
				}
			});
		});
	},
	/**
	 * 显示加载层
	 */
	showLoading : function() {
		if($("#_loading_id").html())
			this.hideLoading();
		$("body").append(this.temp.loadingHTML.replace("zIndex", this.getMaxZIndex()*1+10));
	},
	/**
	 * 隐藏加载层
	 */
	hideLoading : function() {
		$("#_loading_id").remove();
	},
	getMaxZIndex : function() {
		return $("body > .modal:last").css("z-index");
	},
	/**
	 * 初始化日期范围
	 * 1969年10月29日22点30分 互联网诞生
	 * @param startId 开始日期id
	 * @param endId 结束日期id
	 * @param startDate 开始日期初始值
	 * @param endDate 结束日期初始值
	 */
	initDateRange : function(startId, endId, startDate, endDate, noEndDate, config) {
		this.temp.dateId[startId] = startId;
		this.temp.dateId[endId] = endId;
		$(startId).val(startDate||"").attr("readonly", "readonly").datetimepicker($.extend({'startDate': '1969-10-29', 'endDate': noEndDate==true?"":this.getNowDate()}, config)).on('changeDate', function(ev){
//			console.info("选中时间的long类型为："+ev.date.valueOf());
			var _startDate = $(startId).val() || "", _endDate = $(endId).val() || "";
			if(util.formatDateToLong(_startDate) > util.formatDateToLong(_endDate)) {
				_endDate = _startDate;
			}
			$(endId).val(_endDate).datetimepicker('update').datetimepicker('show');
		});
		$(endId).val(endDate||"").attr("readonly", "readonly").datetimepicker($.extend({'startDate': '1969-10-29', 'endDate': noEndDate==true?"":this.getNowDate()}, config)).on('changeDate', function(ev){
			var _startDate = $(startId).val() || "", _endDate = $(endId).val() || "";
			if(!_startDate || util.formatDateToLong(_startDate) > util.formatDateToLong(_endDate)) {
				$(startId).val(_endDate).datetimepicker('update').datetimepicker('show');
			}
		});
	},
	/**
	 * 初始化日期
	 * @param dateId 日期id
	 * @param dateValue 日期默认值
	 */
	initDate : function(dateId, dateValue, noEndDate, config) {
		dateValue = dateValue=="NaN-NaN-NaN"?"":dateValue;
		this.temp.dateId[dateId] = dateId;
		$(dateId).val(dateValue||"").attr("readonly", "readonly").datetimepicker($.extend({'startDate': '1969-10-29', 'endDate': noEndDate==true?"":this.getNowDate()}, config));
	},
	
	initUpload : function() {
		$("#_uploadForm").remove();
		$("body").append(util.temp.uploadHTML);
	},
	selectFile : function(self) {
		var _uploadFile = $("#_filePath");
		util.temp.callBack = $.trim($(self).attr("callBack")) || "";
		util.temp.fileType = $.trim($(self).attr("fileType")) || "";
		_uploadFile.attr("accept", util._mergeFileType());
		return _uploadFile.click();
	},
	uploadFile : function(self) {
		var _uploadFile = $(self);
		var uploadFilePath = $.trim(_uploadFile.val());
		if(uploadFilePath) {
			var _fileType = uploadFilePath.match(/\.[^\.][a-zA-Z0-9]+$/)[0];
			if(util.temp.fileType && (","+util.temp.fileType+",").indexOf(_fileType) == -1) {
				util.alert("请选择以("+util.temp.fileType+")为后缀的文件");
				_uploadFile.val("");
				return;
			}
			util.showLoading();
			$("#_uploadForm").ajaxSubmit({
				url : "/uploadData",
				type : "post",
				enctype : 'multipart/form-data',
				iframe: true,
				dataType : 'json',
				success : function(returnMsg) {
					util.hideLoading();
					if(util.temp.callBack) {
						eval(util.temp.callBack+"("+JSON.stringify(returnMsg)+")");
					}
					util.temp.callBack = "";
					util.temp.fileType = "";
					_uploadFile.val("");
					_uploadFile.attr("accept", "");
				},
				error : function(returnMsg) {
					util.hideLoading();
					util.temp.callBack = "";
					util.temp.fileType = "";
					_uploadFile.val("");
					_uploadFile.attr("accept", "");
					util.alert("上传失败");
				}
			});
		}
	},
	selectImg : function(self) {
		util.temp.callBack = $.trim($(self).attr("callBack")) || "";
		util.temp.fileType = $.trim($(self).attr("fileType")) || "";
		var aspectRatio = $.trim($(self).attr("aspectRatio")) || 0;
		this.modal({
			title : "截图",
//			pageUrl : "/Admin/Carousel/imgEdit",
			pageHtml : util.temp.selectImgHTML,
			height : "cover",
			width : 1124,
			button : {
				"submit" : {
					name : "确定",
					className : "btn-primary",
					click : function(dataMap) {
						if(/^\/downloadData\//.test(util.temp.$SCREENSHOT_IMG.attr("src"))) {
							util.alert("请选择图片");
							return;
						}
						setTimeout(function() {
							var $screenshot_img_canvas = util.temp.$SCREENSHOT_IMG.cropper("getCroppedCanvas"), compression_ratio = 0.9;
							var screenshot_base64 = $screenshot_img_canvas.toDataURL("image/jpeg").replace("data:image/jpeg;base64,", "");
							while(screenshot_base64.length > 178168) {
								screenshot_base64 = $screenshot_img_canvas.toDataURL("image/jpeg", compression_ratio).replace("data:image/jpeg;base64,", "");
//								console.info(JSON.stringify({screenshot_base64_length: screenshot_base64.length, compression_ratio: compression_ratio}));
								if(compression_ratio > 0.11) {
									compression_ratio = ((compression_ratio - 0.1)+"").substring(0,4)*1;
								} else if(compression_ratio > 0.000003){
									compression_ratio = ((compression_ratio/2)+"").substring(0,9)*1;
								} else {
									util.alert("上传的图片太大！");
									return;
								}
							}
							util.call("/uploadData/ueditor", {
								action : "uploadscrawl",
								isTmep : true,
								upfile : screenshot_base64
							}, function(returnMsg) {
								if(util.temp.callBack) {
									eval(util.temp.callBack+"("+returnMsg+")");
								}
								util.temp.callBack = "";
								util.temp.fileType = "";
								util.temp.$SCREENSHOT_FILE = {};
								util.temp.$SCREENSHOT_IMG = {};
								util.closeModal(dataMap);
								util.hideLoading();
							});
						}, 10);
					}
				}
			}
		}, {
			callBack : util.temp.callBack
		});
		
		setTimeout(function() {
			util.temp.$SCREENSHOT_IMG = $("#SCREENSHOT_IMG");
			util.temp.$SCREENSHOT_FILE = $("#SCREENSHOT_FILE");
			if((aspectRatio+"").indexOf("/") > -1) {
				var aspectRatioList = aspectRatio.split("/");
				aspectRatio = aspectRatioList[0]*1/aspectRatioList[1]*1;
			}
			util.temp.$SCREENSHOT_IMG.cropper({
				aspectRatio : aspectRatio,
				preview : ".img-preview",
				viewMode : 1
			});
			util.temp.$SCREENSHOT_FILE.attr("accept", util._mergeFileType());
		}, 200);
	},
	selectIcon : function(self) {
		this.modal({
			title : "系统图标",
			pageUrl : "/selectIcon",
			height : "cover",
			width : 1124,
			button : {
				"submit" : {
					name : "确定",
					className : "btn-primary",
					click : function(dataMap) {
						var selectedIcon = $(".icon-demo-list li.active>span").attr("class");
						if(dataMap["callBack"] && selectedIcon) {
							eval(dataMap["callBack"]+"('"+selectedIcon+"')");
						}
						util.closeModal(dataMap);
						util.hideLoading();
					}
				}
			}
		}, {
			callBack : $.trim($(self).attr("callBack")) || ""
		});
	},
	_mergeFileType : function() {
		var splitSymbols = ["|"], splitSymbol = ",";
		for(var i=0; i<splitSymbols.length; i++) {
			if(util.temp.fileType.indexOf(splitSymbols[i]) > -1) {
				splitSymbol = splitSymbols[i];
			}
		}
		if(splitSymbol != ",") {
			return util.temp.fileType.split(splitSymbol).join(",");
		}
		return util.temp.fileType;
	},
	hasSession : function(returnMsg) {
		if("string" != (typeof returnMsg)) {
			returnMsg = JSON.stringify(returnMsg || {});
		}
		if(returnMsg.indexOf('action="/login"') > -1) {
			window.location.href = "/";
			return false;
		}
		return true;
	},
	_scrollTop : function() {
		$("body").scrollTop(0);
		$(".modal").scrollTop(0);
	},
	getFileSuffix : function(filePath) {
		return ((filePath || "").match(this.temp.fileSuffixRegular) || [""])[0];
	}
};
util = new Util();