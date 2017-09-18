package com.jlqr.controller.data;

import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.LoginInfoService;

public class EmployInfoData extends ControllerUtil {

	@NewService("EmployInfoService")
	private EmployInfoService employInfoService;
	
	@NewService("LoginInfoService")
	private LoginInfoService loginInfoService;
	
	public void employInfoPaginate() {
		try {
			System.out.println(employInfoService.employInfoPaginate(this));
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
	
}


