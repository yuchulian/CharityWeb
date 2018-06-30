package com.common.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.ServiceUtil;
import com.admin.common.model.ForumChannel;
import com.admin.common.model.ForumCommentView;
import com.admin.common.model.ForumInfo;
import com.admin.common.model.ForumInfoView;
import com.admin.common.model.UserInfo;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;

public class ForumInfoService extends ServiceUtil {

	public void saveForumInfo(ForumInfo forumInfo, UserInfo userInfo) throws Exception {
		//只能保存
		Integer id = getMaxColumn(ForumInfo.class,"id")+1;
		forumInfo.setId(id);
		forumInfo.setForumCreateTime(new Date());
		forumInfo.setUserId(userInfo.getId());
		//进行更新大类别中帖子的个数
		if(forumInfo.getForumBigType()!=null){
			ForumChannel forumBigChannel = ForumChannel.dao.findById(forumInfo.getForumBigType());
			forumBigChannel.setChannel_topics(forumBigChannel.getChannel_topics()+1);
			forumBigChannel.update();
		}
		if(forumInfo.getForumSmallType()!=null){
			ForumChannel forumSmallChannel = ForumChannel.dao.findById(forumInfo.getForumSmallType());
			forumSmallChannel.setChannel_topics(forumSmallChannel.getChannel_topics()+1);
			forumSmallChannel.update();
		}
		//减少用户积分
		userInfo.setUserScore(userInfo.getUserScore()-forumInfo.getForumScore());
		forumInfo.save();
		userInfo.update();
	}

	public List<ForumInfoView> findHotForumViewTop() {
		//查询最新的10条新闻
		return ForumInfoView.dao.find("select * from forum_info_view order by forum_open_count desc limit 0,10");
	}

	public List<ForumInfoView> findFineForumViewTop() {
		//查询前10条新闻信息
		return ForumInfoView.dao.find("select * from forum_info_view where forum_fine_post = ? order by forum_create_time desc limit 0,10",1);
	}

	public List<ForumInfoView> findNewForumViewTop() {
		// 查询10条新帖
		return ForumInfoView.dao.find("select * from forum_info_view  order by forum_create_time desc limit 0,10");
	}

	public ForumInfoView findForumInfoViewById(int id) {
		return ForumInfoView.dao.findById(id);
	}

	public List<ForumCommentView> ForumCommentList(Controller controller) throws Exception {
		
		return this.list(ForumCommentView.class, controller);
	}

	public Page<ForumInfoView> forumInfoPaginate(Controller controller) throws Exception {
		return this.paginate(ForumInfoView.class, controller);
	}


}
