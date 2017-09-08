package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.PowerInfo;
import com.jlqr.common.model.ProjectInfo;
import com.jlqr.service.PowerInfoService;
import com.jlqr.service.projectInfoService;

public class ProjectInfoData extends ControllerUtil {
	projectInfoService projectInfoService = new projectInfoService();
	public void projectInfopaginate(){
		try {
			renderJson(projectInfoService.projectInfopaginate(this));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	public void projectInfoSave(){
		ProjectInfo projectInfo = getModel(ProjectInfo.class,"projectInfo");
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			projectInfoService.projectInfoSave(projectInfo);
			returnMsg.put("content","保存成功!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			returnMsg.put("content","保存失败");
		}
		renderJson(returnMsg);
	}
	public void projectInfoDelete() {
		HashMap returnMsg = new HashMap();
		try {
			projectInfoService.deleteProjectInfoById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
}


