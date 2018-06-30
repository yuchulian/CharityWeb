package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.model.CarouseInfo;
import com.admin.common.model.NewInfo;
import com.admin.interceptor.NewService;
import com.common.service.NewsService;
import com.jfinal.core.Controller;

public class NewsInfoData extends Controller{
	@NewService("NewsService")
	NewsService newsService;
	public void newsInfoPaginate(){
		try {
			renderJson(newsService.newsInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void newsInfoSave(){
		HashMap returnMsg = new HashMap();
		try {
			NewInfo newsInfo = getModel(NewInfo.class, "newsInfo");
			newsService.newsInfoSave(newsInfo,this);
			returnMsg.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "保存失败");
		}
		renderJson(returnMsg);
	}
	public void newsInfoDelete(){
		HashMap returnMsg = new HashMap();
		try {
			newsService.newsInfoDelete(getParaToInt("id"));
			returnMsg.put("returnMsg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "删除失败");
		}
		renderJson(returnMsg);
	}
	public void newsInfoToTop(){
		HashMap returnMsg = new HashMap();
		try {
			newsService.newsInfoToTop(getParaToInt("id"));
			returnMsg.put("returnMsg", "置顶成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "置顶失败");
		}
		renderJson(returnMsg);
	}
	public void newsInfoCanceltop(){
		HashMap returnMsg = new HashMap();
		try {
			newsService.newsInfoCanceltop(getParaToInt("id"));
			returnMsg.put("returnMsg", "取消置顶成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "取消置顶失败");
		}
		renderJson(returnMsg);
	}
	public void newsInfoCheckpage(){
		
	}
	//审核通过
	public void newsInfoCheckSuccess(){
		HashMap returnMsg = new HashMap();
		try {
			newsService.newsInfoCheckSuccess(getParaToInt("id"));
			returnMsg.put("returnMsg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "操作失败");
		}
		renderJson(returnMsg);
	}
	//审核不通过
	public void newsInfoCheckFail(){
		HashMap returnMsg = new HashMap();
		try {
			newsService.newsInfoCheckFail(getParaToInt("id"));
			returnMsg.put("returnMsg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "操作失败");
		}
		renderJson(returnMsg);
	}
}
