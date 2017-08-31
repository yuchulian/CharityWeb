package com.jlqr.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePowerInfo<M extends BasePowerInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setPowerName(java.lang.String powerName) {
		set("power_name", powerName);
		return (M)this;
	}

	public java.lang.String getPowerName() {
		return getStr("power_name");
	}

	public M setPowerUrl(java.lang.String powerUrl) {
		set("power_url", powerUrl);
		return (M)this;
	}

	public java.lang.String getPowerUrl() {
		return getStr("power_url");
	}

	public M setPowerState(java.lang.Integer powerState) {
		set("power_state", powerState);
		return (M)this;
	}

	public java.lang.Integer getPowerState() {
		return getInt("power_state");
	}

	public M setPowerPid(java.lang.Integer powerPid) {
		set("power_pid", powerPid);
		return (M)this;
	}

	public java.lang.Integer getPowerPid() {
		return getInt("power_pid");
	}

	public M setPowerIdPath(java.lang.String powerIdPath) {
		set("power_id_path", powerIdPath);
		return (M)this;
	}

	public java.lang.String getPowerIdPath() {
		return getStr("power_id_path");
	}

	public M setPowerNamePath(java.lang.String powerNamePath) {
		set("power_name_path", powerNamePath);
		return (M)this;
	}

	public java.lang.String getPowerNamePath() {
		return getStr("power_name_path");
	}

	public M setPowerIco(java.lang.String powerIco) {
		set("power_ico", powerIco);
		return (M)this;
	}

	public java.lang.String getPowerIco() {
		return getStr("power_ico");
	}

	public M setPowerSort(java.lang.Integer powerSort) {
		set("power_sort", powerSort);
		return (M)this;
	}

	public java.lang.Integer getPowerSort() {
		return getInt("power_sort");
	}

}
