package com.jlqr.common;

import java.util.HashMap;

import com.jfinal.core.Controller;

/**
 * 公用的Controller
 * @author LiQiRan
 * @date 2017-9-1 12:01:04
 */
public abstract class ControllerUtil extends Controller {

	private static HashMap<String, Object> _returnMap = new HashMap<String, Object>();

	/**
	 * 初始化响应的消息
	 * @return 
	 */
	protected static HashMap<String, Object> getReturnMap() {
		_returnMap.put("returnState", "error");
		_returnMap.put("returnMsg", "操作失败");
		return _returnMap;
	}
	
}
