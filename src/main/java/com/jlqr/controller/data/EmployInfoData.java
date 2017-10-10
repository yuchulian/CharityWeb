package com.jlqr.controller.data;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.jfinal.kit.PropKit;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EducationInfo;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.ItemInfo;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.WorkInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.DictionaryService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.LoginInfoService;

public class EmployInfoData extends ControllerUtil {

	@NewService("EmployInfoService")
	private EmployInfoService employInfoService;
	
	@NewService("LoginInfoService")
	private LoginInfoService loginInfoService;
	
	@NewService("DictionaryService")
	private DictionaryService dictionaryService;
	
	public void employInfoPaginate() {
		try {
			renderJson(employInfoService.employInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//开通账号
	/*public void employOpenAccount(){
		Integer id = getParaToInt("id");
		HashMap<String, String> returnMap = new HashMap<String, String>();
		LoginInfo employLoginInfo = new LoginInfo();
		try {
			EmployInfo employInfo = employInfoService.findEmployInfoById(id);
			if(employInfo==null){
				returnMap.put("content","开通失败");
			}else{
				employLoginInfo.setLoginName(employInfo.getEmployName());
				String initPassword = PropKit.get("initPassword");
				employLoginInfo.setLoginPwd(initPassword);
				employLoginInfo.setCreateTime(new Date());
				employLoginInfo.setId(employInfo.getId());
				employLoginInfo.setLoginImg(employInfo.getEmployImg());
				//进行判断账户是不是已经开通
				//保存信息
				boolean state = loginInfoService.LoginInfoSave(employLoginInfo);
				if(state){
					returnMap.put("content","开通成功");
				}else{
					returnMap.put("content","开通失败");
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson(returnMap);
	}*/
	
	
	//进行保存登录信息
	public void employViewSave(){
		HashMap<String, Object> returnMap = getReturnMap();
		
		LoginInfo loginInfo = getModel(LoginInfo.class,"loginInfo");
		EmployInfo employInfo = getModel(EmployInfo.class,"employInfo");
		Integer id = employInfo.getId();
		if(StringUtils.isNotBlank(loginInfo.getLoginPwd())) {
			loginInfo.setLoginPwd(DigestUtils.md5Hex(loginInfo.getLoginPwd()));
		}
		if(null != loginInfo.getId())
			id = null;
		try {
			loginInfoService.LoginInfoSave(loginInfo, id);
			
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void employInfoList() {
		HashMap returnMap = new HashMap();
		try {
			String power_pid = employInfoService.getPara(this, "power_pid", "0");
			if("0".equals(power_pid)) {
				returnMap.put("employInfo", new EmployInfo());
			} else {
				EmployInfo employInfo = employInfoService.findEmployInfoById(Integer.parseInt(power_pid));
				if(null == employInfo)
					returnMap.put("employInfo", new EmployInfo());
				else
					returnMap.put("employInfo", employInfo);
			}
			returnMap.put("employInfoList", employInfoService.employInfoList(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void employInfoSave() {
		HashMap returnMsg = new HashMap();
		try {
			EmployInfo employInfo = getModel(EmployInfo.class, "employInfo");
			employInfoService.employInfoSave(employInfo,this);
			returnMsg.put("content", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "保存失败");
		}
		renderJson(returnMsg);
	}
	
	public void employInfoDelete() {
		HashMap returnMsg = new HashMap();
		try {
			employInfoService.deleteEmployInfoById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
	
	public void employDiplomaSelect(){
		try {
			renderJson(dictionaryService.dictionaryByIdPath(","+PropKit.getInt("employ_speciality")+","));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 教育经历
	 */
	public void educationInfoSave() {
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			EducationInfo educationInfo = getModel(EducationInfo.class, "educationInfo");
			employInfoService.educationInfoSave(educationInfo);
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void educationInfoDelete() {
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			Integer id = getParaToInt("id");
			if(null != id) {
				employInfoService.educationInfoDelete(id);
				returnMap.put("returnState", "success");
				returnMap.put("returnMsg", "删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void educationInfoList() {
		List<EducationInfo> educationInfoList = null;
		try {
			String employId = employInfoService.getPara(this, "employ_id");
			if(StringUtils.isNoneBlank(employId)) {
				educationInfoList = employInfoService.educationInfoList(Integer.parseInt(employId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(educationInfoList);
	}
	
	/**
	 * 工作经历
	 */
	public void workInfoSave() {
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			WorkInfo workInfo = getModel(WorkInfo.class, "workInfo");
			employInfoService.workInfoSave(workInfo);
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void workInfoDelete() {
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			Integer id = getParaToInt("id");
			if(null != id) {
				employInfoService.workInfoDelete(id);
				returnMap.put("returnState", "success");
				returnMap.put("returnMsg", "删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void workInfoList() {
		List<WorkInfo> workInfoList = null;
		try {
			String employId = employInfoService.getPara(this, "employ_id");
			if(StringUtils.isNoneBlank(employId)) {
				workInfoList = employInfoService.workInfoList(Integer.parseInt(employId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(workInfoList);
	}

	/**
	 * 项目经验
	 */
	public void itemInfoSave() {
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			ItemInfo itemInfo = getModel(ItemInfo.class, "itemInfo");
			employInfoService.itemInfoSave(itemInfo);
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void itemInfoDelete() {
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			Integer id = getParaToInt("id");
			if(null != id) {
				employInfoService.itemInfoDelete(id);
				returnMap.put("returnState", "success");
				returnMap.put("returnMsg", "删除成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void itemInfoList() {
		List<ItemInfo> itemInfoList = null;
		try {
			String employId = employInfoService.getPara(this, "employ_id");
			if(StringUtils.isNoneBlank(employId)) {
				itemInfoList = employInfoService.itemInfoList(Integer.parseInt(employId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(itemInfoList);
	}
}