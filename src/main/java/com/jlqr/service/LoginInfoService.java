package com.jlqr.service;

import java.util.Date;

import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.LoginInfo;

public class LoginInfoService extends ServiceUtil{
	
	public void LoginInfoSave(LoginInfo loginInfo, Integer id) throws Exception{
		if(null != id) {
			loginInfo.setId(id);
			loginInfo.setCreateTime(new Date());
			loginInfo.save();
		} else {
			loginInfo.update();
		}
	}
	
	public LoginInfo findLoginInfoById(Integer id) throws Exception{
		return LoginInfo.dao.findById(id);
	}
	
	//保存登录信息
//	public void updateLoginInfo(LoginInfo loginInfo){
//		loginInfo.update();
//	}
}
