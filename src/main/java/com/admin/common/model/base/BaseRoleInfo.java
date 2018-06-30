package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRoleInfo<M extends BaseRoleInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setRoleName(java.lang.String roleName) {
		set("role_name", roleName);
		return (M)this;
	}

	public java.lang.String getRoleName() {
		return getStr("role_name");
	}

	public M setRoleRemark(java.lang.String roleRemark) {
		set("role_remark", roleRemark);
		return (M)this;
	}

	public java.lang.String getRoleRemark() {
		return getStr("role_remark");
	}

	public M setPowerPath(java.lang.String powerPath) {
		set("power_path", powerPath);
		return (M)this;
	}

	public java.lang.String getPowerPath() {
		return getStr("power_path");
	}

	public M setRoleGrade(java.lang.Integer roleGrade) {
		set("role_grade", roleGrade);
		return (M)this;
	}

	public java.lang.Integer getRoleGrade() {
		return getInt("role_grade");
	}

	public M setIsAdmin(java.lang.Integer isAdmin) {
		set("is_admin", isAdmin);
		return (M)this;
	}

	public java.lang.Integer getIsAdmin() {
		return getInt("is_admin");
	}

}