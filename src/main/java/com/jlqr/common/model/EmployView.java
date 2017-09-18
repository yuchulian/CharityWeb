package com.jlqr.common.model;

import com.jfinal.plugin.activerecord.Model;
@SuppressWarnings("serial")
public class EmployView extends Model<EmployView>{
	public static final EmployView dao = new EmployView();
	
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
