package com.jlqr.common;

import java.util.UUID;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

import com.jfinal.log.Log;

public class SystemUtil {
	
	public static final Log logger = Log.getLog(SystemUtil.class);
	
	public static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 生成唯一id
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
}
