package com.jlqr.controller.page;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Record;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.LoginInfoView;
import com.jlqr.common.model.ProjectInfo;
import com.jlqr.common.model.ReimburseInfoView;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.DictionaryService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.ProjectInfoService;
import com.jlqr.service.ReimburseInfoService;

public class ReimburseInfoPage extends ControllerUtil{
	@NewService("ReimburseInfoService")
	ReimburseInfoService reimburseInfoService;
	@NewService("DictionaryService")
	DictionaryService dictionaryService;
	@NewService("ProjectInfoService")
	ProjectInfoService projectInfoService;
	@NewService("EmployInfoService")
	EmployInfoService employInfoService;
	public void index(){
		render("reimburseInfoIndex.html");
	}
	public void reimburseInfoEdit(){
		ReimburseInfoView reimburseInfo = new ReimburseInfoView();
		List<Dictionary> reimburseBrandList = null;
		List<Dictionary> reimburseTypeList = null;
		List<ProjectInfo> projectNumberList = null;
		LoginInfoView loginInfoView = getSessionAttr("loginInfoView");
		projectNumberList = projectInfoService.findProjectInfoByProjectCollector(loginInfoView.getId());
		try {
			reimburseBrandList = dictionaryService.dictionaryByPid(PropKit.getInt("reimburse_brand"));
			reimburseTypeList = dictionaryService.dictionaryByPid(PropKit.getInt("reimburse_type"));
			if(StringUtils.isNotBlank(getPara("id"))){
				reimburseInfo =	reimburseInfoService.findReimburseInfoViewById(getParaToInt("id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setAttr("reimburseBrandList", reimburseBrandList);
		setAttr("reimburseTypeList", reimburseTypeList);
		setAttr("reimburseInfo", reimburseInfo);
		setAttr("projectNumberList", projectNumberList);
	}
	
	public void reimburseInfoDetail(){
		ReimburseInfoView reimburseInfo = new ReimburseInfoView();
		List<String> sequenceFlowList = new ArrayList<String>();
		List<LoginInfoView> loginInfoViewList = null;
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				reimburseInfo =	reimburseInfoService.findReimburseInfoViewById(getParaToInt("id"));
				
				HashMap<String, Object> activitiMap = getSessionAttr("activitiMap");
				if(null != activitiMap) {
					Record task = (Record) activitiMap.get("task");
					sequenceFlowList.add("通过");
					if(StringUtils.equals("usertask1", task.getStr("taskDefinitionKey"))) {
						LoginInfoView loginInfoView = getSessionAttr("loginInfoView");
						loginInfoViewList = employInfoService.findLeaderList(loginInfoView, reimburseInfo.getReimburseTotal());
					} else {
						sequenceFlowList.add("不通过");
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setAttr("reimburseInfo", reimburseInfo);
		setAttr("sequenceFlowList", sequenceFlowList);
		setAttr("loginInfoViewList", loginInfoViewList);
	}
	
	/**
	 * 报销统计页面
	 */
	public void costCountIndex() {
		render("costCountIndex.html");
	}
	
}
