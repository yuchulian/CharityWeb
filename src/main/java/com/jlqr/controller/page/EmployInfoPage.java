package com.jlqr.controller.page;

import org.apache.commons.lang.StringUtils;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.service.EmployInfoService;

public class EmployInfoPage extends ControllerUtil {
	
	private EmployInfoService employInfoService = new EmployInfoService();
	
	public void index() {
		render("employInfoIndex.html");
	}
	
	public void employInfoEdit() {
		EmployInfo employInfo = new EmployInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				employInfo = employInfoService.findEmployInfoById(Integer.parseInt(getPara("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("employInfo", employInfo);
	}
	
}