package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.SystemUtil;
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
			
			//判断是否有权限修改所选择的权限
			boolean stringAContainB = SystemUtil.stringAContainB(powerInfoIds, roleInfo.getPowerPath(), ",");
			if(!stringAContainB) {
				returnMsg.put("content", "保存失败,不能越权修改");
				renderJson(returnMsg);
				return;
			}
			
			roleService.roleInfoSave(roleInfo);
			returnMsg.put("content", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "保存失败");
		}
		renderJson(returnMsg);
	}
	
}
