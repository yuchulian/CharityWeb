package com.jlqr.service;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;
import com.jlqr.common.FileUtil;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.LoginInfo;

public class LoginInfoService extends ServiceUtil{
	
	public void loginInfoSave(LoginInfo loginInfo, Integer id) throws Exception{
		if(StringUtils.defaultString(loginInfo.getLoginImg(), "").indexOf("/temp/") > -1) {
			loginInfo.setLoginImg(FileUtil.cut(loginInfo.getLoginImg(), PropKit.get("img")));
		}
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
	
	public LoginInfo loginInfoByNameAndPwd(String loginName, String loginPwd) throws Exception {
		return LoginInfo.dao.findFirst("select * from login_info where login_name = ? and login_pwd = ?", loginName, DigestUtils.md5Hex(loginPwd));
	}

	/*public LoginInfo findLoginInfoByIdAndPassword(Integer id, String oldPassword) {		
		return LoginInfo.dao.findFirst("select * from login_info where login_pwd='"+DigestUtils.md5Hex(oldPassword)+"' and id ="+id);
	}

	public void resetPwdUpdate(LoginInfo loginInfo) {
		loginInfo.update();
	}*/
	
	//保存登录信息
//	public void updateLoginInfo(LoginInfo loginInfo){
//		loginInfo.update();
//	}
}
