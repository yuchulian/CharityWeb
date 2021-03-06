package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseDictionary<M extends BaseDictionary<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setDictionaryName(java.lang.String dictionaryName) {
		set("dictionary_name", dictionaryName);
		return (M)this;
	}

	public java.lang.String getDictionaryName() {
		return getStr("dictionary_name");
	}

	public M setDictionaryType(java.lang.String dictionaryType) {
		set("dictionary_type", dictionaryType);
		return (M)this;
	}

	public java.lang.String getDictionaryType() {
		return getStr("dictionary_type");
	}

	public M setDictionaryPid(java.lang.Integer dictionaryPid) {
		set("dictionary_pid", dictionaryPid);
		return (M)this;
	}

	public java.lang.Integer getDictionaryPid() {
		return getInt("dictionary_pid");
	}

	public M setDictionaryIdPath(java.lang.String dictionaryIdPath) {
		set("dictionary_id_path", dictionaryIdPath);
		return (M)this;
	}

	public java.lang.String getDictionaryIdPath() {
		return getStr("dictionary_id_path");
	}

	public M setDictionaryNamePath(java.lang.String dictionaryNamePath) {
		set("dictionary_name_path", dictionaryNamePath);
		return (M)this;
	}

	public java.lang.String getDictionaryNamePath() {
		return getStr("dictionary_name_path");
	}

	public M setDictionaryOrder(java.lang.Integer dictionaryOrder) {
		set("dictionary_order", dictionaryOrder);
		return (M)this;
	}

	public java.lang.Integer getDictionaryOrder() {
		return getInt("dictionary_order");
	}

	public M setDictionaryRemark(java.lang.String dictionaryRemark) {
		set("dictionary_remark", dictionaryRemark);
		return (M)this;
	}

	public java.lang.String getDictionaryRemark() {
		return getStr("dictionary_remark");
	}

	public M setDictionaryChildrenSize(java.lang.Integer dictionaryChildrenSize) {
		set("dictionary_children_size", dictionaryChildrenSize);
		return (M)this;
	}

	public java.lang.Integer getDictionaryChildrenSize() {
		return getInt("dictionary_children_size");
	}

}
