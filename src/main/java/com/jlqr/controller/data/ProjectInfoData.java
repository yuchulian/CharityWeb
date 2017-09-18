package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.ProjectInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.ProjectInfoService;

public class ProjectInfoData extends ControllerUtil {

	@NewService("ProjectInfoService")
	ProjectInfoService projectInfoService;
	
	public void projectInfopaginate(){
		try {
			renderJson(projectInfoService.projectInfopaginate(this));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	public void projectInfoSave(){
		ProjectInfo projectInfo = getModel(ProjectInfo.class,"projectInfo");
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			EmployView employView = getSessionAttr("employView");
			projectInfoService.projectInfoSave(projectInfo, employView);
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	public void projectInfoDelete() {
		HashMap returnMsg = new HashMap();
		try {
			projectInfoService.deleteProjectInfoById(getPara("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
}


