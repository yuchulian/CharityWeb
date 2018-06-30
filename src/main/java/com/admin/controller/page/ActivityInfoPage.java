

package com.admin.controller.page;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.model.ActivityInfo;
import com.admin.common.model.ActivityInfoView;
import com.admin.common.model.ActivityUnit;
import com.admin.common.model.Dictionary;
import com.admin.interceptor.NewService;
import com.common.service.ActivityInfoService;
import com.common.service.DictionaryService;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;

public class ActivityInfoPage extends Controller {
	@NewService("ActivityInfoService")
	ActivityInfoService activityInfoService;
	@NewService("DictionaryService")
	DictionaryService dictionaryService;
	//跳轉公益單位
	public void activityUnitPage(){
		render("activityUnitIndex.html");
	}
	//公益单位编辑
	public void activityUnitEdit(){
		ActivityUnit activityUnit = new ActivityUnit();
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				activityUnit = activityInfoService.findActivityUnitById(getParaToInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("activityUnit", activityUnit);
	}
	//进行跳转公益活动
	public void index(){
		render("activityInfoIndex.html");
	}
	//公益活动编辑页面
	public void activityInfoEdit(){
		ActivityInfo activityInfo = new ActivityInfo();
		//查询公益单位
		List<ActivityUnit> activityUnitList = null;
		List<Dictionary> activityTypeList = null;
		try {
			if(StringUtils.isNotBlank(getPara("id"))){
				activityInfo = activityInfoService.findActivityInfoById(getParaToInt("id"));
			}
			activityUnitList  =	activityInfoService.findActivityUnitByOrgin(PropKit.get("company"));
			activityTypeList = dictionaryService.findDictionaryListByPId(PropKit.getInt("activityType"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("activityInfo", activityInfo);
		setAttr("activityUnitList",activityUnitList);
		setAttr("activityTypeList", activityTypeList);
	}
	public void activityCheckIndex(){
		
	}
	
	public void activityCheckEdit(){
		ActivityInfoView activityInfo = new ActivityInfoView();
		try {
			String id = getPara("id");
			if (id!=null&&id.length()>0) {
				activityInfo = activityInfoService.findActivityInfoViewById(Integer.parseInt(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("activityInfo",activityInfo);
	}
	public void activityAttendInfo(){
		Integer id = getParaToInt("id");
		setAttr("actId", id);
	}
}
