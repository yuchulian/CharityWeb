package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.ControllerUtil;
import com.admin.common.model.CarouseInfo;
import com.admin.common.model.PowerInfo;
import com.admin.interceptor.NewService;
import com.common.service.CarouseInfoService;

public class CarouseInfoData extends ControllerUtil {
	@NewService("CarouseInfoService")
	CarouseInfoService carouseInfoService;
	public void carouseInfoPaginate(){
		try {
			renderJson(carouseInfoService.carouseInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void carouseInfoSave(){
		HashMap returnMsg = new HashMap();
		try {
			CarouseInfo carouseInfo = getModel(CarouseInfo.class, "carouseInfo");
			carouseInfoService.carouseInfoSave(carouseInfo,this);
			returnMsg.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "保存失败");
		}
		renderJson(returnMsg);
	}
	public void carouseInfoDelete(){
		HashMap returnMsg = new HashMap();
		try {
			carouseInfoService.carouseInfoService(getParaToInt("id"));
			returnMsg.put("returnMsg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "删除失败");
		}
		renderJson(returnMsg);
	}
	//进行置顶
	public void carouseInfoToTop(){
		HashMap returnMsg = new HashMap();
		try {
			carouseInfoService.carouseInfoToTop(getParaToInt("id"));
			returnMsg.put("returnMsg", "置顶成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "置顶失败");
		}
		renderJson(returnMsg);
	}
	//取消置顶
	public void carouseInfoCanceltop(){
		HashMap returnMsg = new HashMap();
		try {
			carouseInfoService.carouseInfoCanceltop(getParaToInt("id"));
			returnMsg.put("returnMsg", "取消置顶成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "取消置顶失败");
		}
		renderJson(returnMsg);
	}
}
