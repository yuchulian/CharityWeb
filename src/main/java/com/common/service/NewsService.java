package com.common.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.FileUtil;
import com.admin.common.ServiceUtil;
import com.admin.common.model.CarouseInfo;
import com.admin.common.model.NewInfo;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class NewsService extends ServiceUtil{

	public Page<NewInfo> newsInfoPaginate(Controller controller) throws Exception {
		return this.paginate(NewInfo.class, controller);	
	}

	public NewInfo findNewsInfoById(Integer id) {		
		return NewInfo.dao.findById(id);
	}

	public void newsInfoSave(NewInfo newsInfo, Controller controller) throws Exception {
		if(StringUtils.defaultString(newsInfo.getNewsImg(), "").indexOf("/temp/") > -1) {
			newsInfo.setNewsImg(FileUtil.cut(newsInfo.getNewsImg(), PropKit.get("img")));
		}
		if(null == newsInfo.getId()) {
			newsInfo.setId(getMaxColumn(NewInfo.class, "id") + 1);
			Date date = new Date();
			newsInfo.setNewsChangetime(date);
			newsInfo.setNewsState(1);
			newsInfo.setNewsIsTop(0);
			newsInfo.save();
		} else {
			newsInfo.setNewsState(1);
			newsInfo.update();
		}
		
	}

	public void newsInfoDelete(Integer id) throws Exception{
		NewInfo.dao.deleteById(id);
		
	}

	public void newsInfoToTop(Integer id) {
		NewInfo newInfo = new NewInfo();
		newInfo.setId(id);
		newInfo.setNewsIsTop(1);
		newInfo.update();	
	}

	public void newsInfoCanceltop(Integer id) {
		NewInfo newInfo = new NewInfo();
		newInfo.setId(id);
		newInfo.setNewsIsTop(0);
		newInfo.update();
		
	}

	public void newsInfoCheckSuccess(Integer id) {
		NewInfo newInfo = new NewInfo();
		newInfo.setId(id);
		newInfo.setNewsState(2);
		newInfo.update();
	}

	public void newsInfoCheckFail(Integer id) {
		NewInfo newInfo = new NewInfo();
		newInfo.setId(id);
		newInfo.setNewsState(3);
		newInfo.update();	
	}
	//得到最新的新闻 6条
	public List<NewInfo> getFirstNews() {
		 return NewInfo.dao.find("select * from new_info order by news_changetime desc limit 0,"+PropKit.get("firstNewSize"));
	}

	public List<NewInfo> findAllNews() {
		// TODO Auto-generated method stub
		return NewInfo.dao.find("select * from new_info");
	}

}
