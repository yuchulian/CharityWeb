package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.model.ForumManagerInfo;
import com.admin.interceptor.NewService;
import com.common.service.ForumManagerService;
import com.jfinal.core.Controller;

public class ForumManagerData extends Controller {
	@NewService("ForumManagerService")
	ForumManagerService forumManagerService;
	public void forumManagerPaginate(){
		try {
			renderJson(forumManagerService.forumManagerInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void forumManagerSave(){
		HashMap returnMsg = new HashMap();
		try {
			ForumManagerInfo forumManagerInfo = getModel(ForumManagerInfo.class, "forumManagerInfo");
			forumManagerService.forumManagerInfoSave(forumManagerInfo,this);
			returnMsg.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "保存失败");
		}
		renderJson(returnMsg);
	}
	public void forumManagerDelete(){
		HashMap returnMsg = new HashMap();
		try {
			forumManagerService.forumManagerInfoDelete(getParaToInt("id"));
			returnMsg.put("returnMsg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("returnMsg", "删除失败");
		}
		renderJson(returnMsg);
	}
	//审核通过
		public void forumManagerInfoCheckSuccess(){
			HashMap returnMsg = new HashMap();
			try {
				forumManagerService.forumManagerInfoCheckSuccess(getParaToInt("id"));
				returnMsg.put("returnMsg", "操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				returnMsg.put("returnMsg", "操作失败");
			}
			renderJson(returnMsg);
		}
		//审核不通过
		public void forumManagerInfoCheckFail(){
			HashMap returnMsg = new HashMap();
			try {
				forumManagerService.forumManagerInfoCheckFail(getParaToInt("id"));
				returnMsg.put("returnMsg", "操作成功");
			} catch (Exception e) {
				e.printStackTrace();
				returnMsg.put("returnMsg", "操作失败");
			}
			renderJson(returnMsg);
		}
}
