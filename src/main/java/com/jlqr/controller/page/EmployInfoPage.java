package com.jlqr.controller.page;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.SystemUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.EducationInfo;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.EmployInfoView;
import com.jlqr.common.model.LoginInfoView;
import com.jlqr.common.model.ItemInfo;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.RoleInfo;
import com.jlqr.common.model.WorkInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.DictionaryService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.LoginInfoService;
import com.jlqr.service.RoleInfoService;

public class EmployInfoPage extends ControllerUtil {

	@NewService("EmployInfoService")
	private EmployInfoService employInfoService;
	
	@NewService("LoginInfoService")
	private LoginInfoService loginInfoService;
	
	@NewService("DictionaryService")
	private DictionaryService dictionaryService;
	
	@NewService("RoleInfoService")
	private RoleInfoService roleInfoService;
	
	public void index() {
		render("employInfoIndex.html");
	}
	//员工修改页面,此页面跳转可以进行修改
	public void employInfoEdit() {
		EmployInfo employInfo = new EmployInfo();
		List<Dictionary> employEducationList = null;
		List<Dictionary> employDegreeList= null;
		List<Dictionary> employLanguageList= null;
		List<Dictionary> employSpecialityList = null;
		try {
			employEducationList = dictionaryService.findDictionaryListByPId(PropKit.getInt("employEducationId"));
			employDegreeList = dictionaryService.findDictionaryListByPId(PropKit.getInt("employDegreeId"));
			employLanguageList = dictionaryService.findDictionaryListByPId(PropKit.getInt("employLanguageId"));
			employSpecialityList = dictionaryService.findDictionaryListByPId(PropKit.getInt("employSpecialityId"));
			
			Integer id = 0;
			if(StringUtils.isNotBlank(getPara("id"))) {
				id = getParaToInt("id");
			} else if(StringUtils.isNotBlank(getPara("oneself")) && getParaToBoolean("oneself")) {
				LoginInfoView loginInfoView = getSessionAttr("loginInfoView");
				id = loginInfoView.getId();
			}
			
			employInfo = employInfoService.findEmployInfoById(id);
			if(null == employInfo)
				employInfo = new EmployInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("employEducationList", employEducationList);
		setAttr("employDegreeList", employDegreeList);
		setAttr("employLanguageList",employLanguageList);
		setAttr("employSpecialityList", employSpecialityList);
		setAttr("employInfo", employInfo);
	}
	//员工详情页面,此页面用于合同选择的时候进行查看员工的信息,不能够进行修改
	public void employInfoDetail(){
		EmployInfoView employInfo = new EmployInfoView();
		//教育经历
		List<EducationInfo> educationInfoList = null;
		//工作经历
		List<WorkInfo> workInfoList = null;
		//项目经历
		List<ItemInfo> itemInfoList = null;
		Integer employId = getParaToInt("id");
		try {
			employInfo = employInfoService.findEmployInfoViewById(employId);
			educationInfoList = employInfoService.educationInfoList(employId);
			workInfoList = employInfoService.workInfoList(employId);
			itemInfoList = employInfoService.itemInfoList(employId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("employInfo", employInfo);
		setAttr("educationInfoList",educationInfoList);
		setAttr("workInfoList", workInfoList);
		setAttr("itemInfoList", itemInfoList);
	}

	//编辑员工账号信息
	public void employViewEdit(){
		EmployInfo employInfo = getModel(EmployInfo.class,"employInfo");
		LoginInfo loginInfo = new LoginInfo();
		List<Dictionary> projectDepartment = null;
		List<RoleInfo> roleInfoList = null;
		try {
			loginInfo = loginInfoService.findLoginInfoById(employInfo.getId());
			if(null == loginInfo) {
				loginInfo = new LoginInfo();
				loginInfo.setLoginName(employInfo.getEmployName());
			}
			
			projectDepartment = dictionaryService.findDictionaryListByPId(PropKit.getInt("projectDepartment"));
			
			LoginInfoView loginInfoView = getSessionAttr("loginInfoView");
			roleInfoList = roleInfoService.roleInfoByGradePlus(loginInfoView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("employInfo", employInfo);
		setAttr("loginInfo", loginInfo);
		setAttr("projectDepartment", JsonKit.toJson(SystemUtil.toDictionaryList(projectDepartment, loginInfo)));
		setAttr("roleInfoList", JsonKit.toJson(SystemUtil.toRoleInfoList(roleInfoList, loginInfo)));
	}
	//选择专业证书页面
	public void employDiplomaSelectIndex(){
		
	}
	
	/**
	 * 教育经历
	 */
	public void educationInfoEdit() {
		EducationInfo educationInfo = new EducationInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				educationInfo = employInfoService.educationInfoById(getParaToInt("id"));
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
				workInfo = employInfoService.workInfoById(getParaToInt("id"));
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
				itemInfo = employInfoService.itemInfoById(getParaToInt("id"));
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