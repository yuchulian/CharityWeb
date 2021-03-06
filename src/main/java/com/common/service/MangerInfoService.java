package com.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.FileUtil;
import com.admin.common.ServiceUtil;
import com.admin.common.model.EducationInfo;
import com.admin.common.model.ItemInfo;
import com.admin.common.model.LoginInfo;
import com.admin.common.model.LoginInfoView;
import com.admin.common.model.MangerInfo;
import com.admin.common.model.MangerInfoView;
import com.admin.common.model.RoleInfo;
import com.admin.common.model.WorkInfo;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class MangerInfoService extends ServiceUtil {
	
	public Page<MangerInfoView> mangerInfoPaginate(Controller controller) throws Exception {
		return this.paginate(MangerInfoView.class, controller);
	}
	
	public LoginInfoView findMangerViewById(Integer id) {
		return LoginInfoView.dao.findById(id);
	}
	
	public List<MangerInfo> mangerInfoList(Controller controller) throws Exception {
		return this.list(MangerInfo.class, controller);
	}
	
	public MangerInfo findMangerInfoById(Integer id) throws Exception {
		return MangerInfo.dao.findById(id);
	}
	
	public List<MangerInfo> findMangerInfoListByPId(Integer power_pid) throws Exception {
		return MangerInfo.dao.find("select * from power_info where power_pid = "+power_pid);
	}
	
	public void mangerInfoSave(MangerInfo mangerInfo) throws Exception {
		if(null == mangerInfo.getId()) {
			int id = getMaxColumn(MangerInfo.class, "id") + 1;
			if(id==1){
				id = id+1;
			}
			mangerInfo.setId(id);
			mangerInfo.save();
		} else {
			mangerInfo.update();
		}
	}
	
	public void mangerInfoSave(MangerInfo mangerInfo,Controller controller) throws Exception {
		if(StringUtils.defaultString(mangerInfo.getMangerImg(), "").indexOf("/temp/") > -1) {
			mangerInfo.setMangerImg(FileUtil.cut(mangerInfo.getMangerImg(), PropKit.get("img")));
		}
		if(null == mangerInfo.getId()) {
			mangerInfo.setId(getMaxColumn(MangerInfo.class, "id") + 1);
			mangerInfo.save();
		} else {
			mangerInfo.update();
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
	
	public void deleteMangerInfoById(Integer mangerInfoId) throws Exception {
		MangerInfo.dao.deleteById(mangerInfoId);
		//进行查询用户是不是已经开通权限,要是开通权限进行删除权限
		List<LoginInfo> findLoginInfolist = LoginInfo.dao.find("select * from login_info where id = ?",mangerInfoId);
		if(findLoginInfolist!=null&&findLoginInfolist.size()>0){
			LoginInfo.dao.deleteById(findLoginInfolist.get(0).getId());
		}
		/*EmployInfo employInfo = this.findEmployInfoById(employInfoId);
		if(null != employInfo) {
			Db.update("delete from power_info where power_id_path like '"+employInfo.getPowerIdPath()+"%'");
		}*/
	}
	
	/**
	 * 根据部门、角色等级和报销金额获取适合审核费用报销的上级领导
	 */
	public List<LoginInfoView> findLeaderList(LoginInfoView loginInfoView, float reimburseTotal) throws Exception {
		return findEmployViewList("findLeaderList", loginInfoView, reimburseTotal);
	}
	
	/**
	 * 根据角色等级和部门获取我的上级
	 */
	public List<LoginInfoView> findLeaderList(LoginInfoView loginInfoView) throws Exception {
		return findEmployViewList("findLeaderList", loginInfoView, 0);
	}
	
	/**
	 * 根据角色等级和部门获取我的下级
	 */
	public List<LoginInfoView> findStaffList(LoginInfoView loginInfoView) throws Exception {
		return findEmployViewList("findStaffList", loginInfoView, 0);
	}
	
	/**
	 * 根据部门、角色等级和报销金额获取适合审核费用报销的上级领导或下级用户
	 * @param loginInfoView
	 * @param roleInfoListSql
	 * @return
	 * @throws Exception
	 */
	private List<LoginInfoView> findEmployViewList(String method, LoginInfoView loginInfoView, float reimburseTotal) throws Exception {

		List<LoginInfoView> loginInfoViewList = new ArrayList<LoginInfoView>();
		List<String> roleIdCondition = new ArrayList<String>(), departmentIdCondition = new ArrayList<String>();
		
		String departmentId = loginInfoView.getDepartmentId();
		if(StringUtils.isNotBlank(departmentId) && departmentId.length() > 2) {
			String[] departmentIdList = departmentId.substring(1, departmentId.length() - 1).split("\\,");
			for (String _departmentId : departmentIdList) {
				departmentIdCondition.add("department_id like '%,"+_departmentId+",%'");
			}
		}
		
		String roleId = loginInfoView.getRoleId();
		if(StringUtils.isNotBlank(roleId) && roleId.length() > 2) {
			//获取当前登录人最高等级的角色
			RoleInfo loginRoleInfo = RoleInfo.dao.findFirst("select * from role_info where id in ("+roleId.substring(1, roleId.length() - 1)+") order by role_grade asc");
			
			if(null != loginRoleInfo) {
				//获取我的下级的角色
				List<RoleInfo> roleInfoList = null;
				if(StringUtils.equals("findLeaderList", method)) {
					if(reimburseTotal > 0) {
						Integer roleGrade = 3;
						if(reimburseTotal >= 1000)
							roleGrade = 2;
						roleInfoList = RoleInfo.dao.find("select * from role_info where role_grade = ?", roleGrade);
					} else {
						roleInfoList = RoleInfo.dao.find("select * from role_info where role_grade = ?", loginRoleInfo.getRoleGrade() - 1);
					}
				} else if(StringUtils.equals("findStaffList", method)) {
					roleInfoList = RoleInfo.dao.find("select * from role_info where role_grade > ?", loginRoleInfo.getRoleGrade() + 1);
				}
				if(null != roleInfoList) {
					//获取我的下级用户
					for (RoleInfo roleInfo : roleInfoList) {
						roleIdCondition.add("role_id like '%,"+roleInfo.getId()+",%'");
					}
				}
			}
		}
		
		if(roleIdCondition.size() > 0 && departmentIdCondition.size() > 0) {
			loginInfoViewList = LoginInfoView.dao.find("select * from login_Info_view where ("+StringUtils.join(roleIdCondition, " or ")+") and ("+StringUtils.join(departmentIdCondition, " or ")+")");
			if(null == loginInfoViewList)
				loginInfoViewList = new ArrayList<LoginInfoView>();
		}
		
		return loginInfoViewList;
	
	}
	
	public MangerInfoView findMangerInfoViewById(Integer id) {
		return MangerInfoView.dao.findById(id);
	}
	
	/**
	 * 教育经历
	 */
	public EducationInfo educationInfoById(Integer id) throws Exception {
		return EducationInfo.dao.findById(id);
	}
	
	public void educationInfoSave(EducationInfo educationInfo) throws Exception {
		if(null == educationInfo.getId()) {
			Integer id = getMaxColumn(EducationInfo.class, "id") + 1;
			educationInfo.setId(id);
			educationInfo.save();
		} else {
			educationInfo.update();
		}
	}
	
	public void educationInfoDelete(Integer id) throws Exception {
		EducationInfo.dao.deleteById(id);
	}
	
	public List<EducationInfo> educationInfoList(Integer employId) throws Exception {
		return EducationInfo.dao.find("select * from education_info where manger_id = ?", employId);
	}
	
	/**
	 * 工作经历
	 */
	public WorkInfo workInfoById(Integer id) throws Exception {
		return WorkInfo.dao.findById(id);
	}
	
	public void workInfoSave(WorkInfo workInfo) throws Exception {
		if(null == workInfo.getId()) {
			Integer id = getMaxColumn(WorkInfo.class, "id") + 1;
			workInfo.setId(id);
			workInfo.save();
		} else {
			workInfo.update();
		}
	}
	
	public void workInfoDelete(Integer id) throws Exception {
		WorkInfo.dao.deleteById(id);
	}
	
	public List<WorkInfo> workInfoList(Integer employId) throws Exception {
		return WorkInfo.dao.find("select * from work_info where manger_id = ?", employId);
	}

	/**
	 * 项目经验
	 */
	public ItemInfo itemInfoById(Integer id) throws Exception {
		return ItemInfo.dao.findById(id);
	}
	
	public void itemInfoSave(ItemInfo itemInfo) throws Exception {
		if(null == itemInfo.getId()) {
			Integer id = getMaxColumn(ItemInfo.class, "id") + 1;
			itemInfo.setId(id);
			itemInfo.save();
		} else {
			itemInfo.update();
		}
	}
	
	public void itemInfoDelete(Integer id) throws Exception {
		ItemInfo.dao.deleteById(id);
	}
	
	public List<ItemInfo> itemInfoList(Integer employId) throws Exception {
		return ItemInfo.dao.find("select * from item_info where manger_id = ?", employId);
	}

	public List<MangerInfo> findAllMangerInfo() {
		// TODO Auto-generated method stub
		return MangerInfo.dao.find("select * from manger_info");
	}
}
