package com.common.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.FileUtil;
import com.admin.common.ServiceUtil;
import com.admin.common.model.PublicManInfo;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class PublicManInfoService extends ServiceUtil {
	public Page<PublicManInfo> publicManInfoPaginate(Controller controller) throws Exception {
		return this.paginate(PublicManInfo.class, controller);	
	}

	public PublicManInfo findpublicManInfoById(Integer id) {		
		return PublicManInfo.dao.findById(id);
	}

	public void publicManInfoSave(PublicManInfo publicManInfo, Controller controller) throws Exception {
		if(StringUtils.defaultString(publicManInfo.getPublicImg(), "").indexOf("/temp/") > -1) {
			publicManInfo.setPublicImg(FileUtil.cut(publicManInfo.getPublicImg(), PropKit.get("img")));
		}
		if(null == publicManInfo.getId()) {
			publicManInfo.setId(getMaxColumn(PublicManInfo.class, "id") + 1);
			publicManInfo.setPublicIstop(0);
			publicManInfo.setPublicState(1);
			publicManInfo.setPublicOpenSize(0);
			publicManInfo.save();
		} else {
			publicManInfo.setPublicState(1);
			publicManInfo.update();
		}
		
	}

	public void publicManInfoDelete(Integer id) throws Exception{
		PublicManInfo.dao.deleteById(id);
		
	}

	public void publicManInfoToTop(Integer id) {
		PublicManInfo PublicManInfo = new PublicManInfo();
		PublicManInfo.setId(id);
		PublicManInfo.setPublicIstop(1);
		PublicManInfo.update();	
	}

	public void publicManInfoCanceltop(Integer id) {
		PublicManInfo PublicManInfo = new PublicManInfo();
		PublicManInfo.setId(id);
		PublicManInfo.setPublicIstop(0);
		PublicManInfo.update();
		
	}

	public void publicManInfoCheckSuccess(Integer id) {
		PublicManInfo PublicManInfo = new PublicManInfo();
		PublicManInfo.setId(id);
		PublicManInfo.setPublicState(2);
		PublicManInfo.update();
	}

	public void publicManInfoCheckFail(Integer id) {
		PublicManInfo PublicManInfo = new PublicManInfo();
		PublicManInfo.setId(id);
		PublicManInfo.setPublicState(3);
		PublicManInfo.update();	
	}
	//得到最新的七条公益
	public List<PublicManInfo> getFirstNews() {
		 return PublicManInfo.dao.find("select * from new_info order by news_changetime desc limit 0,7");
	}

	public List<PublicManInfo> getTopSevenPublicManInfo() {
		return PublicManInfo.dao.find("SELECT * from public_man_info where public_state=1  ORDER BY public_isTop,public_open_size desc LIMIT 0,7");
	}

	public PublicManInfo findFristPublicManInfo() {
		// TODO Auto-generated method stub
		return PublicManInfo.dao.findFirst("SELECT * from public_man_info where public_state=1 ORDER BY public_isTop,public_open_size desc LIMIT 0,1");
	}
}