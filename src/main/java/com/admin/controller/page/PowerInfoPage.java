package com.admin.controller.page;

import org.apache.commons.lang.StringUtils;

import com.admin.common.ControllerUtil;
import com.admin.common.model.PowerInfo;
import com.admin.interceptor.NewService;
import com.common.service.PowerInfoService;

public class PowerInfoPage extends ControllerUtil {

	@NewService("PowerInfoService")
	private PowerInfoService powerInfoService;
	
	public void index() {
		render("powerInfoIndex.html");
	}
	
	public void powerInfoEdit() {
		PowerInfo powerInfo = new PowerInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				powerInfo = powerInfoService.findPowerInfoById(Integer.parseInt(getPara("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("powerInfo", powerInfo);
	}
	
}