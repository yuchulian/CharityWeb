package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.service.EmployInfoService;

public class EmployInfoData extends ControllerUtil {
	
	private EmployInfoService employInfoService = new EmployInfoService();
	
	public void employInfoPaginate() {
		try {
			renderJson(employInfoService.employInfoPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			employInfoService.deleteEmployInfoById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
	
}


