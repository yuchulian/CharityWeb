package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.ReimburseInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.ReimburseInfoService;

public class ReimburseInfoData extends ControllerUtil{
	HashMap<String,Object> returnMsg = getReturnMap();
	@NewService("ReimburseInfoService")
	ReimburseInfoService reimburseInfoService;
	public void reimburseInfopaginate(){
		try {
			renderJson(reimburseInfoService.reimburseInfopaginate(this));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void reimburseInfoSave(){
		ReimburseInfo reimburseInfo = getModel(ReimburseInfo.class);
		try {
			reimburseInfoService.reimburseInfoSave(reimburseInfo,this);
			returnMsg.put("returnState", "success");
			returnMsg.put("returnMsg","操作成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		renderJson(returnMsg);
	}
	public void reimburseInfoDelete(){
		int id = getParaToInt("id");
		System.out.println(id);
		try {
			reimburseInfoService.reimburseInfoDeleteById(id);
			returnMsg.put("returnState", "success");
			returnMsg.put("returnMsg","删除成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		renderJson(returnMsg);
	}
	
	/**
	 * 报销统计
	 */
	 public void costCount() {
		 try {
			 renderJson(reimburseInfoService.costCount());
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
}
