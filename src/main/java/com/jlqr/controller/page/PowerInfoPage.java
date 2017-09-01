package com.jlqr.controller.page;

import org.apache.commons.lang.StringUtils;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.PowerInfo;
import com.jlqr.service.PowerInfoService;

public class PowerInfoPage extends ControllerUtil {
	
	private PowerInfoService powerInfoService = new PowerInfoService();
	
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