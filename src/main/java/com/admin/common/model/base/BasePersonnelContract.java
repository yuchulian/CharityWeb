package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BasePersonnelContract<M extends BasePersonnelContract<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setEmployId(java.lang.Integer employId) {
		set("employ_id", employId);
		return (M)this;
	}

	public java.lang.Integer getEmployId() {
		return getInt("employ_id");
	}

	public M setContractNumber(java.lang.String contractNumber) {
		set("contract_number", contractNumber);
		return (M)this;
	}

	public java.lang.String getContractNumber() {
		return getStr("contract_number");
	}

	public M setContractType(java.lang.Integer contractType) {
		set("contract_type", contractType);
		return (M)this;
	}

	public java.lang.Integer getContractType() {
		return getInt("contract_type");
	}

	public M setContractState(java.lang.Integer contractState) {
		set("contract_state", contractState);
		return (M)this;
	}

	public java.lang.Integer getContractState() {
		return getInt("contract_state");
	}

	public M setContractStart(java.util.Date contractStart) {
		set("contract_start", contractStart);
		return (M)this;
	}

	public java.util.Date getContractStart() {
		return get("contract_start");
	}

	public M setTrialStart(java.util.Date trialStart) {
		set("trial_start", trialStart);
		return (M)this;
	}

	public java.util.Date getTrialStart() {
		return get("trial_start");
	}

	public M setTrialDays(java.lang.Integer trialDays) {
		set("trial_days", trialDays);
		return (M)this;
	}

	public java.lang.Integer getTrialDays() {
		return getInt("trial_days");
	}

	public M setTrialEnd(java.util.Date trialEnd) {
		set("trial_end", trialEnd);
		return (M)this;
	}

	public java.util.Date getTrialEnd() {
		return get("trial_end");
	}

	public M setContractEnd(java.util.Date contractEnd) {
		set("contract_end", contractEnd);
		return (M)this;
	}

	public java.util.Date getContractEnd() {
		return get("contract_end");
	}

	public M setContractRemark(java.lang.String contractRemark) {
		set("contract_remark", contractRemark);
		return (M)this;
	}

	public java.lang.String getContractRemark() {
		return getStr("contract_remark");
	}

}
