package com.jlqr.service;

import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.LoginInfo;

public class LoginInfoService extends ServiceUtil{
	public boolean LoginInfoSave(LoginInfo loginInfo){
		if(loginInfo!=null){
			//设置id
			try {
				loginInfo.setId(getMaxColumn(LoginInfo.class, "id")+1);
				return loginInfo.save();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
}
