package com.jlqr.index;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.PowerInfo;
import com.jlqr.common.model.RoleInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.PowerInfoService;
import com.jlqr.service.RoleInfoService;

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
	
	@NewService("RoleInfoService")
	private RoleInfoService roleInfoService;
	
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
		
		if(StringUtils.isBlank(loginName)) {
			returnMsg = "账号必填";
		} else if (StringUtils.isBlank(loginPwd)) {
			returnMsg = "密码必填";
		} else {
			loginInfo = LoginInfo.dao.findFirst("select * from login_info where login_name = ? and login_pwd = ?", loginName, DigestUtils.md5Hex(loginPwd));
			if(null != loginInfo) {
				redirectPage = "/home";
				employView = employInfoService.findEmployViewById(loginInfo.getId());
				
				String loginRole = employView.getRoleId();
				if(StringUtils.isNotBlank(loginRole) && loginRole.length() > 2) {
					try {
						List<String> powerIdList = new ArrayList<String>();
						
						String powerPath = "";
						List<RoleInfo> roleInfoList = roleInfoService.roleInfoListInId(loginRole.substring(1, loginRole.length()-1));
						for (RoleInfo roleInfo : roleInfoList) {
							powerPath += roleInfo.getPowerPath();
						}
						
						String[] _powerIdList = powerPath.split("\\,");
						HashMap<String, String> powerIdMap = new HashMap<String, String>();
						for (String _powerId : _powerIdList) {
							if(StringUtils.isNotBlank(_powerId) && !powerIdMap.containsKey(_powerId)) {
								powerIdList.add(_powerId);
								powerIdMap.put(_powerId, _powerId);
							}
						}
						
						powerInfoList = powerInfoService.findPowerInfoListInId(StringUtils.join(powerIdList, ","));
					} catch (Exception e) {
						e.printStackTrace();
					}
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



