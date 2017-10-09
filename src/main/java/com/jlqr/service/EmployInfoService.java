package com.jlqr.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.FileUtil;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.EmployInfo;
import com.jlqr.common.model.EmployInfoView;
import com.jlqr.common.model.EmployView;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.RoleInfo;

public class EmployInfoService extends ServiceUtil {
	
	public Page<EmployView> employInfoPaginate(Controller controller) throws Exception {
		return this.paginate(EmployView.class, controller);
	}
	
	public EmployView findEmployViewById(Integer id) {
		return EmployView.dao.findById(id);
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
		if(null != employInfo.getEmployImg() && employInfo.getEmployImg().indexOf("/temp/") > -1) {
			employInfo.setEmployImg(FileUtil.cut(employInfo.getEmployImg(), PropKit.get("img")));
		}
		if(null == employInfo.getId()) {
			employInfo.setId(getMaxColumn(EmployInfo.class, "id") + 1);
			employInfo.save();
		} else {
			employInfo.update();
		}
		/**
		 * 重做
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
		 */
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
	
	/**
	 * 根据角色等级和部门获取我的上级
	 */
	public List<EmployView> findLeaderList(EmployView employView) {
		List<EmployView> employViewList = new ArrayList<EmployView>();
		List<String> roleIdCondition = new ArrayList<String>(), departmentIdCondition = new ArrayList<String>();
		
		String departmentId = employView.getDepartmentId();
		if(StringUtils.isNotBlank(departmentId) && departmentId.length() > 2) {
			String[] departmentIdList = departmentId.substring(1, departmentId.length() - 1).split("\\,");
			for (String _departmentId : departmentIdList) {
				departmentIdCondition.add("department_id like '%,"+_departmentId+",%'");
			}
		}

		String roleId = employView.getRoleId();
		if(StringUtils.isNotBlank(roleId) && roleId.length() > 2) {
			//获取当前登录人最高等级的角色
			RoleInfo loginRoleInfo = RoleInfo.dao.findFirst("select * from role_info where id in ("+roleId.substring(1, roleId.length() - 1)+") order by role_grade desc");
			
			if(null != loginRoleInfo) {
				//获取我的上一级的角色
				List<RoleInfo> leaderRoleInfoList = RoleInfo.dao.find("select * from role_info where role_grade = ?", loginRoleInfo.getRoleGrade() - 1);
				if(null != leaderRoleInfoList) {
					//获取我的上一级领导
					for (RoleInfo roleInfo : leaderRoleInfoList) {
						roleIdCondition.add("role_id like '%,"+roleInfo.getId()+",%'");
					}
				}
			}
		}
		
		if(roleIdCondition.size() > 0 && departmentIdCondition.size() > 0) {
			employViewList = EmployView.dao.find("select * from employ_view where ("+StringUtils.join(roleIdCondition, " or ")+") and ("+StringUtils.join(departmentIdCondition, " or ")+")");
			if(null == employViewList)
				employViewList = new ArrayList<EmployView>();
		}
		
		return employViewList;
	}
	
	/**
	 * 根据角色等级和部门获取我的下级
	 */
	public List<EmployView> findStaffList(EmployView employView) {
		List<EmployView> employViewList = new ArrayList<EmployView>();
		List<String> roleIdCondition = new ArrayList<String>(), departmentIdCondition = new ArrayList<String>();
		
		String departmentId = employView.getDepartmentId();
		if(StringUtils.isNotBlank(departmentId) && departmentId.length() > 2) {
			String[] departmentIdList = departmentId.substring(1, departmentId.length() - 1).split("\\,");
			for (String _departmentId : departmentIdList) {
				departmentIdCondition.add("department_id like '%,"+_departmentId+",%'");
			}
		}
		
		String roleId = employView.getRoleId();
		if(StringUtils.isNotBlank(roleId) && roleId.length() > 2) {
			//获取当前登录人最高等级的角色
			RoleInfo loginRoleInfo = RoleInfo.dao.findFirst("select * from role_info where id in ("+roleId.substring(1, roleId.length() - 1)+") order by role_grade desc");
			
			if(null != loginRoleInfo) {
				//获取我的下级的角色
				List<RoleInfo> leaderRoleInfoList = RoleInfo.dao.find("select * from role_info where role_grade > ?", loginRoleInfo.getRoleGrade() + 1);
				if(null != leaderRoleInfoList) {
					//获取我的下级员工
					for (RoleInfo roleInfo : leaderRoleInfoList) {
						roleIdCondition.add("role_id like '%,"+roleInfo.getId()+",%'");
					}
				}
			}
		}
		
		if(roleIdCondition.size() > 0 && departmentIdCondition.size() > 0) {
			employViewList = EmployView.dao.find("select * from employ_view where ("+StringUtils.join(roleIdCondition, " or ")+") and ("+StringUtils.join(departmentIdCondition, " or ")+")");
			if(null == employViewList)
				employViewList = new ArrayList<EmployView>();
		}
		
		return employViewList;
	}

	public EmployInfoView findEmployInfoViewById(Integer id) {
		return EmployInfoView.dao.findById(id);
	}
	
}
