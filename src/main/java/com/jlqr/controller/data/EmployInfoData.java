package com.jlqr.controller.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.jfinal.kit.PropKit;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.RoleInfo;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.LoginInfoService;

public class EmployInfoData extends ControllerUtil {
	
	private EmployInfoService employInfoService = new EmployInfoService();
	private LoginInfoService loginInfoService = new LoginInfoService();
	
	public void employInfoPaginate() {
		try {
			System.out.println(employInfoService.employInfoPaginate(this));
			renderJson(employInfoService.employInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//开通账号
	public void employOpenAccount(){
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
			employInfoService.employInfoSave(employInfo);
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
			System.out.println("输出:"+getParaToInt("id"));
			employInfoService.deleteEmployInfoById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
	
}


