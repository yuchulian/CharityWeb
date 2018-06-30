package com.admin.common;

import java.util.HashMap;

import com.admin.interceptor.SessionInterceptor;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

/**
 * 公用的Controller
 * @author LiQiRan
 * @date 2017-9-1 12:01:04
 */
@Before(SessionInterceptor.class)
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

	/**
	 * 如果t为null，将返回t的空对象
	 * @param t
	 * @return
	 */
	/*public <T> T newObject(T t) {
		try {
			t = null == t ? (T)(new Object()) : t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}*/
	
}
