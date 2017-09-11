package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.RoleInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.RoleInfoService;

public class RoleInfoData extends ControllerUtil {

	@NewService("RoleInfoService")
	private RoleInfoService roleService;
	
	public void roleInfoPaginate() {
		try {
			renderJson(roleService.roleInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void roleInfoSave() {
		
		HashMap returnMsg = new HashMap();
		try {
			RoleInfo roleInfo = getModel(RoleInfo.class, "roleInfo");
			roleService.roleInfoSave(roleInfo);
			returnMsg.put("content", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "保存失败");
		}
		renderJson(returnMsg);
		
	}
	
	public void roleInfoDelete() {
		HashMap returnMsg = new HashMap();
		try {
			roleService.deleteRoleInfoById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
	
	public void roleInfoPowerSave() {
		HashMap returnMsg = new HashMap();
		try {
			//获取拥有权限的ip
			String powerInfoIds = roleService.getPowerInfoIdsByUserRole((LoginInfo)getSessionAttr("loginInfo"),(RoleInfo)getSessionAttr("roleInfo"));
			RoleInfo roleInfo = getModel(RoleInfo.class, "roleInfo");
			
			roleService.roleInfoSave(roleInfo);
			returnMsg.put("content", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "保存失败");
		}
		renderJson(returnMsg);
	}
	
}
