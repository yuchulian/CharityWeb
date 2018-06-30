package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseWorkInfo<M extends BaseWorkInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setCompanyName(java.lang.String companyName) {
		set("company_name", companyName);
		return (M)this;
	}

	public java.lang.String getCompanyName() {
		return getStr("company_name");
	}

	public M setPositionName(java.lang.String positionName) {
		set("position_name", positionName);
		return (M)this;
	}

	public java.lang.String getPositionName() {
		return getStr("position_name");
	}

	public M setWages(java.lang.Integer wages) {
		set("wages", wages);
		return (M)this;
	}

	public java.lang.Integer getWages() {
		return getInt("wages");
	}

	public M setWorkStart(java.util.Date workStart) {
		set("work_start", workStart);
		return (M)this;
	}

	public java.util.Date getWorkStart() {
		return get("work_start");
	}

	public M setWorkEnd(java.util.Date workEnd) {
		set("work_end", workEnd);
		return (M)this;
	}

	public java.util.Date getWorkEnd() {
		return get("work_end");
	}

	public M setWorkContent(java.lang.String workContent) {
		set("work_content", workContent);
		return (M)this;
	}

	public java.lang.String getWorkContent() {
		return getStr("work_content");
	}

	public M setMangerId(java.lang.Integer mangerId) {
		set("manger_id", mangerId);
		return (M)this;
	}

	public java.lang.Integer getMangerId() {
		return getInt("manger_id");
	}

}