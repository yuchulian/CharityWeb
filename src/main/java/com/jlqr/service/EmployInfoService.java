package com.jlqr.service;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.LoginInfo;

public class EmployInfoService extends ServiceUtil {
	public Page<EmployView> employInfoPaginate(Controller controller) throws Exception {
		return this.paginate(EmployView.class, controller);
	}
	public List<EmployInfo> employInfoList(Controller controller) throws Exception {
		return this.list(EmployInfo.class, controller);
	}
	public EmployInfo findEmployInfoById(Integer id) throws Exception {
		return EmployInfo.dao.findById(id);
	}
	public List<EmployInfo> findEmployInfoListByPId(Integer power_pid) throws Exception {
		return EmployInfo.dao.find("select * from power_info where power_pid = "+power_pid);
	}
	public void employInfoSave(EmployInfo employInfo) throws Exception {
		if(null == employInfo.getId()) {
			int id = getMaxColumn(EmployInfo.class, "id") + 1;
			if(id==1){
				id = id+1;
			}
			employInfo.setId(id);
			employInfo.save();
		} else {
			employInfo.update();
		}
	}
	
	public void employInfoSave(EmployInfo employInfo,Controller controller) throws Exception {
		String idPath = "";	
		EmployInfo session_employInfo = controller.getSessionAttr("employInfo");
		LoginInfo session_loginInfo = controller.getSessionAttr("loginInfo");
		if(null == employInfo.getId()) {
			int id = getMaxColumn(EmployInfo.class, "id") + 1;
			if(id==1){
				id = id+1;
			}
			if(session_employInfo==null){
				idPath = ","+ session_loginInfo.getId()+","+id+",";
			}else{
				idPath = session_employInfo.getEmployIdPath()+id+",";
			}
			employInfo.setId(id);
			employInfo.setEmployIdPath(idPath);
			employInfo.save();
		} else {
			employInfo.update();
		}
	}
	
	public void deleteEmployInfoById(Integer employInfoId) throws Exception {
		EmployInfo.dao.deleteById(employInfoId);
		//进行查询用户是不是已经开通权限,要是开通权限进行删除权限
		List<LoginInfo> findLoginInfolist = LoginInfo.dao.find("select * from login_info where id = ?",employInfoId);
		if(findLoginInfolist!=null&&findLoginInfolist.size()>0){
			LoginInfo.dao.deleteById(findLoginInfolist.get(0).getId());
		}
		/*EmployInfo employInfo = this.findEmployInfoById(employInfoId);
		if(null != employInfo) {
			Db.update("delete from power_info where power_id_path like '"+employInfo.getPowerIdPath()+"%'");
		}*/
	}
	

	
}
