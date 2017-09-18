package com.jlqr.index;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.PowerInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.PowerInfoService;

/**
 * 该Controller只包含登录界面和主菜单
 * @author LiQiRan
 *
 */
public class IndexController extends Controller {
//	@NewService("RoleInfoService")
//	private RoleInfoService roleService;
	
	@NewService("EmployInfoService")
	private EmployInfoService employInfoService;
	
	@NewService("PowerInfoService")
	private PowerInfoService powerInfoService;
	
	/**
	 * 初始化系统界面
	 */
	public void index() {
		EmployView employView = getSessionAttr("employView");
		if(null == employView) {
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
		EmployView employView = null;
//		EmployInfo employInfo = null;
//		RoleInfo roleInfo = null;
		List<PowerInfo> powerInfoList = null;
		
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
				employView = employInfoService.findEmployViewById(loginInfo.getId());
//				employInfo = EmployInfo.dao.findById(loginInfo.getId());
				
				//获取该用户的角色权限信息并保存到session上
//				roleInfo = roleService.getPodwerbyroleid(loginInfo.getRoleId());
				
				try {
					powerInfoList = powerInfoService.powerInfoList(this);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				returnMsg = "用户名或密码错误";
			}
		}
		
//		setSessionAttr("roleInfo", roleInfo);
//		setSessionAttr("employInfo", employInfo);
		setSessionAttr("employView", employView);
		setSessionAttr("returnMsg", returnMsg);
		setSessionAttr("loginInfo", loginInfo);
		setSessionAttr("powerInfoList", powerInfoList);
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



