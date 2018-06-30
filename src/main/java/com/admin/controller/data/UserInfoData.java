package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.ControllerUtil;
import com.admin.interceptor.NewService;
import com.common.service.UserInfoService;

public class UserInfoData extends ControllerUtil {
	@NewService("UserInfoService")
	UserInfoService userInfoService;
	
	public void userInfoPaginate(){
		try {
			renderJson(userInfoService.userInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void userInfoChange(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			userInfoService.userInfoChange(getParaToInt("id"));
			returnMsg.put("returnMsg", "操作成功");
		} catch (Exception e) {
			returnMsg.put("returnMsg", "操作失败");
			e.printStackTrace();
		}
		renderJson(returnMsg);
	}
}
