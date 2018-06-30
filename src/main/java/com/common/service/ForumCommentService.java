package com.common.service;

import java.util.Date;

import com.admin.common.ServiceUtil;
import com.admin.common.model.ForumComment;
import com.admin.common.model.UserInfo;

public class ForumCommentService extends ServiceUtil {

	public void CommentSave(ForumComment forumComment, UserInfo loginInfo) throws Exception {
		if(forumComment.getId()==null){
			int id = getMaxColumn(ForumComment.class,"id")+1;
			forumComment.setId(id);
			forumComment.setForumDiscusstime(new Date());
			forumComment.setIsdisplay(1);
			forumComment.setUserId(loginInfo.getId());
			forumComment.save();
		}else{
			forumComment.update();
		}
		
	}

}
