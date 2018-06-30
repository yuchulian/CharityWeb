package com.admin.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseForumChannel<M extends BaseForumChannel<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setChannelName(java.lang.String channelName) {
		set("channel_name", channelName);
		return (M)this;
	}

	public java.lang.String getChannelName() {
		return getStr("channel_name");
	}

	public M setChannelType(java.lang.String channelType) {
		set("channel_type", channelType);
		return (M)this;
	}

	public java.lang.String getChannelType() {
		return getStr("channel_type");
	}

	public M setChannelPid(java.lang.Integer channelPid) {
		set("channel_pid", channelPid);
		return (M)this;
	}

	public java.lang.Integer getChannelPid() {
		return getInt("channel_pid");
	}

	public M setChannelIdPath(java.lang.String channelIdPath) {
		set("channel_id_path", channelIdPath);
		return (M)this;
	}

	public java.lang.String getChannelIdPath() {
		return getStr("channel_id_path");
	}

	public M setChannelNamePath(java.lang.String channelNamePath) {
		set("channel_name_path", channelNamePath);
		return (M)this;
	}

	public java.lang.String getChannelNamePath() {
		return getStr("channel_name_path");
	}

	public M setChannelRemark(java.lang.String channelRemark) {
		set("channel_remark", channelRemark);
		return (M)this;
	}

	public java.lang.String getChannelRemark() {
		return getStr("channel_remark");
	}

	public M setChannelChildrenSize(java.lang.Integer channelChildrenSize) {
		set("channel_children_size", channelChildrenSize);
		return (M)this;
	}

	public java.lang.Integer getChannelChildrenSize() {
		return getInt("channel_children_size");
	}

	public M setChannel_topics(java.lang.Integer channel_topics) {
		set("channel__topics", channel_topics);
		return (M)this;
	}

	public java.lang.Integer getChannel_topics() {
		return getInt("channel__topics");
	}

	public M setChannelComments(java.lang.Integer channelComments) {
		set("channel_comments", channelComments);
		return (M)this;
	}

	public java.lang.Integer getChannelComments() {
		return getInt("channel_comments");
	}

}
