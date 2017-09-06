package com.jlqr.service;

import java.util.List;

import com.jfinal.plugin.activerecord.tx.TxConfig;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.LoginInfo;

public class LoginInfoService extends ServiceUtil{
	public boolean LoginInfoSave(LoginInfo loginInfo){
		if(loginInfo!=null){
			//进行保存账号
			return loginInfo.save();
		}
		return false;
	}
	public List<LoginInfo> findLoginInfoById(int id){
		List<LoginInfo> findLoginInfo = LoginInfo.dao.find("select * from login_info where id =?",id);
		return findLoginInfo;
	}
	//保存登录信息
	public void updateLoginInfo(LoginInfo loginInfo){
		loginInfo.update();
	}
}
