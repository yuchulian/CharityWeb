package com.common.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.admin.common.FileUtil;
import com.admin.common.ServiceUtil;
import com.admin.common.model.UserInfo;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class UserInfoService extends ServiceUtil {

	public UserInfo findUserInfoByNickName(String nickName) {
		return UserInfo.dao.findFirst("select * from user_info where user_nickname=?",nickName);
	}

	public void userInfoSave(UserInfo userInfo) throws Exception {
		if(StringUtils.defaultString(userInfo.getUserImg(), "").indexOf("/temp/") > -1) {
			userInfo.setUserImg(FileUtil.cut(userInfo.getUserImg(), PropKit.get("img")));
		}
		if(userInfo.getId()==null){
			int id = getMaxColumn(UserInfo.class,"id")+1;
			userInfo.setId(id);
			userInfo.setUserPassword(DigestUtils.md5Hex(userInfo.getUserPassword()));
			userInfo.setUserRegisterTime(new Date());
			userInfo.setUserIsRegister(0);
			//默认注册赠送100积分
			userInfo.setUserScore(100);
			userInfo.save();
		}else{
			userInfo.update();
		}
	}

	public UserInfo findUserInfobyCode(String code) {
		UserInfo userInfo = new UserInfo();
		if(StringUtils.isNotBlank(code)){
			userInfo = UserInfo.dao.findFirst("select * from user_info where user_code = ?",code);
		}
		return userInfo;
	}

	public void activeUser(UserInfo exitUser) {
		//进行激活用户
		if(exitUser!=null){
			exitUser.setUserIsRegister(1);
			exitUser.setIsAble(0);
			exitUser.update();
		}
		
	}

	public UserInfo loginByNickNameAndPassword(String userNickname, String userPassword) {
		System.out.println(DigestUtils.md5Hex(StringUtils.defaultString(userPassword, "")));
		return UserInfo.dao.findFirst("select * from user_info where user_nickname= ? and user_password= ?",StringUtils.defaultString(userNickname, ""),DigestUtils.md5Hex(StringUtils.defaultString(userPassword, "")));	 
	}

	public Page<UserInfo> userInfoPaginate(Controller controller) throws Exception {
		return this.paginate(UserInfo.class, controller);
	}

	public void userInfoChange(Integer id) {
		UserInfo userInfo = new UserInfo();
		userInfo = UserInfo.dao.findById(id);
		if(userInfo!=null){
			if(userInfo.getIsAble()==1){
				userInfo.setIsAble(0);
			}else{
				userInfo.setIsAble(1);
			}
			userInfo.update();
		}
	}
	public UserInfo findUserInfoById(Integer id){
		return UserInfo.dao.findById(id);
	}
	//这个方法进行校验密码
	public static boolean checkPassword(String userPassword, String oldPassword) {
		return 	DigestUtils.md5Hex(oldPassword).equals(userPassword);
	}

	public static UserInfo findUserInfoByIdAndPassword(Integer id, String oldPassword) {
		 return  UserInfo.dao.findFirst("select * from user_info where id = ? and user_password = ?",id,oldPassword);
	}

	public List<UserInfo> findUserInfobyIdIn(String idStr) {
		// TODO Auto-generated method stub
		return UserInfo.dao.find("select * from user_info where id in ("+idStr+") limit 0,9");
	}

}
