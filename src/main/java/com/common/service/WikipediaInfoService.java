package com.common.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.FileUtil;
import com.admin.common.ServiceUtil;
import com.admin.common.model.WikipediaInfo;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class WikipediaInfoService extends ServiceUtil {
	public Page<WikipediaInfo> wikipediaInfoPaginate(Controller controller) throws Exception {
		return this.paginate(WikipediaInfo.class, controller);	
	}

	public WikipediaInfo findwikipediaInfoById(Integer id) {		
		return WikipediaInfo.dao.findById(id);
	}

	public void wikipediaInfoSave(WikipediaInfo wikipediaInfo, Controller controller) throws Exception {
		if(StringUtils.defaultString(wikipediaInfo.getWikipediaImg(), "").indexOf("/temp/") > -1) {
			wikipediaInfo.setWikipediaImg(FileUtil.cut(wikipediaInfo.getWikipediaImg(), PropKit.get("img")));
		}
		if(null == wikipediaInfo.getId()) {
			wikipediaInfo.setId(getMaxColumn(WikipediaInfo.class, "id") + 1);
			wikipediaInfo.setWikipediaIstop(0);
			wikipediaInfo.setWikipediaState(1);
			wikipediaInfo.setWikipediaOpenSize(0);
			wikipediaInfo.save();
		} else {
			wikipediaInfo.setWikipediaState(1);
			wikipediaInfo.update();
		}
		
	}

	public void wikipediaInfoDelete(Integer id) throws Exception{
		WikipediaInfo.dao.deleteById(id);
		
	}

	public void wikipediaInfoToTop(Integer id) {
		WikipediaInfo WikipediaInfo = new WikipediaInfo();
		WikipediaInfo.setId(id);
		WikipediaInfo.setWikipediaIstop(1);
		WikipediaInfo.update();	
	}

	public void wikipediaInfoCanceltop(Integer id) {
		WikipediaInfo WikipediaInfo = new WikipediaInfo();
		WikipediaInfo.setId(id);
		WikipediaInfo.setWikipediaIstop(0);
		WikipediaInfo.update();
		
	}

	public void wikipediaInfoCheckSuccess(Integer id) {
		WikipediaInfo WikipediaInfo = new WikipediaInfo();
		WikipediaInfo.setId(id);
		WikipediaInfo.setWikipediaState(2);
		WikipediaInfo.update();
	}

	public void wikipediaInfoCheckFail(Integer id) {
		WikipediaInfo WikipediaInfo = new WikipediaInfo();
		WikipediaInfo.setId(id);
		WikipediaInfo.setWikipediaState(3);
		WikipediaInfo.update();	
	}

	public List<WikipediaInfo> getTopSevenWikipediaInfo() {
		return WikipediaInfo.dao.find("SELECT * from wikipedia_info ORDER BY wikipedia_open_size desc LIMIT 0,7");
	}

	public WikipediaInfo findFristWikipediaInfo() {
		// TODO Auto-generated method stub
		return WikipediaInfo.dao.findFirst("SELECT * from wikipedia_info ORDER BY wikipedia_open_size desc LIMIT 0,1");
	}
}