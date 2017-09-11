package com.jlqr.controller.page;

import org.apache.commons.lang.StringUtils;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.ProjectInfo;
import com.jlqr.service.PowerInfoService;

public class ProjectInfoPage extends ControllerUtil {
	
	private PowerInfoService powerInfoService = new PowerInfoService();
	
	public void index() {
		render("projectInfoIndex.html");
	}
	public void ProjectInfoEdit(){
		ProjectInfo projectInfo = new ProjectInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				projectInfo = powerInfoService.findEmployInfoById(Integer.parseInt(getPara("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("projectInfo", projectInfo);
	}
	
	public void projectTask() {
		render("projectTaskIndex.html");
	}
	
}