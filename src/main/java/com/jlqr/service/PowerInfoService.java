package com.jlqr.service;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.PowerInfo;

public class PowerInfoService extends ServiceUtil {
	public Page<PowerInfo> powerInfoPaginate(Controller controller) throws Exception {
		return this.paginate(PowerInfo.class, controller);
	}
	public List<PowerInfo> powerInfoList(Controller controller) throws Exception {
		return this.list(PowerInfo.class, controller);
	}
	public PowerInfo findPowerInfoById(Integer id) throws Exception {
		return PowerInfo.dao.findById(id);
	}
	public List<PowerInfo> findPowerInfoListByPId(Integer power_pid) throws Exception {
		return PowerInfo.dao.find("select * from power_info where power_pid = "+power_pid);
	}
	public void powerInfoSave(PowerInfo PowerInfo) throws Exception {
		if(null != PowerInfo) {
			PowerInfo PowerInfoParent = this.findPowerInfoById(PowerInfo.getInt("power_pid"));//PowerInfo.dao.findById(PowerInfo.getInt("power_pid"));
			if(null == PowerInfo.getInt("id")) {
				PowerInfo PowerInfoMax = PowerInfo.dao.findFirst("select max(id) as id from power_info");
				
				//自增长id
				PowerInfo.set("id", getMaxColumn(PowerInfo.getClass(), "id")+1);
				
				//组装path和pathname
				PowerInfo.set("power_name", PowerInfo.getStr("power_name").replaceAll("\\,", ""));
				if(null != PowerInfoParent) {
					PowerInfo.set("power_id_path", PowerInfoParent.getStr("power_id_path")+PowerInfo.getInt("id")+",");
					PowerInfo.set("power_name_path", PowerInfoParent.getStr("power_name_path")+PowerInfo.getStr("power_name")+",");
				} else {
					PowerInfo.set("power_id_path", ","+PowerInfo.getInt("id")+",");
					PowerInfo.set("power_name_path", ","+PowerInfo.getStr("power_name")+",");
				}
				
				PowerInfo.save();
			} else {
				//组装path和pathname
				PowerInfo.set("power_name", PowerInfo.getStr("power_name").replaceAll("\\,", ""));
				if(null != PowerInfoParent) {
					PowerInfo.set("power_name_path", PowerInfoParent.getStr("power_name_path")+PowerInfo.getStr("power_name")+",");
				} else {
					PowerInfo.set("power_name_path", ","+PowerInfo.getStr("power_name")+",");
				}
				
				//修改该pathname的子类
				PowerInfo powerInfoOld = this.findPowerInfoById(PowerInfo.getInt("id"));
				List<PowerInfo> powerInfoList = PowerInfo.dao.find("select * from power_info where power_id_path like '"+powerInfoOld.getStr("power_id_path")+"%' and power_id_path <> '"+powerInfoOld.getStr("power_id_path")+"'");
				for (PowerInfo powerInfo : powerInfoList) {
					powerInfo.set("power_name_path", powerInfo.getStr("power_name_path").replace(powerInfoOld.getStr("power_name_path"), PowerInfo.getStr("power_name_path")));
					powerInfo.update();
				}
				
				PowerInfo.update();
			}
		}
	}
	public void deletePowerInfoById(Integer powerInfoId) throws Exception {
		PowerInfo powerInfo = this.findPowerInfoById(powerInfoId);
		if(null != powerInfo) {
			Db.update("delete from power_info where power_id_path like '"+powerInfo.getStr("power_id_path")+"%'");
		}
		/*if(null != powerInfo) {
			List<PowerInfo> powerInfoList = PowerInfo.dao.find("select * from power_info where power_id_path like '"+powerInfo.getStr("power_id_path")+"%'");
			if(powerInfoList.size() > 0) {
				Integer[] deletePowerInfoIds = new Integer[powerInfoList.size()];
				for (int i=0; i<powerInfoList.size(); i++) {
					deletePowerInfoIds[i] = powerInfoList.get(i).getInt("id");
				}
				Db.update("delete from power_info where id in("+StringUtils.join(deletePowerInfoIds, ",")+")");
			}
		}*/
	}
	

	
}
