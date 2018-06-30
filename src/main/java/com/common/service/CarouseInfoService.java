package com.common.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.admin.common.FileUtil;
import com.admin.common.ServiceUtil;
import com.admin.common.model.CarouseInfo;
import com.admin.common.model.MangerInfo;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Page;

public class CarouseInfoService extends ServiceUtil {

	public Page<CarouseInfo> carouseInfoPaginate(Controller controller) throws Exception {
		// TODO Auto-generated method stub
		return this.paginate(CarouseInfo.class, controller);
	}

	public CarouseInfo findCarouseInfoById(int id) {
		return CarouseInfo.dao.findById(id);
	}

	public void carouseInfoSave(CarouseInfo carouseInfo,Controller controller) throws Exception {
		if(StringUtils.defaultString(carouseInfo.getCarouseImg(), "").indexOf("/temp/") > -1) {
			carouseInfo.setCarouseImg(FileUtil.cut(carouseInfo.getCarouseImg(), PropKit.get("img")));
		}
		if(null == carouseInfo.getId()) {
			carouseInfo.setId(getMaxColumn(CarouseInfo.class, "id") + 1);
			Date date = new Date();
			carouseInfo.setCarouseChagetime(date);
			carouseInfo.setCarouseIstop(0);
			carouseInfo.save();
		} else {
			carouseInfo.update();
		}
	}

	public void carouseInfoService(Integer id) {
		CarouseInfo.dao.deleteById(id);
	}

	public List<CarouseInfo> findCarouseforHome() {
		// TODO Auto-generated method stub
		return CarouseInfo.dao.find("select * from carouse_info ORDER BY carouse_istop DESC , carouse_createtime  DESC LIMIT 0,3");
	}

	public void carouseInfoToTop(Integer id) {
		CarouseInfo carouseInfo = new CarouseInfo();
		carouseInfo.setId(id);
		Date date = new Date();
		carouseInfo.setCarouseChagetime(date);
		carouseInfo.setCarouseIstop(1);
		carouseInfo.update();
		
	}

	public void carouseInfoCanceltop(Integer id) {
		// TODO Auto-generated method stub
		CarouseInfo carouseInfo = new CarouseInfo();
		carouseInfo.setId(id);
		Date date = new Date();
		carouseInfo.setCarouseChagetime(date);
		carouseInfo.setCarouseIstop(0);
		carouseInfo.update();
	}



}
