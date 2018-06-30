package com.common.service;

import java.util.Date;
import java.util.List;

import org.activiti.engine.delegate.event.ActivitiEventType;
import org.apache.commons.lang.StringUtils;

import com.admin.common.ServiceUtil;
import com.admin.common.model.ActivityInfo;
import com.admin.common.model.ActivityInfoView;
import com.admin.common.model.ActivityUnit;
import com.admin.common.model.AttendInfo;
import com.admin.common.model.AttendUserInfoView;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class ActivityInfoService extends ServiceUtil {

	public Page<ActivityUnit> activityUnitPaginate(Controller controller) throws Exception {
		return this.paginate(ActivityUnit.class, controller);	
	}

	public ActivityUnit findActivityUnitById(Integer id) {
		return ActivityUnit.dao.findById(id);	
	}

	public void activityUnitSave(ActivityUnit activityUnit) throws Exception {
		if(activityUnit.getId()==null){
			Integer id = getMaxColumn(ActivityUnit.class,"id")+1;
			activityUnit.setId(id);
			activityUnit.setUnitPostFailNumber(0);
			activityUnit.setUnitPostSuccessNumber(0);
			activityUnit.setUnitPostNumber(0);
			activityUnit.setUnitOrgin(PropKit.get("company"));
			activityUnit.save();
		}else{
			activityUnit.update();
		}
	}

	public void activityUnitDelete(Integer id) {
		ActivityUnit.dao.deleteById(id);
	}

	public Page<ActivityInfoView> activityInfoPaginate(Controller controller) throws Exception {
		return this.paginate(ActivityInfoView.class, controller);
	}

	public ActivityInfo findActivityInfoById(Integer id) {
		return ActivityInfo.dao.findById(id);
	}

	public List<ActivityUnit> findActivityUnitByOrgin(String orgin) {
		
		return ActivityUnit.dao.find("select * from activity_unit where unit_orgin =?",orgin);
	}

	public void activityInfoSave(ActivityInfo activityInfo) throws Exception {
		if(activityInfo.getId()==null){
			int id = getMaxColumn(ActivityInfo.class,"id")+1;
			activityInfo.setActivityState(0);
			activityInfo.setActivityEnrolTotal(0);
			activityInfo.setId(id);
			activityInfo.setActivityChangeTime(new Date());
			activityInfo.setActivityAttendNum(0);
			activityInfo.save();
		}else {
			activityInfo.setActivityCheckState(0);
			activityInfo.setActivityChangeTime(new Date());
			activityInfo.update();
		}
		
	}

	public void activityInfoDelete(Integer id) {
		ActivityInfo.dao.deleteById(id);
	}

	public void activityInfoRecomend(String id) {
		if(StringUtils.isNotBlank(id)){
			ActivityInfo activityInfo = new ActivityInfo();
			activityInfo = ActivityInfo.dao.findById(id);
			if(activityInfo!=null){
				int is_recomend = activityInfo.getActivityIsRecomened();
				if(is_recomend==1){
					activityInfo.setActivityIsRecomened(0);
				}else if(is_recomend==0){
					activityInfo.setActivityIsRecomened(1);
				}
				activityInfo.update();
			}
		}
	}
	//查询首页推荐活动
	public List<ActivityInfoView> findTopRecomendActivity() {
		return ActivityInfoView.dao.find("SELECT * from activity_info_view WHERE activity_is_recomened = 1 and activity_check_state = 1 ORDER BY id DESC LIMIT 0,2");
	}
	//通过活动类型查询活动
	public List<ActivityInfoView> findActivityInfoByType(Integer type) {
		return ActivityInfoView.dao.find("SELECT * from activity_info_view WHERE activity_type = ? ORDER BY id DESC LIMIT 0,6",type);
	}

	public ActivityInfoView findActivityInfoViewById(Integer id) {
		if(id!=null){
			return ActivityInfoView.dao.findById(id);
		}
		return null;
	}
	//首页最新活动
	public List<ActivityInfoView> findActivityInfoNew() {
		return ActivityInfoView.dao.find("select * from activity_info where activity_check_state = 1 order by id desc limit 0,2");
	}
	//首页项目推荐
	public List<ActivityInfoView> findActivityInfoRecomen() {
		return ActivityInfoView.dao.find("select * from activity_info WHERE activity_is_recomened=1 and activity_check_state = 1 order by id desc limit 0,3");
	}


	public void activityInfoCheckFail(String id) {
		if(id!=null&id.length()>0){
			ActivityInfo activityInfo = ActivityInfo.dao.findById(id);
			if(activityInfo!=null){
				activityInfo.setActivityCheckState(1);
				activityInfo.update();
			}
		}
		
	}

	public void activityInfoChecksFail(String id) {
		if(id!=null&id.length()>0){
			ActivityInfo activityInfo = ActivityInfo.dao.findById(id);
			if(activityInfo!=null){
				activityInfo.setActivityCheckState(2);
				activityInfo.update();
			}
		}
		
	}

	public AttendInfo findAttendInfoByUserIdAndActId(Integer userId, Integer actId) {
		return AttendInfo.dao.findFirst("SELECT * from attend_info where user_id = ? and activity_id = ?",userId,actId);		
	}

	public void activityAttendSave(AttendInfo userAttend) throws Exception {
		if(userAttend.getId()==null){
			Integer id = getMaxColumn(AttendInfo.class,"id")+1;
			userAttend.setId(id);
			userAttend.save();
		}else{
			userAttend.update();
		}
		
	}

	public List<AttendInfo> findAttendInfoListById(Integer id) {		
		return AttendInfo.dao.find("SELECT * from attend_info WHERE activity_id = ?",id);
	}

	public Page<AttendUserInfoView> attendUserInfoPaginate(Controller controller) throws Exception {
		return this.paginate(AttendUserInfoView.class, controller);
	}
}
