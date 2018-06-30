package com.common.service;

import java.util.List;

import com.admin.common.ServiceUtil;
import com.admin.common.model.ActivityComementInfo;
import com.admin.common.model.ActivityCommentView;

public class ActivityCommentService extends ServiceUtil {

	public void saveActivityComment(ActivityComementInfo activityComementInfo) throws Exception {
		if(activityComementInfo.getId()==null){
			Integer id = getMaxColumn(ActivityComementInfo.class,"id")+1;
			activityComementInfo.setId(id);
			activityComementInfo.save();
		}else{
			activityComementInfo.update();
		}
	}


	public List<ActivityCommentView> findActivityCommentViewListByActId(Integer actId) {
		return ActivityCommentView.dao.find("SELECT * from activity_comment_view acv where acv.activity_id = ? order by acv.content_time desc",actId);
	}

}
