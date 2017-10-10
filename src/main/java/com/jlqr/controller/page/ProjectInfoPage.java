package com.jlqr.controller.page;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.ProjectInfoView;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.DictionaryService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.ProjectInfoService;

public class ProjectInfoPage extends ControllerUtil {

	@NewService("ProjectInfoService")
	private ProjectInfoService projectInfoService;
	
	@NewService("DictionaryService")
	private DictionaryService dictionaryService;
	
	@NewService("EmployInfoService")
	private EmployInfoService employInfoService;
	
	public void index() {
		EmployView employView = getSessionAttr("employView");
		render("projectInfoIndex.html");
	}
	
	public void projectInfoEdit(){
		ProjectInfoView projectInfoView = new ProjectInfoView();
		List<Dictionary> dictionaryDepartment = null;
		List<Dictionary> dictionaryType = null;
		List<Dictionary> dictionaryUnit = null;
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				projectInfoView = projectInfoService.projectInfoViewById(getParaToInt("id"));
			}
			dictionaryDepartment = dictionaryService.dictionaryByPid(PropKit.getInt("projectDepartment"));
			dictionaryType = dictionaryService.dictionaryByPid(PropKit.getInt("projectType"));
			dictionaryUnit = dictionaryService.dictionaryByPid(PropKit.getInt("projectUnit"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("projectInfo", projectInfoView);
		setAttr("dictionaryDepartment",dictionaryDepartment);
		setAttr("dictionaryType",dictionaryType);
		setAttr("dictionaryUnit", dictionaryUnit);
	}
	
	public void projectInfoDetail(){
		ProjectInfoView projectInfoView = null;
		List<EmployView> employViewList = null;
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				projectInfoView = projectInfoService.projectInfoViewById(getParaToInt("id"));
				
				//获取当前登录人的领导
				HashMap<String, Object> activitiMap = getSessionAttr("activitiMap");
				if(null != activitiMap) {
					EmployView employView = getSessionAttr("employView");
					employViewList = employInfoService.findLeaderList(employView);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("projectInfo", projectInfoView);
		setAttr("employViewList", employViewList);
	}
	
}