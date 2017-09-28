package com.jlqr.controller.page;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.SystemUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.RoleInfo;
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
			if(StringUtils.isNotBlank(getPara("id"))) {
				employInfo = employInfoService.findEmployInfoById(getParaToInt("id"));
				if(null == employInfo)
					employInfo = new EmployInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("employEducationList", employEducationList);
		setAttr("employDegreeList", employDegreeList);
		setAttr("employLanguageList",employLanguageList);
		setAttr("employSpecialityList", employSpecialityList);
		setAttr("employInfo", employInfo);
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
			
			EmployView employView = getSessionAttr("employView");
			roleInfoList = roleInfoService.roleInfoByGradePlus(employView);
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("employInfo", employInfo);
		setAttr("loginInfo", loginInfo);
		setAttr("projectDepartment", JsonKit.toJson(SystemUtil.toDictionaryList(projectDepartment, loginInfo)));
		setAttr("roleInfoList", JsonKit.toJson(SystemUtil.toRoleInfoList(roleInfoList, loginInfo)));
	}
	//选择专业证书
	public void employDiplomaSelect(){
		Integer employSpeciality = PropKit.getInt("employ_speciality");
		List<HashMap> employDiplomaSelectList = null;
		try {
			employDiplomaSelectList = dictionaryService.employDiplomaSelect(this,employSpeciality);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAttr("employDiplomaSelectList", employDiplomaSelectList);
	} 
	
}