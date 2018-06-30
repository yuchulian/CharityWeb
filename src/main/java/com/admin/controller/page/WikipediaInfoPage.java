package com.admin.controller.page;

import org.apache.commons.lang.StringUtils;

import com.admin.common.model.NewInfo;
import com.admin.common.model.WikipediaInfo;
import com.admin.interceptor.NewService;
import com.common.service.WikipediaInfoService;
import com.jfinal.core.Controller;

public class WikipediaInfoPage extends Controller {
	@NewService("WikipediaInfoService")
	WikipediaInfoService wikipediaInfoService;
	
	public void index(){
		render("wikipediaInfoIndex.html");
	}
	public void wikipediaInfoEdit(){
		WikipediaInfo wikipediaInfo = new WikipediaInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				wikipediaInfo = wikipediaInfoService.findwikipediaInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("wikipediaInfo", wikipediaInfo);
	}
	public void wikipediaInfoCheckpage(){
		WikipediaInfo wikipediaInfo = new WikipediaInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				wikipediaInfo = wikipediaInfoService.findwikipediaInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("wikipediaInfo", wikipediaInfo);
	}
	public void wikipediaInfoCheckIndex(){
		
	}
}