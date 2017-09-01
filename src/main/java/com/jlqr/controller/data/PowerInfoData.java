package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.PowerInfo;
import com.jlqr.service.PowerInfoService;

public class PowerInfoData extends ControllerUtil {
	
	private PowerInfoService powerInfoService = new PowerInfoService();
	
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


