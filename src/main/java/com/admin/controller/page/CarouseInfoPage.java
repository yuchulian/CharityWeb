package com.admin.controller.page;

import org.apache.commons.lang.StringUtils;

import com.admin.common.ControllerUtil;
import com.admin.common.model.CarouseInfo;
import com.admin.common.model.PowerInfo;
import com.admin.interceptor.NewService;
import com.common.service.CarouseInfoService;

public class CarouseInfoPage extends ControllerUtil {
	@NewService("CarouseInfoService")
	CarouseInfoService carouseInfoService;
	public void index(){
		render("carouseInfoIndex.html");
	}
	public void carouseInfoEdit(){
		CarouseInfo carouseInfo = new CarouseInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				carouseInfo = carouseInfoService.findCarouseInfoById(Integer.parseInt(getPara("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("carouseInfo", carouseInfo);
	}
	
}
