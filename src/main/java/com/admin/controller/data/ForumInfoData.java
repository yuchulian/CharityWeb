package com.admin.controller.data;

import com.admin.common.ControllerUtil;
import com.admin.interceptor.NewService;
import com.common.service.ForumInfoService;

public class ForumInfoData extends ControllerUtil{
	@NewService("ForumInfoService")
	ForumInfoService forumInfoService;
	public void forumInfoPaginate(){
		try {
			renderJson(forumInfoService.forumInfoPaginate(this));;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
