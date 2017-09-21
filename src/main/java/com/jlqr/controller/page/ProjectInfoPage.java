package com.jlqr.controller.page;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.ProjectInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.ProjectInfoService;

public class ProjectInfoPage extends ControllerUtil {

	@NewService("ProjectInfoService")
	private ProjectInfoService projectInfoService;
	
	public void index() {
		EmployView employView = getSessionAttr("employView");
		render("projectInfoIndex.html");
	}
	
	public void projectInfoEdit(){
		ProjectInfo projectInfo = new ProjectInfo();
		List<Dictionary> dictionaryDepartment = null;
		List<Dictionary> dictionaryType = null;
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				projectInfo = projectInfoService.projectInfoById(getPara("id"));
			}
			dictionaryDepartment = projectInfoService.projectDepartment(PropKit.getInt("projectDepartment"));
			dictionaryType = projectInfoService.projectType(PropKit.getInt("projectType")); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("projectInfo", projectInfo);
		setAttr("dictionaryDepartment",dictionaryDepartment);
		setAttr("dictionaryType",dictionaryType);
		}
	
	public void projectInfoDetail(){
		ProjectInfo projectInfo = new ProjectInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				projectInfo = projectInfoService.projectInfoById(getPara("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("projectInfo", projectInfo);
	}
	
}