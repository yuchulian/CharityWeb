package com.admin.controller.page;

import org.apache.commons.lang.StringUtils;

import com.admin.common.ControllerUtil;
import com.admin.common.model.ForumChannel;
import com.admin.interceptor.NewService;
import com.common.service.ChannelService;

public class ChannelPage extends ControllerUtil{

	@NewService("ChannelService")
	private ChannelService channelService;
	
	public void index() {
		render("forumChannelIndex.html");
	}
	
	public void channelEdit() {
		ForumChannel channel = new ForumChannel();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				channel = channelService.findchannelById(Integer.parseInt(getPara("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("channel", channel);
	}
	
}
