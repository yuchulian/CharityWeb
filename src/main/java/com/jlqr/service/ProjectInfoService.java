package com.jlqr.service;

import java.util.Date;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.ProjectInfo;

public class ProjectInfoService extends ServiceUtil{

	public Page<ProjectInfo> projectInfopaginate(Controller controller) throws Exception {
		return this.paginate(ProjectInfo.class,controller);
	}

	public void projectInfoSave(ProjectInfo projectInfo) throws Exception {
		//进行设置更新的时间
		projectInfo.setProjectUpdateTime(new Date());
		if(projectInfo.getId()==null){
			Integer id = getMaxColumn(ProjectInfo.class, "id")+1;
			projectInfo.setId(id);
			projectInfo.setProjectCreateTime(new Date());
			projectInfo.save();
		}else{
			projectInfo.update();
		}
	}

	public void deleteProjectInfoById(String id) {
		ProjectInfo.dao.deleteById(id);
	}
	
	public ProjectInfo projectInfoById(String id) throws Exception {
		return ProjectInfo.dao.findById(id);
	}
	
}
