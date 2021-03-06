package com.admin.controller.page;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.ControllerUtil;
import com.admin.common.model.Dictionary;
import com.admin.common.model.PowerInfo;
import com.admin.common.model.RoleInfo;
import com.admin.interceptor.NewService;
import com.common.service.DictionaryService;
import com.common.service.PowerInfoService;
import com.common.service.RoleInfoService;
import com.jfinal.kit.JsonKit;
import com.jfinal.kit.PropKit;

public class RoleInfoPage extends ControllerUtil {
	
	@NewService("RoleInfoService")
	private RoleInfoService roleService;
	
	@NewService("PowerInfoService")
	private PowerInfoService powerInfoService;
	
	@NewService("DictionaryService")
	private DictionaryService dictionaryService;
	
	public void index() {
		render("roleInfoIndex.html");
	}
	
	public void roleInfoEdit() {
		RoleInfo roleInfo = new RoleInfo();
		List<Dictionary> roleGrade = null;
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				roleInfo = roleService.findRoleById(Integer.parseInt(getPara("id")));
			}
			roleGrade = dictionaryService.findDictionaryListByPId(PropKit.getInt("roleGrade"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("roleInfo", roleInfo);
		setAttr("roleGrade", roleGrade);
	}
	
	public void roleInfoPower() {
//		List<PowerInfo> powerInfoList = null;
//		try {
//			powerInfoList = powerInfoService.powerInfoList(this);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if(null == powerInfoList)
//			powerInfoList = new ArrayList<PowerInfo>();
		RoleInfo roleInfo = new RoleInfo();
		try {
			roleInfo = roleService.findRoleById(getParaToInt("id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<PowerInfo> powerInfoList = getSessionAttr("powerInfoList");
		setAttr("powerInfoList", JsonKit.toJson(powerInfoList));
		setAttr("roleInfo", roleInfo);
		
		/**
		 * 重做
			RoleInfo roleInfo = new RoleInfo();
			try {
				//获取拥有ip的权限
				String powerInfoIds = roleService.getPowerInfoIdsByUserRole((LoginInfo)getSessionAttr("loginInfo"),(RoleInfo)getSessionAttr("roleInfo"));
				List<PowerInfo> powers = powerInfoService.findAllByStateAndIds(1, powerInfoIds, "id,power_pid pid,power_name name");
				setAttr("powers", JsonKit.toJson(powers));
	
				if(StringUtils.isNotBlank(getPara("id"))) {
					roleInfo = roleService.findRoleById(Integer.parseInt(getPara("id")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			setAttr("roleInfo", roleInfo);
			setAttr("roleInfo", roleInfo);
			render("roleInfoPower.html");
		 */
	}
}
