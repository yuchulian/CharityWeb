package com.common.service;

import java.util.ArrayList;
import java.util.List;

import com.admin.common.ServiceUtil;
import com.admin.common.model.PowerInfo;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;

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
	
	public List<PowerInfo> findPowerInfoListInId(String powerId) throws Exception {
		return PowerInfo.dao.find("select * from power_info where id in("+powerId+") order by id asc");
	}
	
	public void powerInfoSave(PowerInfo powerInfo) throws Exception {
		if(null != powerInfo) {
			PowerInfo powerInfoParent = this.findPowerInfoById(powerInfo.getPowerPid());
			powerInfo.setPowerName(powerInfo.getPowerName().replaceAll("\\,", ""));
			if(null == powerInfo.getId()) {
				PowerInfo powerInfoMax = powerInfo.dao.findFirst("select max(id) as id from power_info");

				//自增长id
				powerInfo.setId(getMaxColumn(PowerInfo.class, "id")+1);

				//组装path和pathname
				if(null != powerInfoParent) {
					powerInfo.setPowerIdPath(powerInfoParent.getPowerIdPath()+powerInfo.getId()+",");
					powerInfo.setPowerNamePath(powerInfoParent.getPowerNamePath()+powerInfo.getPowerName()+",");
				} else {
					powerInfo.setPowerIdPath(","+powerInfo.getId()+",");
					powerInfo.setPowerNamePath(","+powerInfo.getPowerName()+",");
				}

				powerInfo.save();
			} else {
				//组装path和pathname
				if(null != powerInfoParent) {
					powerInfo.setPowerNamePath(powerInfoParent.getPowerNamePath()+powerInfo.getPowerName()+",");
				} else {
					powerInfo.setPowerNamePath(","+powerInfo.getPowerName()+",");
				}

				//修改该pathname的子类
				PowerInfo powerInfoOld = this.findPowerInfoById(powerInfo.getId());
				List<PowerInfo> powerInfoList = powerInfo.dao.find("select * from power_info where power_id_path like '"+powerInfoOld.getPowerIdPath()+"%' and power_id_path <> '"+powerInfoOld.getPowerIdPath()+"'");
				for (PowerInfo _powerInfo : powerInfoList) {
					_powerInfo.setPowerNamePath(_powerInfo.getPowerNamePath().replace(powerInfoOld.getPowerNamePath(), powerInfo.getPowerNamePath()));
					_powerInfo.update();
				}

				powerInfo.update();
			}
		}
	}
	
	public void deletePowerInfoById(Integer powerInfoId) throws Exception {
		PowerInfo powerInfo = this.findPowerInfoById(powerInfoId);
		if(null != powerInfo) {
			Db.update("delete from power_info where power_id_path like '"+powerInfo.getPowerIdPath()+"%'");
		}
	}

	/**
	 * 角色管理用到
	 * @param state
	 * @return
	 */
	public List<PowerInfo> findAllByStateAndIds(int state, String ids, String ...val){
		String value = "*";
		if(val.length>0){
			value = val[0];
		}
		
		if( ids != null && !"".equals(ids.trim())) {
			return PowerInfo.dao.find("select " + value + " from power_info where power_state = ? and id in(" + ids + ")", state);
		}
		return new ArrayList<>();
	}

}
