package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseForumInfo<M extends BaseForumInfo<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setForumTitle(java.lang.String forumTitle) {
		set("forum_title", forumTitle);
		return (M)this;
	}

	public java.lang.String getForumTitle() {
		return getStr("forum_title");
	}

	public M setForumContent(java.lang.String forumContent) {
		set("forum_content", forumContent);
		return (M)this;
	}

	public java.lang.String getForumContent() {
		return getStr("forum_content");
	}

	public M setForumBigType(java.lang.Integer forumBigType) {
		set("forum_big_type", forumBigType);
		return (M)this;
	}

	public java.lang.Integer getForumBigType() {
		return getInt("forum_big_type");
	}

	public M setForumSmallType(java.lang.Integer forumSmallType) {
		set("forum_small_type", forumSmallType);
		return (M)this;
	}

	public java.lang.Integer getForumSmallType() {
		return getInt("forum_small_type");
	}

	public M setForumScore(java.lang.Integer forumScore) {
		set("forum_score", forumScore);
		return (M)this;
	}

	public java.lang.Integer getForumScore() {
		return getInt("forum_score");
	}

	public M setUserId(java.lang.Integer userId) {
		set("user_id", userId);
		return (M)this;
	}

	public java.lang.Integer getUserId() {
		return getInt("user_id");
	}

	public M setForumCreateTime(java.util.Date forumCreateTime) {
		set("forum_create_time", forumCreateTime);
		return (M)this;
	}

	public java.util.Date getForumCreateTime() {
		return get("forum_create_time");
	}

	public M setForumFinePost(java.lang.Integer forumFinePost) {
		set("forum_fine_post", forumFinePost);
		return (M)this;
	}

	public java.lang.Integer getForumFinePost() {
		return getInt("forum_fine_post");
	}

	public M setForumOpenCount(java.lang.Integer forumOpenCount) {
		set("forum_open_count", forumOpenCount);
		return (M)this;
	}

	public java.lang.Integer getForumOpenCount() {
		return getInt("forum_open_count");
	}

	public M setForumState(java.lang.Integer forumState) {
		set("forum_state", forumState);
		return (M)this;
	}

	public java.lang.Integer getForumState() {
		return getInt("forum_state");
	}

	public M setForumCommentCount(java.lang.Integer forumCommentCount) {
		set("forum_comment_count", forumCommentCount);
		return (M)this;
	}

	public java.lang.Integer getForumCommentCount() {
		return getInt("forum_comment_count");
	}

}
