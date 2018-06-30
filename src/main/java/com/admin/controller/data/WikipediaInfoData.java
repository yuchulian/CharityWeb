package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.model.NewInfo;
import com.admin.common.model.WikipediaInfo;
import com.admin.interceptor.NewService;
import com.common.service.WikipediaInfoService;
import com.jfinal.core.Controller;

public class WikipediaInfoData extends Controller {
	@NewService("WikipediaInfoService")
	WikipediaInfoService wikipediaInfoService;
	public void wikipediaInfoPaginate(){
		try {
			renderJson(wikipediaInfoService.wikipediaInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void wikipediaInfoSave(){
		HashMap returnMsg = new HashMap();
		try {
			WikipediaInfo wikipediaInfo = getModel(WikipediaInfo.class, "wikipediaInfo");
			wikipediaInfoService.wikipediaInfoSave(wikipediaInfo,this);
			returnMsg.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "保存失败");
		}
		renderJson(returnMsg);
	}
	public void wikipediaInfoDelete(){
		HashMap returnMsg = new HashMap();
		try {
			wikipediaInfoService.wikipediaInfoDelete(getParaToInt("id"));
			returnMsg.put("returnMsg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "删除失败");
		}
		renderJson(returnMsg);
	}
	public void wikipediaInfoToTop(){
		HashMap returnMsg = new HashMap();
		try {
			wikipediaInfoService.wikipediaInfoToTop(getParaToInt("id"));
			returnMsg.put("returnMsg", "置顶成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "置顶失败");
		}
		renderJson(returnMsg);
	}
	public void wikipediaInfoCanceltop(){
		HashMap returnMsg = new HashMap();
		try {
			wikipediaInfoService.wikipediaInfoCanceltop(getParaToInt("id"));
			returnMsg.put("returnMsg", "取消置顶成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "取消置顶失败");
		}
		renderJson(returnMsg);
	}
	public void wikipediaInfoCheckpage(){
		
	}
	//审核通过
	public void wikipediaInfoCheckSuccess(){
		HashMap returnMsg = new HashMap();
		try {
			wikipediaInfoService.wikipediaInfoCheckSuccess(getParaToInt("id"));
			returnMsg.put("returnMsg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "操作失败");
		}
		renderJson(returnMsg);
	}
	//审核不通过
	public void wikipediaInfoCheckFail(){
		HashMap returnMsg = new HashMap();
		try {
			wikipediaInfoService.wikipediaInfoCheckFail(getParaToInt("id"));
			returnMsg.put("returnMsg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "操作失败");
		}
		renderJson(returnMsg);
	}
}
