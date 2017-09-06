package com.jlqr.controller.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
	}
	//编辑员工账号信息
	public void employViewEdit(){
		String id = getPara("id");
		System.out.println(id);
		LoginInfo loginInfo = new LoginInfo();
		List<LoginInfo> findLoginInfo = loginInfoService.findLoginInfoById(Integer.parseInt(id));
		if(findLoginInfo!=null&&findLoginInfo.size()>0){
			loginInfo = findLoginInfo.get(0);
		}
		setAttr("loginInfo", loginInfo);
	}
	//进行保存登录信息
	public void employViewSave(){
		HashMap<String,String> returnMsg = new HashMap<String, String>();
		LoginInfo model = getModel(LoginInfo.class,"loginInfo");
		EmployInfo employInfo = new EmployInfo();
		try {
			loginInfoService.updateLoginInfo(model);
			employInfo = employInfoService.findEmployInfoById(model.getId());
			employInfo.setEmployName(model.getLoginName());
			employInfoService.employInfoSave(employInfo);
			returnMsg.put("content", "编辑成功");
		} catch (Exception e) {
			// TODO: handle exception
			returnMsg.put("content", "编辑失败");
		}
		renderJson(returnMsg);
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


