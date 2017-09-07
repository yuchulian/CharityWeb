package com.jlqr.service;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.RoleInfo;

public class RoleInfoService extends ServiceUtil {
	public int i=0;
	
	public RoleInfo findRoleById(Integer id) throws Exception {
		return RoleInfo.dao.findById(id);
	}
	
	public Page<RoleInfo> roleInfoPaginate(Controller controller) throws Exception {
		return this.paginate(RoleInfo.class, controller);
	}
	
	public void roleInfoSave(RoleInfo roleInfo) throws Exception {
		if(null == roleInfo.getId()) {
			int id = getMaxColumn(RoleInfo.class, "id") + 1;
			if(id==1){
				id = id+1;
			}
			roleInfo.setId(id);
			roleInfo.save();
		} else {
			roleInfo.update();
		}
	}
}
