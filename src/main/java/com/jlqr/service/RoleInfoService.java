package com.jlqr.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.PowerInfo;
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

	public void deleteRoleInfoById(Integer roleInfoId) throws Exception {
		RoleInfo.dao.deleteById(roleInfoId);
	}

	public <T> RoleInfo getPodwerbyroleid(T id){
		RoleInfo findById = RoleInfo.dao.findById(id);
		return findById;
	}

	
	/**
	 * 根据登录信息判断该用户权限信息的ids;
	 * @param loginInfo 登录信息
	 * @param userRole 角色信息
	 * @return
	 */
	public String getPowerInfoIdsByUserRole(LoginInfo loginInfo,RoleInfo userRole){
		if(loginInfo != null ){
			if(loginInfo.getId() == 1){
				List<PowerInfo> find = PowerInfo.dao.find("select id from power_info");
				List<Integer> integers = new ArrayList<>(); 
				for (PowerInfo powerInfo : find) {
					integers.add(powerInfo.getId());
				}
				return StringUtils.join(integers, ",");
			} else {
				if(null != userRole){
					RoleInfo info = (RoleInfo) userRole;
					String powerPath = info.getPowerPath();
					if(powerPath.split(",").length > 1){
						String ids = powerPath.substring(1, powerPath.length()-1);
						return ids;
					}
				}
			}
		}

		return null;
	}
}
