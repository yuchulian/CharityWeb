package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.model.NewInfo;
import com.admin.common.model.PublicManInfo;
import com.admin.interceptor.NewService;
import com.common.service.PublicManInfoService;
import com.jfinal.core.Controller;

public class PublicManInfoData extends Controller {
	@NewService("PublicManInfoService")
	PublicManInfoService publicManInfoService;
	public void publicManInfoPaginate(){
		try {
			renderJson(publicManInfoService.publicManInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void publicManInfoSave(){
		HashMap returnMsg = new HashMap();
		try {
			PublicManInfo publicManInfo = getModel(PublicManInfo.class, "publicManInfo");
			publicManInfoService.publicManInfoSave(publicManInfo,this);
			returnMsg.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "保存失败");
		}
		renderJson(returnMsg);
	}
	public void publicManInfoDelete(){
		HashMap returnMsg = new HashMap();
		try {
			publicManInfoService.publicManInfoDelete(getParaToInt("id"));
			returnMsg.put("returnMsg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "删除失败");
		}
		renderJson(returnMsg);
	}
	public void publicManInfoToTop(){
		HashMap returnMsg = new HashMap();
		try {
			publicManInfoService.publicManInfoToTop(getParaToInt("id"));
			returnMsg.put("returnMsg", "置顶成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "置顶失败");
		}
		renderJson(returnMsg);
	}
	public void publicManInfoCanceltop(){
		HashMap returnMsg = new HashMap();
		try {
			publicManInfoService.publicManInfoCanceltop(getParaToInt("id"));
			returnMsg.put("returnMsg", "取消置顶成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "取消置顶失败");
		}
		renderJson(returnMsg);
	}
	public void publicManInfoCheckpage(){
		
	}
	//审核通过
	public void publicManInfoCheckSuccess(){
		HashMap returnMsg = new HashMap();
		try {
			publicManInfoService.publicManInfoCheckSuccess(getParaToInt("id"));
			returnMsg.put("returnMsg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "操作失败");
		}
		renderJson(returnMsg);
	}
	//审核不通过
	public void publicManInfoCheckFail(){
		HashMap returnMsg = new HashMap();
		try {
			publicManInfoService.publicManInfoCheckFail(getParaToInt("id"));
			returnMsg.put("returnMsg", "操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "操作失败");
		}
		renderJson(returnMsg);
	}
}
