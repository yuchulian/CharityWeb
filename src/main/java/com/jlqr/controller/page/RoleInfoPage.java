package com.jlqr.controller.page;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.JsonKit;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.PowerInfo;
import com.jlqr.common.model.RoleInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.PowerInfoService;
import com.jlqr.service.RoleInfoService;

public class RoleInfoPage extends ControllerUtil {
	
	@NewService("RoleInfoService")
	private RoleInfoService roleService;
	@NewService("PowerInfoService")
	private PowerInfoService powerInfoService;
	
	public void index() {
		render("roleInfoIndex.html");
	}
	
	public void roleInfoEdit() {
		RoleInfo roleInfo = new RoleInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				roleInfo = roleService.findRoleById(Integer.parseInt(getPara("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("roleInfo", roleInfo);
	}
	
	public void roleInfoPower() {
		RoleInfo roleInfo = new RoleInfo();
		try {
			//获取拥有ip的权限
			String powerInfoIds = roleService.getPowerInfoIdsByUserRole((LoginInfo)getSessionAttr("loginInfo"),(RoleInfo)getSessionAttr("roleInfo"));
			List<PowerInfo> powers = powerInfoService.findAllByStateAndIds(1, powerInfoIds, "id,power_pid pid,power_name name");
			setAttr("powers", JsonKit.toJson(powers));

			if(StringUtils.isNotBlank(getPara("id"))) {
				roleInfo = roleService.findRoleById(Integer.parseInt(getPara("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("roleInfo", roleInfo);
		setAttr("roleInfo", roleInfo);
		render("roleInfoPower.html");
	}
}
