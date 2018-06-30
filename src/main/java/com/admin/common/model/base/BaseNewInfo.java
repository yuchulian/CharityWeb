package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseNewInfo<M extends BaseNewInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setNewsTitle(java.lang.String newsTitle) {
		set("news_title", newsTitle);
		return (M)this;
	}

	public java.lang.String getNewsTitle() {
		return getStr("news_title");
	}

	public M setNewsContent(java.lang.String newsContent) {
		set("news_content", newsContent);
		return (M)this;
	}

	public java.lang.String getNewsContent() {
		return getStr("news_content");
	}

	public M setNewsWriter(java.lang.String newsWriter) {
		set("news_writer", newsWriter);
		return (M)this;
	}

	public java.lang.String getNewsWriter() {
		return getStr("news_writer");
	}

	public M setNewsCreatetime(java.util.Date newsCreatetime) {
		set("news_createtime", newsCreatetime);
		return (M)this;
	}

	public java.util.Date getNewsCreatetime() {
		return get("news_createtime");
	}

	public M setNewsChangetime(java.util.Date newsChangetime) {
		set("news_changetime", newsChangetime);
		return (M)this;
	}

	public java.util.Date getNewsChangetime() {
		return get("news_changetime");
	}

	public M setNewsIntroduce(java.lang.String newsIntroduce) {
		set("news_introduce", newsIntroduce);
		return (M)this;
	}

	public java.lang.String getNewsIntroduce() {
		return getStr("news_introduce");
	}

	public M setNewsType(java.lang.Integer newsType) {
		set("news_type", newsType);
		return (M)this;
	}

	public java.lang.Integer getNewsType() {
		return getInt("news_type");
	}

	public M setNewsState(java.lang.Integer newsState) {
		set("news_state", newsState);
		return (M)this;
	}

	public java.lang.Integer getNewsState() {
		return getInt("news_state");
	}

	public M setNewsIsTop(java.lang.Integer newsIsTop) {
		set("news_is_top", newsIsTop);
		return (M)this;
	}

	public java.lang.Integer getNewsIsTop() {
		return getInt("news_is_top");
	}

	public M setNewsImg(java.lang.String newsImg) {
		set("news_img", newsImg);
		return (M)this;
	}

	public java.lang.String getNewsImg() {
		return getStr("news_img");
	}

}