package com.jlqr.controller.page;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.ReimburseInfo;
import com.jlqr.common.model.ReimburseInfoView;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.DictionaryService;
import com.jlqr.service.ReimburseInfoService;

public class ReimburseInfoPage extends Controller{
	@NewService("ReimburseInfoService")
	ReimburseInfoService reimburseInfoService;
	@NewService("DictionaryService")
	DictionaryService dictionaryService;
	public void index(){
		render("reimburseInfoIndex.html");
	}
	public void reimburseInfoEdit(){
		ReimburseInfoView reimburseInfo = new ReimburseInfoView();
		List<Dictionary> reimburseBrandList = null;
		List<Dictionary> reimburseTypeList = null;
		try {
			reimburseBrandList = dictionaryService.dictionaryByPid(PropKit.getInt("reimburse_brand"));
			reimburseTypeList = dictionaryService.dictionaryByPid(PropKit.getInt("reimburse_type"));
			if(StringUtils.isNotBlank(getPara("id"))){
				reimburseInfo =	reimburseInfoService.findReimburseInfoViewById(getParaToInt("id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setAttr("reimburseBrandList", reimburseBrandList);
		setAttr("reimburseTypeList", reimburseTypeList);
		setAttr("reimburseInfo", reimburseInfo);
	}
	public void reimburseInfoDetail(){
		ReimburseInfoView reimburseInfo = new ReimburseInfoView();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				reimburseInfo =	reimburseInfoService.findReimburseInfoViewById(getParaToInt("id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setAttr("reimburseInfo", reimburseInfo);
	}
}
