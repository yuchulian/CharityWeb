package com.admin.controller.page;

import org.apache.commons.lang.StringUtils;

import com.admin.common.model.ForumManagerInfo;
import com.admin.common.model.WikipediaInfo;
import com.admin.interceptor.NewService;
import com.common.service.ForumManagerService;
import com.jfinal.core.Controller;

public class ForumManagerPage extends Controller {
	@NewService("ForumManagerService")
	ForumManagerService forumManagerService;
	public void forumAnnouncementPage(){
		
	}
	public void forumManagerInfoEdit(){
		ForumManagerInfo forumManagerInfo = new ForumManagerInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				forumManagerInfo = forumManagerService.findForumManagerInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("forumManagerInfo", forumManagerInfo);
	}
	public void forumManagerInfoCheckpage(){
		ForumManagerInfo forumManagerInfo = new ForumManagerInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				forumManagerInfo = forumManagerService.findForumManagerInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("forumManagerInfo", forumManagerInfo);
	}
}
