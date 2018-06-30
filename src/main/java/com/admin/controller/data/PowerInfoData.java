package com.admin.controller.data;

import java.util.HashMap;

import com.admin.common.ControllerUtil;
import com.admin.common.model.PowerInfo;
import com.admin.interceptor.NewService;
import com.common.service.PowerInfoService;

public class PowerInfoData extends ControllerUtil {

	@NewService("PowerInfoService")
	private PowerInfoService powerInfoService;
	
	public void powerInfoList() {
		HashMap returnMap = new HashMap();
		try {
			String power_pid = powerInfoService.getPara(this, "power_pid", "0");
			if("0".equals(power_pid)) {
				returnMap.put("powerInfo", new PowerInfo());
			} else {
				PowerInfo powerInfo = powerInfoService.findPowerInfoById(Integer.parseInt(power_pid));
				if(null == powerInfo)
					returnMap.put("powerInfo", new PowerInfo());
				else
					returnMap.put("powerInfo", powerInfo);
			}
			returnMap.put("powerInfoList", powerInfoService.powerInfoList(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void powerInfoSave() {
		HashMap returnMsg = new HashMap();
		try {
			PowerInfo powerInfo = getModel(PowerInfo.class, "powerInfo");
			powerInfoService.powerInfoSave(powerInfo);
			returnMsg.put("content", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "保存失败");
		}
		renderJson(returnMsg);
	}
	
	public void powerInfoDelete() {
		HashMap returnMsg = new HashMap();
		try {
			powerInfoService.deletePowerInfoById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
	
}


