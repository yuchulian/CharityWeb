package com.common.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.FileUtil;
import com.admin.common.ServiceUtil;
import com.admin.common.model.ForumManagerInfo;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class ForumManagerService extends ServiceUtil {
	public Page<ForumManagerInfo> forumManagerInfoPaginate(Controller controller) throws Exception {
		return this.paginate(ForumManagerInfo.class, controller);	
	}

	public ForumManagerInfo findForumManagerInfoById(Integer id) {		
		return ForumManagerInfo.dao.findById(id);
	}

	public void forumManagerInfoSave(ForumManagerInfo forumManagerInfo, Controller controller) throws Exception {
		if(null == forumManagerInfo.getId()) {
			forumManagerInfo.setId(getMaxColumn(ForumManagerInfo.class, "id") + 1);
			forumManagerInfo.setManagerState(1);
			forumManagerInfo.save();
		} else {
			forumManagerInfo.setManagerState(1);
			forumManagerInfo.update();
		}
		
	}

	public void forumManagerInfoDelete(Integer id) throws Exception{
		ForumManagerInfo.dao.deleteById(id);
		
	}

	public void forumManagerInfoCheckSuccess(Integer id) {
		ForumManagerInfo forumManagerInfo = new ForumManagerInfo();
		forumManagerInfo.setId(id);
		forumManagerInfo.setManagerState(2);
		forumManagerInfo.update();
	}

	public void forumManagerInfoCheckFail(Integer id) {
		ForumManagerInfo forumManagerInfo = new ForumManagerInfo();
		forumManagerInfo.setId(id);
		forumManagerInfo.setManagerState(3);
		forumManagerInfo.update();	
	}

	public List<ForumManagerInfo> getTopSevenForumManagerInfo() {
		return ForumManagerInfo.dao.find("SELECT * from wikipedia_info ORDER BY wikipedia_open_size desc LIMIT 0,7");
	}

	public List<ForumManagerInfo> findTopNoice() {
		return ForumManagerInfo.dao.find("SELECT * from forum_manager_info WHERE manager_type=1 ORDER BY manager_create_time DESC LIMIT 0,4");
	}

	public List<ForumManagerInfo> findTopFriendLink() {
		return ForumManagerInfo.dao.find("SELECT * from forum_manager_info WHERE manager_type=3 ORDER BY manager_create_time DESC LIMIT 0,4");
	}

	public List<ForumManagerInfo> findTopForumHelp() {
		// TODO Auto-generated method stub
		return ForumManagerInfo.dao.find("SELECT * from forum_manager_info WHERE manager_type=2 ORDER BY manager_create_time DESC LIMIT 0,4");
	}
}
