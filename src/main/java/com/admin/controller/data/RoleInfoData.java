package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.ControllerUtil;
import com.admin.common.model.RoleInfo;
import com.admin.interceptor.NewService;
import com.common.service.PowerInfoService;
import com.common.service.RoleInfoService;

public class RoleInfoData extends ControllerUtil {

	@NewService("RoleInfoService")
	private RoleInfoService roleInfoService;
	
	@NewService("PowerInfoService")
	private PowerInfoService powerInfoService;
	
	public void roleInfoPaginate() {
		try {
			renderJson(roleInfoService.roleInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void roleInfoSave() {
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			RoleInfo roleInfo = getModel(RoleInfo.class, "roleInfo");
			roleInfoService.roleInfoSave(roleInfo);
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void roleInfoDelete() {
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			roleInfoService.deleteRoleInfoById(getParaToInt("id"));
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void roleInfoPowerSave() {
		HashMap<String,Object> returnMap = getReturnMap();
		RoleInfo roleInfo = getModel(RoleInfo.class, "roleInfo");
		try {
			roleInfoService.roleInfoSave(roleInfo);
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
		
		/**
		 * 重做
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
		renderJson(returnMsg);*/
	}
	
}
