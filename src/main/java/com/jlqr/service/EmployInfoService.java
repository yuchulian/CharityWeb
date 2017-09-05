package com.jlqr.service;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.EmployInfo;

public class EmployInfoService extends ServiceUtil {
	public Page<EmployInfo> employInfoPaginate(Controller controller) throws Exception {
		return this.paginate(EmployInfo.class, controller);
	}
	public List<EmployInfo> employInfoList(Controller controller) throws Exception {
		return this.list(EmployInfo.class, controller);
	}
	public EmployInfo findEmployInfoById(Integer id) throws Exception {
		return EmployInfo.dao.findById(id);
	}
	public List<EmployInfo> findEmployInfoListByPId(Integer power_pid) throws Exception {
		return EmployInfo.dao.find("select * from power_info where power_pid = "+power_pid);
	}
	public void employInfoSave(EmployInfo employInfo) throws Exception {
		if(null == employInfo.getId()) {
			employInfo.setId(getMaxColumn(EmployInfo.class, "id") + 1);
			employInfo.save();
		} else {
			employInfo.update();
		}
	}
	public void deleteEmployInfoById(Integer employInfoId) throws Exception {
		EmployInfo.dao.deleteById(employInfoId);
		/*EmployInfo employInfo = this.findEmployInfoById(employInfoId);
		if(null != employInfo) {
			Db.update("delete from power_info where power_id_path like '"+employInfo.getPowerIdPath()+"%'");
		}*/
	}
	

	
}
