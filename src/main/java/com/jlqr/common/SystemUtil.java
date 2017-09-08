package com.jlqr.common;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;

import com.jfinal.log.Log;

public class SystemUtil {
	public static final Log logger = Log.getLog(SystemUtil.class);
	public static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
}
