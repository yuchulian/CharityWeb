package com.admin.controller.page;

import org.apache.commons.lang.StringUtils;

import com.admin.common.model.NewInfo;
import com.admin.interceptor.NewService;
import com.common.service.NewsService;
import com.jfinal.core.Controller;

public class NewsInfoPage extends Controller{
	@NewService("NewsService")
	NewsService newsService;
	public void index(){
		render("newsInfoIndex.html");
	}
	public void newsInfoEdit(){
		NewInfo newsInfo = new NewInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				newsInfo = newsService.findNewsInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("newsInfo", newsInfo);
	}
	public void newsInfoCheckpage(){
		NewInfo newsInfo = new NewInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				newsInfo = newsService.findNewsInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("newsInfo", newsInfo);
	}
	public void newsInfoCheckIndex(){
		
	}
}
