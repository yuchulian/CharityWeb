package com.admin.controller.page;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.ControllerUtil;
import com.admin.common.SystemUtil;
import com.admin.common.model.Dictionary;
import com.admin.common.model.EducationInfo;
import com.admin.common.model.ItemInfo;
import com.admin.common.model.LoginInfo;
import com.admin.common.model.LoginInfoView;
import com.admin.common.model.MangerInfo;
import com.admin.common.model.MangerInfoView;
import com.admin.common.model.RoleInfo;
import com.admin.common.model.WorkInfo;
import com.admin.interceptor.NewService;
import com.common.service.DictionaryService;
import com.common.service.LoginInfoService;
import com.common.service.MangerInfoService;
import com.common.service.RoleInfoService;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;

public class MangerInfoPage extends ControllerUtil {

	@NewService("MangerInfoService")
	private MangerInfoService mangerInfoService;
	
	@NewService("LoginInfoService")
	private LoginInfoService loginInfoService;
	
	@NewService("DictionaryService")
	private DictionaryService dictionaryService;
	
	@NewService("RoleInfoService")
	private RoleInfoService roleInfoService;
	
	public void index() {
		render("mangerInfoIndex.html");
	}
	//用户修改页面,此页面跳转可以进行修改
	public void mangerInfoEdit() {
		MangerInfo mangerInfo = new MangerInfo();
		List<Dictionary> mangerEducationList = null;
		List<Dictionary> mangerDegreeList= null;
		List<Dictionary> mangerLanguageList= null;
		List<Dictionary> mangerSpecialityList = null;
		try {
			mangerEducationList = dictionaryService.findDictionaryListByPId(PropKit.getInt("mangerEducationId"));
			mangerDegreeList = dictionaryService.findDictionaryListByPId(PropKit.getInt("mangerDegreeId"));
			mangerLanguageList = dictionaryService.findDictionaryListByPId(PropKit.getInt("mangerLanguageId"));
			mangerSpecialityList = dictionaryService.findDictionaryListByPId(PropKit.getInt("mangerSpecialityId"));
			
			Integer id = 0;
			if(StringUtils.isNotBlank(getPara("id"))) {
				id = getParaToInt("id");
			} else if(StringUtils.isNotBlank(getPara("oneself")) && getParaToBoolean("oneself")) {
				LoginInfoView loginInfoView = getSessionAttr("loginInfoView");
				id = loginInfoView.getId();
			}
			
			mangerInfo = mangerInfoService.findMangerInfoById(id);
			if(null == mangerInfo)
				mangerInfo = new MangerInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("mangerEducationList", mangerEducationList);
		setAttr("mangerDegreeList", mangerDegreeList);
		setAttr("mangerLanguageList",mangerLanguageList);
		setAttr("mangerSpecialityList", mangerSpecialityList);
		setAttr("mangerInfo", mangerInfo);
	}
	//用户详情页面,此页面用于合同选择的时候进行查看用户的信息,不能够进行修改
	public void mangerInfoDetail(){
		MangerInfoView mangerInfo = new MangerInfoView();
		//教育经历
		List<EducationInfo> educationInfoList = null;
		//工作经历
		List<WorkInfo> workInfoList = null;
		//项目经历
		List<ItemInfo> itemInfoList = null;
		Integer mangerId = getParaToInt("id");
		try {
			mangerInfo = mangerInfoService.findMangerInfoViewById(mangerId);
			educationInfoList = mangerInfoService.educationInfoList(mangerId);
			workInfoList = mangerInfoService.workInfoList(mangerId);
			itemInfoList = mangerInfoService.itemInfoList(mangerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("mangerInfo", mangerInfo);
		setAttr("educationInfoList",educationInfoList);
		setAttr("workInfoList", workInfoList);
		setAttr("itemInfoList", itemInfoList);
	}

	//编辑用户账号信息
	public void mangerViewEdit(){
		MangerInfo mangerInfo = getModel(MangerInfo.class,"mangerInfo");
		LoginInfo loginInfo = new LoginInfo();
		List<Dictionary> projectDepartment = null;
		List<RoleInfo> roleInfoList = null;
		try {
			loginInfo = loginInfoService.findLoginInfoById(mangerInfo.getId());
			if(null == loginInfo) {
				loginInfo = new LoginInfo();
				loginInfo.setLoginName(mangerInfo.getMangerName());
			}
			
			projectDepartment = dictionaryService.findDictionaryListByPId(PropKit.getInt("projectDepartment"));
			
			LoginInfoView loginInfoView = getSessionAttr("loginInfoView");
			roleInfoList = roleInfoService.roleInfoByGradePlus(loginInfoView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("mangerInfo", mangerInfo);
		setAttr("loginInfo", loginInfo);
		setAttr("projectDepartment", JsonKit.toJson(SystemUtil.toDictionaryList(projectDepartment, loginInfo)));
		setAttr("roleInfoList", JsonKit.toJson(SystemUtil.toRoleInfoList(roleInfoList, loginInfo)));
	}
	//选择专业证书页面
	public void mangerDiplomaSelectIndex(){
		
	}
	
	/**
	 * 教育经历
	 */
	public void educationInfoEdit() {
		EducationInfo educationInfo = new EducationInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				educationInfo = mangerInfoService.educationInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("educationInfo", educationInfo);
	}
	
	/**
	 * 工作经历
	 */
	public void workInfoEdit() {
		WorkInfo workInfo = new WorkInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				workInfo = mangerInfoService.workInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("workInfo", workInfo);
	}
	
	/**
	 * 项目经验
	 */
	public void itemInfoEdit() {
		ItemInfo itemInfo = new ItemInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				itemInfo = mangerInfoService.itemInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("itemInfo", itemInfo);
	}

	
	//账号设置界面
	public void loginInfoEdit(){
		
	}
	
}