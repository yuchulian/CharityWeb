package com.admin.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.admin.common.SystemUtil;
import com.admin.common.model.LoginInfo;
import com.admin.common.model.LoginInfoView;
import com.admin.common.model.MangerInfo;
import com.admin.common.model.NewInfo;
import com.admin.common.model.PowerInfo;
import com.admin.common.model.RoleInfo;
import com.admin.interceptor.NewService;
import com.common.service.LoginInfoService;
import com.common.service.MangerInfoService;
import com.common.service.NewsService;
import com.common.service.PowerInfoService;
import com.common.service.RoleInfoService;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Record;

import net.sf.json.JSONArray;

/**
 * 该Controller只包含登录界面
 *
 *
 */
public class IndexController extends Controller {

//	@NewService("RoleInfoService")
//	private RoleInfoService roleService;
	
	@NewService("MangerInfoService")
	private MangerInfoService mangerInfoService;
	
	@NewService("RoleInfoService")
	private RoleInfoService roleInfoService;
	
	@NewService("PowerInfoService")
	private PowerInfoService powerInfoService;
	@NewService("LoginInfoService")
	LoginInfoService loginInfoService;
	
	@NewService("NewsService")
	private NewsService newsService;
	/**
	 * 初始化系统界面
	 */
	public void index() {
		LoginInfoView loginInfoView = getSessionAttr("loginInfoView");
		if(null == loginInfoView) {
			if(null == getSessionAttr("isClearSession")) {
				setSessionAttr("isClearSession", true);
			} else if((boolean) getSessionAttr("isClearSession")) {
				SystemUtil.clearSession(getSession());
//				clearSession();
			}
		} else {
			redirect("/Admin/home");
		}
	}
	
	/**
	 * 登录
	 */
	public void login() {
		String redirectPage = "/Admin", returnMsg = "";
		String loginName = StringUtils.trim(getPara("loginName")), loginPwd = StringUtils.trim(getPara("loginPwd")), rememberPwd = StringUtils.trim(getPara("rememberPwd"));
		LoginInfoView loginInfoView = null;//用户信息
		List<String> menuList = new ArrayList<String>();//菜单集合
		boolean isFinish = false;//是否完成
		List<PowerInfo> powerInfoList = null;
		List<String> powerUrlList = new ArrayList<String>();
		List<Integer> staffList = new ArrayList<Integer>();
		powerUrlList.add("uploadData");
		powerUrlList.add("downloadData");

		try {
			if(StringUtils.isBlank(loginName)) {
				returnMsg = "账号必填";
			} else if (StringUtils.isBlank(loginPwd)) {
				returnMsg = "密码必填";
			} else {
	//			LoginInfo loginInfo = LoginInfo.dao.findFirst("select * from login_info where login_name = ? and login_pwd = ?", loginName, DigestUtils.md5Hex(loginPwd));
				LoginInfo loginInfo = loginInfoService.loginInfoByNameAndPwd(loginName, loginPwd);
				if(null != loginInfo) {
					redirectPage = "/Admin/home";
					loginInfoView = mangerInfoService.findMangerViewById(loginInfo.getId());
	
						String loginRole = loginInfoView.getRoleId();
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
							
							/**
							 * 获取我的下级用户
							 */
							List<LoginInfoView> loginInfoViewList = mangerInfoService.findStaffList(loginInfoView);
							staffList.add(loginInfoView.getId());
							for (LoginInfoView _loginInfoView : loginInfoViewList) {
								staffList.add(_loginInfoView.getId());
							}
							
						}
					
					
				} else {
					returnMsg = "用户名或密码错误";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		setSessionAttr("loginInfoView", loginInfoView);
		setSessionAttr("returnMsg", returnMsg);
		setSessionAttr("powerInfoList", powerInfoList);
		setSessionAttr("powerUrlList", powerUrlList);
		setSessionAttr("menu", StringUtils.join(menuList, ""));
		setSessionAttr("staff", StringUtils.join(staffList, ","));
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
		redirect("/Admin");
	}
	
	/**
	 * 首页
	 */
	public void home() {
		//查询男女比例
		List<MangerInfo> findAllMangerInfo = null;
		findAllMangerInfo = mangerInfoService.findAllMangerInfo();
		List<Map<String,Object>> data = new ArrayList<>();
		Integer manSum = 0;
		Integer feManSum = 0;
		for (MangerInfo mangerInfo : findAllMangerInfo) {
			if(mangerInfo.getMangerSex()==1){
				manSum++;
			}else if(mangerInfo.getMangerSex()==2){
				feManSum++;
			}
		}
		Map<String,Object> manMap = new HashMap<String, Object>();
		manMap.put("name", "男");
		manMap.put("value", manSum);
		data.add(manMap);
		Map<String,Object> feManMap = new HashMap<String, Object>();
		feManMap.put("name", "女");
		feManMap.put("value", feManSum);
		data.add(feManMap);
		setAttr("data", JSONArray.fromObject(data));
		//查询新闻比例
		List<NewInfo> findAllNews = null;
		findAllNews = newsService.findAllNews();
		Integer noCheck = 0;
		Integer pass = 0;
		Integer noPass = 0;
		for (NewInfo newInfo : findAllNews) {
			if(newInfo.getNewsState()==1){
				noCheck++;
			}else if(newInfo.getNewsState()==2){
				pass++;
			}else if(newInfo.getNewsState()==3){
				noPass++;
			}
		}
		List<Object> newsData = new ArrayList<>();
		newsData.add(noCheck);
		newsData.add(pass);
		newsData.add(noPass);
		setAttr("newsData", JSONArray.fromObject(newsData));
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
	
	/**
	 * 显示框架所有图标
	 */
	public void selectIcon() {
		
	}
}



