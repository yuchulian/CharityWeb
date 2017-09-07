package com.jlqr.controller.page;

import org.apache.commons.lang.StringUtils;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.RoleInfo;
import com.jlqr.service.RoleInfoService;

public class RoleInfoPage extends ControllerUtil {
	RoleInfoService roleService = new RoleInfoService();
	
	public void index() {
		render("RoleIndex.html");
	}
	
	public void RoleEdit() {
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
}
