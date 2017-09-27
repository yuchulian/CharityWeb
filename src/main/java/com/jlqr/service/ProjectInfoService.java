package com.jlqr.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ChineseCharToEnUtil;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.ProjectInfo;
import com.jlqr.common.model.ProjectInfoView;

public class ProjectInfoService extends ServiceUtil{

	public Page<ProjectInfoView> projectInfopaginate(Controller controller) throws Exception {
		return this.paginate(ProjectInfoView.class,controller);
	}

	public void projectInfoSave(ProjectInfo projectInfo, EmployView employView) throws Exception {
		//进行设置更新的时间
		String projectNumber = null;
		projectInfo.setProjectUpdateTime(new Date());
		if(projectInfo.getId()==null){
			Integer id = getMaxColumn(ProjectInfo.class, "id")+1;
			projectInfo.setId(id);
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			String dateString = format.format(date);
			StringBuffer sb = new StringBuffer();
			sb.append(PropKit.get("cityFristChar"));
			sb.append(dateString);
			sb.append(id);
			sb.append(ChineseCharToEnUtil.getAllFirstLetter(employView.getEmployName()).toUpperCase());
			projectNumber = sb.toString();
			projectInfo.setProjectNumber(projectNumber);
			projectInfo.setProjectCreateTime(new Date());
			projectInfo.setProjectCollector(employView.getId());
			projectInfo.setProjectState(1);
			projectInfo.save();
		}else{
			projectInfo.update();
		}
	}

	public void deleteProjectInfoById(String id) throws Exception{
		ProjectInfo.dao.deleteById(id);
	}
	
	public ProjectInfo projectInfoById(String id) throws Exception {
		return ProjectInfo.dao.findById(id);
	}

	public ProjectInfoView projectInfoViewById(Integer id) throws Exception {
		return ProjectInfoView.dao.findById(id);
	}

	public List<ProjectInfo> findProjectInfoByProjectCollector(Integer id) {
		// TODO Auto-generated method stub
		return ProjectInfo.dao.find("select * from project_info where project_collector=?",id);
	}

}
