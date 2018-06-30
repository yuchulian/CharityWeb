package com.common.service;

import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.omg.CosNaming.IstringHelper;

import com.admin.common.ServiceUtil;
import com.admin.common.model.ForumChannel;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

public class ChannelService extends ServiceUtil {
	public Page<ForumChannel> channelPaginate(Controller controller) throws Exception {
		return this.paginate(ForumChannel.class, controller);
	}	
	public List<ForumChannel> channelList(Controller controller) throws Exception {
		return this.list(ForumChannel.class, controller);
	}
	public ForumChannel findchannelById(Integer id) throws Exception {
		return ForumChannel.dao.findById(id);
	}
	public List<ForumChannel> findchannelListByPId(Integer channel_pid) throws Exception {
		return ForumChannel.dao.find("select * from forum_channel where channel_pid = "+channel_pid);
	}
	public void channelSave(ForumChannel channel) throws Exception {
		if(null != channel) {
			ForumChannel channelParent = this.findchannelById(channel.getChannelPid());
			channel.setChannelName(channel.getChannelName().replaceAll("\\,", ""));
			if(null == channel.getId()) {
				channel.setId(getMaxColumn(ForumChannel.class, "id") + 1);
				channel.setChannel_topics(0);
				channel.setChannelComments(0);
				//组装path和pathname
				if(null != channelParent) {
					channel.setChannelIdPath(channelParent.getChannelIdPath()+channel.getId()+",");
					channel.setChannelNamePath(channelParent.getChannelNamePath()+channel.getChannelName()+",");
					channelParent.setChannelChildrenSize(channelParent.getChannelChildrenSize()+1);
					channelParent.update();
				} else {
					channel.setChannelIdPath(","+channel.getId()+",");
					channel.setChannelNamePath(","+channel.getChannelName()+",");
				}
				
				channel.save();
			} else {
				//组装path和pathname
				if(null != channelParent) {
					channel.setChannelNamePath(channelParent.getChannelNamePath()+channel.getChannelName()+",");
				} else {
					channel.setChannelNamePath(","+channel.getChannelName()+",");
				}
				
				//修改该pathname的子类
				ForumChannel channelOld = this.findchannelById(channel.getId());
				List<ForumChannel> channelList = ForumChannel.dao.find("select * from forum_channel where channel_id_path like '"+channelOld.getChannelIdPath()+"%' and channel_id_path <> '"+channelOld.getChannelIdPath()+"'");
				for (ForumChannel _channel : channelList) {
					_channel.setChannelNamePath(_channel.getChannelNamePath().replace(channelOld.getChannelNamePath(), channel.getChannelNamePath()));
					_channel.update();
				}
				
				channel.update();
			}
		}
	}
	public void deletechannelById(Integer channelId) throws Exception {
		ForumChannel channel = this.findchannelById(channelId);
		if(channel!=null){
			ForumChannel channelParent = this.findchannelById(channel.getChannelPid());
			if(channelParent!=null){
				channelParent.setChannelChildrenSize(channelParent.getChannelChildrenSize()-1);
				channelParent.update();
			}
		}
		Db.update("delete from forum_channel where channel_id_path like '"+channel.getChannelIdPath()+"%'");
	}
	public List<ForumChannel> channelByPid(Integer pid) throws Exception{
		return ForumChannel.dao.find("select * from forum_channel where channel_pid =?",pid);
	}
	public List<ForumChannel> channelByIdPath(String channelIdPath) throws Exception{
		return ForumChannel.dao.find("select * from forum_channel where channel_id_path like '"+channelIdPath+"%'");
	}
	public List<ForumChannel> findchannelListByIdPathLike(Integer pid) {
		return ForumChannel.dao.find("select * from forum_channel where channel_id_path like ',"+pid+",%' and channel_pid <> 0 ORDER BY channel_children_size DESC");
	}
	public List<ForumChannel> findAllForumChannel() {
		return ForumChannel.dao.find("select * from forum_channel");
	}

	

	
}
