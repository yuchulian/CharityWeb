package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseActivityUnit<M extends BaseActivityUnit<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setUnitName(java.lang.String unitName) {
		set("unit_name", unitName);
		return (M)this;
	}

	public java.lang.String getUnitName() {
		return getStr("unit_name");
	}

	public M setUnitPostNumber(java.lang.Integer unitPostNumber) {
		set("unit_post_number", unitPostNumber);
		return (M)this;
	}

	public java.lang.Integer getUnitPostNumber() {
		return getInt("unit_post_number");
	}

	public M setUnitPostSuccessNumber(java.lang.Integer unitPostSuccessNumber) {
		set("unit_post_success_number", unitPostSuccessNumber);
		return (M)this;
	}

	public java.lang.Integer getUnitPostSuccessNumber() {
		return getInt("unit_post_success_number");
	}

	public M setUnitPostFailNumber(java.lang.Integer unitPostFailNumber) {
		set("unit_post_fail_number", unitPostFailNumber);
		return (M)this;
	}

	public java.lang.Integer getUnitPostFailNumber() {
		return getInt("unit_post_fail_number");
	}

	public M setUnitIntroduce(java.lang.String unitIntroduce) {
		set("unit_introduce", unitIntroduce);
		return (M)this;
	}

	public java.lang.String getUnitIntroduce() {
		return getStr("unit_introduce");
	}

	public M setUnitImg(java.lang.String unitImg) {
		set("unit_img", unitImg);
		return (M)this;
	}

	public java.lang.String getUnitImg() {
		return getStr("unit_img");
	}

	public M setUnitCreateTime(java.util.Date unitCreateTime) {
		set("unit_create_time", unitCreateTime);
		return (M)this;
	}

	public java.util.Date getUnitCreateTime() {
		return get("unit_create_time");
	}

	public M setUnitOrgin(java.lang.String unitOrgin) {
		set("unit_orgin", unitOrgin);
		return (M)this;
	}

	public java.lang.String getUnitOrgin() {
		return getStr("unit_orgin");
	}

}
