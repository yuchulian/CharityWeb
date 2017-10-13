package com.jlqr.controller.page;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.LoginInfoView;
import com.jlqr.common.model.PersonnelContract;
import com.jlqr.common.model.PersonnelContractView;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.DictionaryService;
import com.jlqr.service.EmployInfoService;
import com.jlqr.service.PersonnelContractService;

public class PersonnelContractPage extends ControllerUtil {
	@NewService("PersonnelContractService")
	PersonnelContractService  personnelContractService;
	@NewService("DictionaryService")
	DictionaryService dictionaryService;
	@NewService("EmployInfoService")
	EmployInfoService employInfoService;
	public void index(){
		List<Dictionary> contractTypeList = null;
		try {
			contractTypeList = dictionaryService.dictionaryByPid(PropKit.getInt("contract_type_id"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAttr("contractTypeList", contractTypeList);
		render("personnelContractIndex.html");
	}
	public void personnelContractEdit(){
		PersonnelContractView personnelContract = new PersonnelContractView();
		List<Dictionary> contractTypeList = null;
		EmployInfo  employInfo = null;
		try {
			contractTypeList = dictionaryService.dictionaryByPid(PropKit.getInt("contract_type_id"));
			if(StringUtils.isNotBlank(getPara("id"))) {
				personnelContract = personnelContractService.personnelContractViewById(Integer.parseInt(getPara("id")));
				employInfo = employInfoService.findEmployInfoById(personnelContract.getInt("employ_id"));
			}else{
				employInfo = new EmployInfo();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("contractTypeList",contractTypeList);
		setAttr("personnelContract", personnelContract);
		setAttr("employInfo", employInfo);
	}
	public void employNameSelectIndex(){
		render("employNameSelectIndex.html");
	}
	
}
