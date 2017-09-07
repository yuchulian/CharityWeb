package com.jlqr.index;

import java.util.Enumeration;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.LoginInfo;

/**
 * 该Controller只包含登录界面和主菜单
 * @author LiQiRan
 *
 */
public class IndexController extends Controller {
	/**
	 * 初始化系统界面
	 */
	public void index() {
		LoginInfo loginInfo = getSessionAttr("loginInfo");
		if(null == loginInfo) {
			if(null == getSessionAttr("isClearSession")) {
				setSessionAttr("isClearSession", true);
			} else if((boolean) getSessionAttr("isClearSession")) {
				clearSession();
			}
		} else {
			redirect("/home");
		}
	}
	
	/**
	 * 登录
	 */
	public void login() {
		String redirectPage = "/", returnMsg = "";
		String loginName = StringUtils.trim(getPara("loginName")), loginPwd = StringUtils.trim(getPara("loginPwd")), rememberPwd = StringUtils.trim(getPara("rememberPwd"));
		LoginInfo loginInfo = null;
		EmployInfo employInfo = null;
		
		if(!StringUtils.equals("admin", loginName)) {
			//md5加密
		}
		
		if(StringUtils.isBlank(loginName)) {
			returnMsg = "账号必填";
		} else if (StringUtils.isBlank(loginPwd)) {
			returnMsg = "密码必填";
		} else {
			loginInfo = LoginInfo.dao.findFirst("select * from login_info where login_name = ? and login_pwd = ?", loginName, loginPwd);
			if(null != loginInfo) {
				redirectPage = "/home";
				employInfo = EmployInfo.dao.findById(loginInfo.getId());
			} else {
				returnMsg = "用户名或密码错误";
			}
		}
		
		setSessionAttr("returnMsg", returnMsg);
		setSessionAttr("loginInfo", loginInfo);
		setSessionAttr("employInfo", employInfo);
		removeSessionAttr("isClearSession");
		redirect(redirectPage);
	}
	
	/**
	 * 退出登录
	 */
	public void logOut() {
		clearSession();
		redirect("/");
	}
	
	/**
	 * 清除session
	 */
	private void clearSession() {
		HttpSession session = getSession();
		Enumeration<String> attributeNames = session.getAttributeNames();
		String attributeName = "";
//		Object sessionValue = null;
		while (attributeNames.hasMoreElements()) {
			attributeName = attributeNames.nextElement().toString();
//			sessionValue = getSessionAttr(attributeName);
//			System.out.println("{attributeName: "+attributeName+", sessionValue: "+sessionValue+"}");
			session.removeAttribute(attributeName);
		}
//		setSessionAttr("isClearSession", false);
	}
	
	/**
	 * 首页
	 */
	public void home() {
		
	}
	
}



