package com.jlqr.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.LoginInfoView;
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
	
	public List<RoleInfo> roleInfoListInId(String roleId) throws Exception {
		return RoleInfo.dao.find("select * from role_info where id in (?)", roleId);
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
	
	/**
	 * 根据当前登录人角色最低等级，获取下一级角色集合
	 */
	public List<RoleInfo> roleInfoByGradePlus(LoginInfoView loginInfoView) throws Exception {
		String roleId = loginInfoView.getRoleId();
		if(StringUtils.isNotBlank(roleId) && roleId.length() > 2) {
			//获取当前登录人最高等级的角色
			RoleInfo loginRoleInfo = RoleInfo.dao.findFirst("select * from role_info where id in("+roleId.substring(1, roleId.length() - 1)+") order by role_grade asc");
			
			if(null != loginRoleInfo) {
				//获取我的上一级的角色
				return RoleInfo.dao.find("select * from role_info where role_grade >= ?", loginRoleInfo.getRoleGrade());// + 1
			}
		}
		return new ArrayList<RoleInfo>();
	}
}
