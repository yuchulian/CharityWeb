package com.jlqr.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.LoginInfoView;
import com.jlqr.common.model.ReimburseInfo;
import com.jlqr.common.model.ReimburseInfoView;

public class ReimburseInfoService extends ServiceUtil{

	public Page<ReimburseInfoView> reimburseInfopaginate(Controller controller) throws Exception {
		// TODO Auto-generated method stub
		return this.paginate(ReimburseInfoView.class, controller);
	}

	public ReimburseInfoView findReimburseInfoViewById(Integer id) {
		// TODO Auto-generated method stub
		return ReimburseInfoView.dao.findById(id);
	}

	public void reimburseInfoSave(ReimburseInfo reimburseInfo,Controller controller) throws Exception {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(reimburseInfo.getStr("id"))){
			int id = getMaxColumn(ReimburseInfo.class, "id")+1;
			reimburseInfo.setId(id);
			LoginInfoView loginInfoView = controller.getSessionAttr("loginInfoView");
			reimburseInfo.setEmployId(loginInfoView.getId());
			reimburseInfo.setReimburseTime(new Date());
			reimburseInfo.setReimburseState(1);
			reimburseInfo.save();
		}else{
			reimburseInfo.update();
		}
	}

	public void reimburseInfoDeleteById(int id) {
		// TODO Auto-generated method stub
		ReimburseInfo.dao.deleteById(id);
	}

	public List<Record> costCount(Controller controller) throws Exception{
		String sqlExceptSelect1 = "select dictionary.dictionary_name project_department_name, reimburse_info.reimburse_number, reimburse_info.employ_id, employ_info.employ_name, sum(reimburse_total) as cost_count from "
				+ "reimburse_info left join project_info on(project_info.project_number = reimburse_info.reimburse_number) "
				+ "left join dictionary on(dictionary.dictionary_pid = 2 and dictionary.id = project_info.project_department) "
				+ "left join employ_info on(employ_info.id = reimburse_info.employ_id) "
				+ "where 1=1",
				sqlExceptSelect2 = "group by reimburse_number, employ_id";
		return this.list(controller, sqlExceptSelect1, sqlExceptSelect2);
	}


}
