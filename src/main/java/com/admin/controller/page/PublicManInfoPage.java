package com.admin.controller.page;

import org.apache.commons.lang.StringUtils;

import com.admin.common.model.NewInfo;
import com.admin.common.model.PublicManInfo;
import com.admin.interceptor.NewService;
import com.common.service.PublicManInfoService;
import com.jfinal.core.Controller;

public class PublicManInfoPage extends Controller {
	@NewService("PublicManInfoService")
	PublicManInfoService publicManInfoService;
	
	public void index(){
		render("publicManInfoIndex.html");
	}
	public void publicManInfoEdit(){
		PublicManInfo publicManInfo = new PublicManInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				publicManInfo = publicManInfoService.findpublicManInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("publicManInfo", publicManInfo);
	}
	public void publicManInfoCheckpage(){
		PublicManInfo publicManInfo = new PublicManInfo();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				publicManInfo = publicManInfoService.findpublicManInfoById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("publicManInfo", publicManInfo);
	}
	public void publicManInfoCheckIndex(){
		
	}
}