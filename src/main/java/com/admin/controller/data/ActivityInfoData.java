package com.admin.controller.data;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.admin.common.model.ActivityInfo;
import com.admin.common.model.ActivityUnit;
import com.admin.interceptor.NewService;
import com.common.service.ActivityInfoService;
import com.jfinal.core.Controller;

public class ActivityInfoData extends Controller {
	@NewService("ActivityInfoService")
	ActivityInfoService activityInfoService;
	
	public void activityUnitPaginate(){
		try {
			renderJson(activityInfoService.activityUnitPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//进行保存单位信息
	public void activityUnitSave(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		ActivityUnit activityUnit = getModel(ActivityUnit.class);
		try {
			activityInfoService.activityUnitSave(activityUnit);
			returnMsg.put("returnMsg","操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg","操作失败");
		}
		renderJson(returnMsg);
	}
	//删除单位信息
	public void activityUnitDelete(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			activityInfoService.activityUnitDelete(getParaToInt("id"));
			returnMsg.put("returnMsg","删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg","删除失败");
		}
		renderJson(returnMsg);
	}
	//进行查询公益活动
	public void activityInfoPaginate(){
		try {
			renderJson(activityInfoService.activityInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void activityInfoSave(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		ActivityInfo activityInfo = new ActivityInfo();
		try {
			activityInfo = getModel(ActivityInfo.class);
			activityInfoService.activityInfoSave(activityInfo);
			returnMsg.put("returnMsg","保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg","保存失败");
		}
		renderJson(returnMsg);
	}
	public void activityInfoDelete(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			activityInfoService.activityInfoDelete(getParaToInt("id"));
			returnMsg.put("returnMsg","删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg","删除失败");
			
		}
		renderJson(returnMsg);
	}
	//推荐新闻
	public void activityInfoRecomend(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			activityInfoService.activityInfoRecomend(getPara("id"));
			returnMsg.put("returnMsg","操作成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg","操作失败");
			
		}
		renderJson(returnMsg);
	}
	public void activityInfoChecksSuccess(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			String id = getPara("id");
				activityInfoService.activityInfoCheckFail(id);
			returnMsg.put("returnMsg","操作成功");
		} catch (Exception e) {
			returnMsg.put("returnMsg","操作失败");
		}
		renderJson(returnMsg);
	}
	public void activityInfoChecksFail(){
		HashMap<String,String> returnMsg = new HashMap<String,String>();
		try {
			String id = getPara("id");
				activityInfoService.activityInfoChecksFail(id);
			returnMsg.put("returnMsg","操作成功");
		} catch (Exception e) {
			returnMsg.put("returnMsg","操作失败");
		}
		renderJson(returnMsg);
	}
	public void attendUserInfoPaginate(){
		try {
			renderJson(activityInfoService.attendUserInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
