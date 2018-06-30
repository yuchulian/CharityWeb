package com.admin.controller.page;

import com.admin.common.ControllerUtil;
import com.admin.common.model.UserInfo;
import com.admin.interceptor.NewService;
import com.common.service.UserInfoService;

public class UserInfoPage extends ControllerUtil {
	@NewService("UserInfoService")
	UserInfoService userInfoService;
	public void index(){
		render("userInfoIndex.html");
	}
	public void userInfoDetail(){
		UserInfo userInfo = new UserInfo();
		userInfo =  userInfoService.findUserInfoById(getParaToInt("id"));
		setAttr("userInfo", userInfo);
	}
}
