package com.jlqr.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;
import com.jlqr.common.SystemUtil;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.PowerInfo;
import com.jlqr.common.model.RoleInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.PowerInfoService;
import com.jlqr.service.RoleInfoService;

/**
 * 该Controller只包含登录界面
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
				SystemUtil.clearSession(getSession());
//				clearSession();
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
		EmployView employView = null;//员工信息
		List<String> menuList = new ArrayList<String>();//菜单集合
		boolean isFinish = false;//是否完成
		List<PowerInfo> powerInfoList = null;
		List<String> powerUrlList = new ArrayList<String>();
		powerUrlList.add("uploadData");
		powerUrlList.add("downloadData");
		
		if(StringUtils.isBlank(loginName)) {
			returnMsg = "账号必填";
		} else if (StringUtils.isBlank(loginPwd)) {
			returnMsg = "密码必填";
		} else {
			LoginInfo loginInfo = LoginInfo.dao.findFirst("select * from login_info where login_name = ? and login_pwd = ?", loginName, DigestUtils.md5Hex(loginPwd));
			if(null != loginInfo) {
				redirectPage = "/home";
				employView = employInfoService.findEmployViewById(loginInfo.getId());

				try {
					String loginRole = employView.getRoleId();
					if(StringUtils.isNotBlank(loginRole) && loginRole.length() > 2) {
						List<String> powerIdList = new ArrayList<String>();
						
						String powerPath = "";
						Integer roleGrade = 6;
						List<RoleInfo> roleInfoList = roleInfoService.roleInfoListInId(loginRole.substring(1, loginRole.length()-1));
						for (RoleInfo roleInfo : roleInfoList) {
							if(roleGrade > roleInfo.getRoleGrade()) {
								roleGrade = roleInfo.getRoleGrade();
							}
							powerPath += roleInfo.getPowerPath();
						}
						
						if(roleGrade == 1) {
							powerInfoList = powerInfoService.powerInfoList(this);
						} else {
							String[] _powerIdList = powerPath.split("\\,");
							HashMap<String, String> powerIdMap = new HashMap<String, String>();
							for (String _powerId : _powerIdList) {
								if(StringUtils.isNotBlank(_powerId) && !powerIdMap.containsKey(_powerId)) {
									powerIdList.add(_powerId);
									powerIdMap.put(_powerId, _powerId);
								}
							}
							
							powerInfoList = powerInfoService.findPowerInfoListInId(StringUtils.join(powerIdList, ","));
						}

						HashMap<Integer, Object> powerPidMap = new HashMap<Integer, Object>();//权限键值对
						List<Record> recordList = new ArrayList<Record>();
						Record record = new Record();
						for (PowerInfo powerInfo : powerInfoList) {
							if(StringUtils.isNotBlank(powerInfo.getPowerUrl())) {
								powerUrlList.add(powerInfo.getPowerUrl());
								if(powerInfo.getPowerUrl().indexOf("Page") > -1) {
									powerUrlList.add(powerInfo.getPowerUrl().replaceAll("Page", "Data"));
								}
							}
							
							record = powerInfo.toRecord();
							record.set("children", new ArrayList<Record>());
							record.set("grade", powerInfo.getPowerIdPath().split("\\,").length - 1);
							if(0 == powerInfo.getPowerPid()) {
								powerPidMap.put(powerInfo.getId(), null);
								recordList.add(record);
							} else if(powerPidMap.containsKey(powerInfo.getPowerPid())) {
								powerPidMap.put(powerInfo.getId(), null);
								mergeRecordList(isFinish, recordList, record);
							}
						}
						mergeMenuList(recordList, menuList);
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
			} else {
				returnMsg = "用户名或密码错误";
			}
		}
		
		setSessionAttr("employView", employView);
		setSessionAttr("returnMsg", returnMsg);
		setSessionAttr("powerInfoList", powerInfoList);
		setSessionAttr("powerUrlList", powerUrlList);
		setSessionAttr("menu", StringUtils.join(menuList, ""));
		removeSessionAttr("isClearSession");
		redirect(redirectPage);
//		renderJson(JsonKit.toJson(initPowerInfoList(powerInfoList)));
	}
	
	/**
	 * 退出登录
	 */
	public void logOut() {
//		clearSession();
		SystemUtil.clearSession(getSession());
		redirect("/");
	}
	
	/**
	 * 首页
	 */
	public void home() {
		
	}
	
	/**
	 * 更新recordList
	 */
	private void mergeRecordList(boolean isFinish, List<Record> recordList, Record record) {
		if(!isFinish) {
			List<Record> children = new ArrayList<Record>();
			for (Record _record : recordList) {
				children = _record.get("children");
				if(_record.getInt("id") == record.getInt("power_pid")) {
					children.add(record);
					isFinish = true;
					return;
				} else {
					mergeRecordList(isFinish, children, record);
				}
			}
		}
	}
	
	/**
	 * 初始化菜单
	 */
	private void mergeMenuList(List<Record> recordList, List<String> menuList) {
		List<Record> children = new ArrayList<Record>();
		boolean noChildren = false;
		for (Record record : recordList) {
			children = record.get("children");
			noChildren = 0 == children.size();
			
			menuList.add("<li>");
			
			if(record.getInt("grade") < 3) {
				if(noChildren) {
					menuList.add("	<a href='"+StringUtils.defaultIfEmpty(record.getStr("power_url"), "javascript:void(0);")+"'>");
				} else {
					menuList.add("	<a href='javascript:void(0);' class='accordion-toggle'>");
				}
				menuList.add("			<span class='"+record.getStr("power_ico")+"'></span>");
				
				if(record.getInt("grade") == 1) {
					menuList.add("		<span class='sidebar-title'>"+record.getStr("power_name")+"</span>");
				} else if(record.getInt("grade") == 2) {
					menuList.add(		record.getStr("power_name"));
				}
				
				if(noChildren) {
//					menuList.add("		<span class='sidebar-title-tray hidden'>");
//					menuList.add("			<span class='label label-xs bg-primary'>New</span>");
//					menuList.add("		</span>");
				} else {
					menuList.add("		<span class='caret'></span>");
				}
				menuList.add("		</a>");
				
				if(!noChildren) {
					menuList.add("	<ul class='nav sub-nav'>");
					mergeMenuList(children, menuList);
					menuList.add("	</ul>");
				}
			} else {
				menuList.add("<a href='"+StringUtils.defaultIfEmpty(record.getStr("power_url"), "javascript:void(0);")+"'>"+record.getStr("power_name")+"</a>");
			}
			
			menuList.add("</li>");
		}
	}
	
}



