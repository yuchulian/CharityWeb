package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseWikipediaInfo<M extends BaseWikipediaInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setWikipediaName(java.lang.String wikipediaName) {
		set("wikipedia_name", wikipediaName);
		return (M)this;
	}

	public java.lang.String getWikipediaName() {
		return getStr("wikipedia_name");
	}

	public M setWikipediaIstop(java.lang.Integer wikipediaIstop) {
		set("wikipedia_isTop", wikipediaIstop);
		return (M)this;
	}

	public java.lang.Integer getWikipediaIstop() {
		return getInt("wikipedia_isTop");
	}

	public M setWikipediaImg(java.lang.String wikipediaImg) {
		set("wikipedia_img", wikipediaImg);
		return (M)this;
	}

	public java.lang.String getWikipediaImg() {
		return getStr("wikipedia_img");
	}

	public M setWikipediaOpenSize(java.lang.Integer wikipediaOpenSize) {
		set("wikipedia_open_size", wikipediaOpenSize);
		return (M)this;
	}

	public java.lang.Integer getWikipediaOpenSize() {
		return getInt("wikipedia_open_size");
	}

	public M setWikipediaCreateTime(java.util.Date wikipediaCreateTime) {
		set("wikipedia_create_time", wikipediaCreateTime);
		return (M)this;
	}

	public java.util.Date getWikipediaCreateTime() {
		return get("wikipedia_create_time");
	}

	public M setWikipediaState(java.lang.Integer wikipediaState) {
		set("wikipedia_state", wikipediaState);
		return (M)this;
	}

	public java.lang.Integer getWikipediaState() {
		return getInt("wikipedia_state");
	}

	public M setWikipediaContent(java.lang.String wikipediaContent) {
		set("wikipedia_content", wikipediaContent);
		return (M)this;
	}

	public java.lang.String getWikipediaContent() {
		return getStr("wikipedia_content");
	}

}
