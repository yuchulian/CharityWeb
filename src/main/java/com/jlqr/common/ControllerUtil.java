package com.jlqr.common;

import java.util.HashMap;

import com.jfinal.core.Controller;

/**
 * 公用的Controller
 * @author LiQiRan
 * @date 2017-9-1 12:01:04
 */
public abstract class ControllerUtil extends Controller {

	/**
	 * 初始化响应的消息
	 * @return 
	 */
	protected static HashMap<String, Object> getReturnMap() {
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("returnState", "error");
		returnMap.put("returnMsg", "操作失败");
		return returnMap;
	}
	
}
