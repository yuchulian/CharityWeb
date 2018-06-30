package com.admin.controller.data;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.admin.common.ControllerUtil;
import com.admin.common.model.EducationInfo;
import com.admin.common.model.ItemInfo;
import com.admin.common.model.LoginInfo;
import com.admin.common.model.LoginInfoView;
import com.admin.common.model.MangerInfo;
import com.admin.common.model.WorkInfo;
import com.admin.interceptor.NewService;
import com.common.service.DictionaryService;
import com.common.service.LoginInfoService;
import com.common.service.MangerInfoService;
import com.jfinal.kit.PropKit;

public class MangerInfoData extends ControllerUtil {

	@NewService("MangerInfoService")
	private MangerInfoService mangerInfoService;
	
	@NewService("LoginInfoService")
	private LoginInfoService loginInfoService;
	
	@NewService("DictionaryService")
	private DictionaryService dictionaryService;
	
	public void mangerInfoPaginate() {
		try {
			System.out.println(mangerInfoService.mangerInfoPaginate(this));
			renderJson(mangerInfoService.mangerInfoPaginate(this));
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
	public void mangerViewSave(){
		HashMap<String, Object> returnMap = getReturnMap();
		
		LoginInfo loginInfo = getModel(LoginInfo.class,"loginInfo");
		MangerInfo mangerInfo = getModel(MangerInfo.class,"mangerInfo");
		Integer id = mangerInfo.getId();
		if(StringUtils.isNotBlank(loginInfo.getLoginPwd())) {
			loginInfo.setLoginPwd(DigestUtils.md5Hex(loginInfo.getLoginPwd()));
		}
		if(null != loginInfo.getId())
			id = null;
		try {
			loginInfoService.loginInfoSave(loginInfo, id);
			
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void mangerInfoList() {
		HashMap returnMap = new HashMap();
		try {
			String power_pid = mangerInfoService.getPara(this, "power_pid", "0");
			if("0".equals(power_pid)) {
				returnMap.put("mangerInfo", new MangerInfo());
			} else {
				MangerInfo mangerInfo = mangerInfoService.findMangerInfoById(Integer.parseInt(power_pid));
				if(null == mangerInfo)
					returnMap.put("mangerInfo", new MangerInfo());
				else
					returnMap.put("mangerInfo", mangerInfo);
			}
			returnMap.put("mangerInfoList", mangerInfoService.mangerInfoList(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	//用户的信息查询
	public void mangerInfoListForContract(){
		try {
			renderJson(mangerInfoService.mangerInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void mangerInfoSave() {
		HashMap returnMsg = new HashMap();
		try {
			MangerInfo mangerInfo = getModel(MangerInfo.class, "mangerInfo");
			mangerInfoService.mangerInfoSave(mangerInfo,this);
			returnMsg.put("content", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "保存失败");
		}
		renderJson(returnMsg);
	}
	
	public void mangerInfoDelete() {
		HashMap returnMsg = new HashMap();
		try {
			mangerInfoService.deleteMangerInfoById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
	
	public void mangerDiplomaSelect(){
		try {
			renderJson(dictionaryService.dictionaryByIdPath(","+PropKit.getInt("manger_speciality")+","));
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
			mangerInfoService.educationInfoSave(educationInfo);
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
				mangerInfoService.educationInfoDelete(id);
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
			String employId = mangerInfoService.getPara(this, "manger_id");
			if(StringUtils.isNotBlank(employId)) {
				educationInfoList = mangerInfoService.educationInfoList(Integer.parseInt(employId));
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
			mangerInfoService.workInfoSave(workInfo);
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
				mangerInfoService.workInfoDelete(id);
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
			String employId = mangerInfoService.getPara(this, "manger_id");
			if(StringUtils.isNotBlank(employId)) {
				workInfoList = mangerInfoService.workInfoList(Integer.parseInt(employId));
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
			mangerInfoService.itemInfoSave(itemInfo);
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
				mangerInfoService.itemInfoDelete(id);
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
			String employId = mangerInfoService.getPara(this, "manger_id");
			if(StringUtils.isNotBlank(employId)) {
				itemInfoList = mangerInfoService.itemInfoList(Integer.parseInt(employId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(itemInfoList);
	}

	//账号设置
	public void loginInfoSave(){
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			LoginInfoView loginInfoView = getSessionAttr("loginInfoView");
			Integer loginId = loginInfoView.getId();
			LoginInfo _loginInfo = loginInfoService.findLoginInfoById(loginId);
			LoginInfo loginInfo = getModel(LoginInfo.class, "loginInfo");
			String oldPassword = getPara("oldPassword"), newPassword = getPara("newPassword");
			loginInfo.setId(loginId);
			if(StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
				if(StringUtils.equals(_loginInfo.getLoginPwd(), DigestUtils.md5Hex(oldPassword))) {
					loginInfo.setLoginPwd(DigestUtils.md5Hex(newPassword));
				} else {
					returnMap.put("returnMsg", "原始密码错误");
				}
			}
			loginInfoService.loginInfoSave(loginInfo, null);
			setSessionAttr("loginInfoView", mangerInfoService.findMangerViewById(loginId));
			if(StringUtils.equals((String)returnMap.get("returnMsg"), "操作失败")) {
				returnMap.put("returnMsg", "保存成功");
			}
			returnMap.put("returnState", "success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
}