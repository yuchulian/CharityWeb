package com.jlqr.common.model;

import com.jfinal.plugin.activerecord.Model;
@SuppressWarnings("serial")
public class LoginInfoView extends Model<LoginInfoView>{
	public static final LoginInfoView dao = new LoginInfoView();
	
	public Integer getId() {
		return getInt("id");
	}
	
	public String getEmployName() {
		return get("employ_name");
	}
	
	public String getRoleId() {
		return get("role_id");
	}
	
	public String getDepartmentId() {
		return get("department_id");
	}
	
}
