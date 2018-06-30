package com.admin.controller.page;

import java.util.List;

import com.admin.common.ControllerUtil;
import com.admin.common.model.ForumChannel;
import com.admin.common.model.ForumInfoView;
import com.admin.interceptor.NewService;
import com.common.service.ChannelService;
import com.common.service.ForumInfoService;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;

public class ForumInfoPage extends ControllerUtil {
	@NewService("ForumInfoService")
	ForumInfoService forumInfoService;
	
	@NewService("ChannelService")
	ChannelService channelService;
	public void index(){
		//查询所有帖子类型
		List<ForumChannel> forumChannelList = channelService.findAllForumChannel();
		setSessionAttr("forumChannelList", JsonKit.toJson(forumChannelList));
		render("forumInfoIndex.html");
	}
}
