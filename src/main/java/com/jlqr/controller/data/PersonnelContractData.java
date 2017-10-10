package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.PersonnelContract;
import com.jlqr.common.model.ProjectInfo;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.PersonnelContractService;

public class PersonnelContractData extends ControllerUtil {
	@NewService("PersonnelContractService")
	PersonnelContractService  personnelContractService;
	
	public void personnelContractList(){
		try {
			renderJson(personnelContractService.personnelContractPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void personnelContractSave(){
		PersonnelContract personnelContract = getModel(PersonnelContract.class,"personnelContract");
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			personnelContractService.personnelContractSave(personnelContract);
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	public void personnelContractDelete(){
		HashMap<String,Object> returnMap = getReturnMap();
		try {
			personnelContractService.personnelContractDelete(getParaToInt("id"));
			returnMap.put("returnState", "success");
			returnMap.put("returnMsg", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
}
