package com.jlqr.interceptor;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jlqr.common.SystemUtil;
import com.jlqr.common.model.LoginInfoView;

/**
 * 判断session是否过期
 * @author LiQiRan
 */
public class SessionInterceptor implements Interceptor {
	
	public void intercept(Invocation inv) {
		String actionKey = inv.getActionKey();
		Controller controller = inv.getController();
		String requestMethod = controller.getRequest().getMethod();
		LoginInfoView loginInfoView = controller.getSessionAttr("loginInfoView");
		List<String> powerUrlList = controller.getSessionAttr("powerUrlList");
		if(null != loginInfoView || null != powerUrlList) {
			if(StringUtils.equals("POST", requestMethod)) {
				inv.invoke();
				return;
			} else {
				for (String powerUrl : powerUrlList) {
					if(actionKey.indexOf(powerUrl) > -1) {
						inv.invoke();
						return;
					}
				}
			}
		}
		SystemUtil.clearSession(controller.getSession());
		controller.redirect("/");
	}
	
}
