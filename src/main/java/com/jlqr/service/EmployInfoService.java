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
		/*if(null != employInfo) {
			EmployInfo employInfoParent = this.findEmployInfoById(employInfo.getPowerPid());
			employInfo.setPowerName(employInfo.getPowerName().replaceAll("\\,", ""));
			if(null == employInfo.getId()) {
				EmployInfo employInfoMax = employInfo.dao.findFirst("select max(id) as id from power_info");
				
				//自增长id
				employInfo.setId(getMaxColumn(EmployInfo.class, "id")+1);
				
				//组装path和pathname
				if(null != employInfoParent) {
					employInfo.setPowerIdPath(employInfoParent.getPowerIdPath()+employInfo.getId()+",");
					employInfo.setPowerNamePath(employInfoParent.getPowerNamePath()+employInfo.getPowerName()+",");
				} else {
					employInfo.setPowerIdPath(","+employInfo.getId()+",");
					employInfo.setPowerNamePath(","+employInfo.getPowerName()+",");
				}
				
				employInfo.save();
			} else {
				//组装path和pathname
				if(null != employInfoParent) {
					employInfo.setPowerNamePath(employInfoParent.getPowerNamePath()+employInfo.getPowerName()+",");
				} else {
					employInfo.setPowerNamePath(","+employInfo.getPowerName()+",");
				}
				
				//修改该pathname的子类
				EmployInfo employInfoOld = this.findEmployInfoById(employInfo.getId());
				List<EmployInfo> employInfoList = employInfo.dao.find("select * from power_info where power_id_path like '"+employInfoOld.getPowerIdPath()+"%' and power_id_path <> '"+employInfoOld.getPowerIdPath()+"'");
				for (EmployInfo _employInfo : employInfoList) {
					_employInfo.setPowerNamePath(_employInfo.getPowerNamePath().replace(employInfoOld.getPowerNamePath(), employInfo.getPowerNamePath()));
					_employInfo.update();
				}
				
				employInfo.update();
			}
		}*/
	}
	public void deleteEmployInfoById(Integer employInfoId) throws Exception {
		/*EmployInfo employInfo = this.findEmployInfoById(employInfoId);
		if(null != employInfo) {
			Db.update("delete from power_info where power_id_path like '"+employInfo.getPowerIdPath()+"%'");
		}*/
	}
	

	
}
