package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.ControllerUtil;
import com.admin.common.model.ForumChannel;
import com.admin.interceptor.NewService;
import com.common.service.ChannelService;
public class ChannelData extends ControllerUtil{

	@NewService("ChannelService")
	private ChannelService channelService;
	
	public void channelList() {
		HashMap returnMap = new HashMap();
		try {
			String channel_pid = channelService.getPara(this, "channel_pid", "0");
			if("0".equals(channel_pid)) {
				returnMap.put("channel", new ForumChannel());
			} else {
				ForumChannel channel = channelService.findchannelById(Integer.parseInt(channel_pid));
				if(null == channel)
					returnMap.put("channel", new ForumChannel());
				else
					returnMap.put("channel", channel);
			}
			returnMap.put("channelList", channelService.channelList(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void channelSave() {
		HashMap returnMsg = new HashMap();
		try {
			ForumChannel channel = getModel(ForumChannel.class, "channel");
			channelService.channelSave(channel);
			returnMsg.put("content", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "保存失败");
		}
		renderJson(returnMsg);
	}
	
	public void channelDelete() {
		HashMap returnMsg = new HashMap();
		try {
			channelService.deletechannelById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
	
	public void channelPaginate() {
		try {
			renderJson(channelService.channelPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
