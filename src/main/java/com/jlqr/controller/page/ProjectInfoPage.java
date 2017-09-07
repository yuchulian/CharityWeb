package com.jlqr.controller.page;

import com.jlqr.common.ControllerUtil;
import com.jlqr.service.PowerInfoService;

public class ProjectInfoPage extends ControllerUtil {
	
	private PowerInfoService powerInfoService = new PowerInfoService();
	
	public void index() {
		render("projectInfoIndex.html");
	}
	
}