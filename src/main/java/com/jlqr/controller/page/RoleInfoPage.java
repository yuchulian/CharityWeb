package com.jlqr.controller.page;

import org.apache.commons.lang.StringUtils;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.RoleInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.RoleInfoService;

public class RoleInfoPage extends ControllerUtil {
	
	@NewService("RoleInfoService")
	private RoleInfoService roleService;
	
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
}
