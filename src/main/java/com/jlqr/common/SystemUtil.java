package com.jlqr.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

import com.jfinal.log.Log;

public class SystemUtil {
	
	/**
	 * log4j对象
	 */
	public static final Log logger = Log.getLog(SystemUtil.class);
	
	/**
	 * activiti流程引擎
	 */
	public static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 将Date()格式化成标准的时间
	 */
	private static SimpleDateFormat _timeSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String formatTime(Date date) {
		if(null == date)
			return "";
		return _timeSdf.format(date);
	}
	
	/**
	 * 将Date()格式化成标准的日期
	 */
	public static SimpleDateFormat _dateSdf = new SimpleDateFormat("yyyy-MM-dd");
	public static String formatDate(Date date) {
		if(null == date)
			return "";
		return _dateSdf.format(date);
	}
	
	/**
	 * 生成唯一id
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
}
