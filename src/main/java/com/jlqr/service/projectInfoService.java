package com.jlqr.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.ProjectInfo;

public class projectInfoService extends ServiceUtil{

	public Page<ProjectInfo> projectInfopaginate(Controller controller) throws Exception {
		// TODO Auto-generated method stub
		return this.paginate(ProjectInfo.class,controller);
	}

	public void projectInfoSave(ProjectInfo projectInfo) throws Exception {
		// TODO Auto-generated method stub
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

	public void deleteProjectInfoById(Integer id) {
		// TODO Auto-generated method stub
		ProjectInfo.dao.deleteById(id);
	}
}
