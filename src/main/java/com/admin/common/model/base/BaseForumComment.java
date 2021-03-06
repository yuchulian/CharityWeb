package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseForumComment<M extends BaseForumComment<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setForumContent(java.lang.String forumContent) {
		set("forum_content", forumContent);
		return (M)this;
	}

	public java.lang.String getForumContent() {
		return getStr("forum_content");
	}

	public M setForumDiscusstime(java.util.Date forumDiscusstime) {
		set("forum_discusstime", forumDiscusstime);
		return (M)this;
	}

	public java.util.Date getForumDiscusstime() {
		return get("forum_discusstime");
	}

	public M setIsdisplay(java.lang.Integer isdisplay) {
		set("isdisplay", isdisplay);
		return (M)this;
	}

	public java.lang.Integer getIsdisplay() {
		return getInt("isdisplay");
	}

	public M setCommentpid(java.lang.Integer commentpid) {
		set("commentpid", commentpid);
		return (M)this;
	}

	public java.lang.Integer getCommentpid() {
		return getInt("commentpid");
	}

	public M setCommentIP(java.lang.String commentIP) {
		set("commentIP", commentIP);
		return (M)this;
	}

	public java.lang.String getCommentIP() {
		return getStr("commentIP");
	}

	public M setUserId(java.lang.Integer userId) {
		set("user_id", userId);
		return (M)this;
	}

	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

}
