package com.admin.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.jfinal.handler.Handler;

/**
 * 加载完activiti的数据后，清除activitiMap
 * @author LiQiRan
 */
public class ClearActivitiMapHandler extends Handler {

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		next.handle(target, request, response, isHandled);
		if(StringUtils.equals("/projectInfoPage/projectInfoDetail", target) || StringUtils.equals("/reimburseInfoPage/reimburseInfoDetail", target)) {
			HttpSession session = request.getSession();
			session.removeAttribute("activitiMap");
		}
	}

}
