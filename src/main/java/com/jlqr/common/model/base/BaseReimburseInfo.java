package com.jlqr.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseReimburseInfo<M extends BaseReimburseInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setReimburseBrand(java.lang.Integer reimburseBrand) {
		set("reimburse_brand", reimburseBrand);
		return (M)this;
	}

	public java.lang.Integer getReimburseBrand() {
		return getInt("reimburse_brand");
	}

	public M setReimburseType(java.lang.Integer reimburseType) {
		set("reimburse_type", reimburseType);
		return (M)this;
	}

	public java.lang.Integer getReimburseType() {
		return getInt("reimburse_type");
	}

	public M setReimburseModel(java.lang.String reimburseModel) {
		set("reimburse_model", reimburseModel);
		return (M)this;
	}

	public java.lang.String getReimburseModel() {
		return getStr("reimburse_model");
	}

	public M setReimbursePrice(java.lang.Float reimbursePrice) {
		set("reimburse_price", reimbursePrice);
		return (M)this;
	}

	public java.lang.Float getReimbursePrice() {
		return getFloat("reimburse_price");
	}

	public M setReimburseCount(java.lang.Integer reimburseCount) {
		set("reimburse_count", reimburseCount);
		return (M)this;
	}

	public java.lang.Integer getReimburseCount() {
		return getInt("reimburse_count");
	}

	public M setReimburseTotal(java.lang.Float reimburseTotal) {
		set("reimburse_total", reimburseTotal);
		return (M)this;
	}

	public java.lang.Float getReimburseTotal() {
		return getFloat("reimburse_total");
	}

	public M setReimburseRemark(java.lang.String reimburseRemark) {
		set("reimburse_remark", reimburseRemark);
		return (M)this;
	}

	public java.lang.String getReimburseRemark() {
		return getStr("reimburse_remark");
	}

	public M setReimburseNumber(java.lang.String reimburseNumber) {
		set("reimburse_number", reimburseNumber);
		return (M)this;
	}

	public java.lang.String getReimburseNumber() {
		return getStr("reimburse_number");
	}

	public M setReimburseTime(java.util.Date reimburseTime) {
		set("reimburse_time", reimburseTime);
		return (M)this;
	}

	public java.util.Date getReimburseTime() {
		return get("reimburse_time");
	}

	public M setEmployId(java.lang.Integer employId) {
		set("employ_id", employId);
		return (M)this;
	}

	public java.lang.Integer getEmployId() {
		return getInt("employ_id");
	}

	public M setReimburseState(java.lang.Integer reimburseState) {
		set("reimburse_state", reimburseState);
		return (M)this;
	}

	public java.lang.Integer getReimburseState() {
		return getInt("reimburse_state");
	}

}
