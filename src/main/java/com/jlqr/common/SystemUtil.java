package com.jlqr.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	/**
	 * 判断类似子集，如:<br>
	 * A:",1,2,3,"<br>
	 * B:",2,3,"<br>
	 * spliRegex:","<br>
	 * B是A的子集返回true
	 * @param a
	 * @param b
	 * @param spliRegex
	 * @return
	 */
	public static boolean stringAContainB(String a, String b, String spliRegex){
		List<String> lista = Arrays.asList(a.split(spliRegex));
		List<String> listA = new ArrayList<String>(lista);
		listA.remove("");
        List<String> listb = Arrays.asList(b.split(spliRegex));
        List<String> listB = new ArrayList<String>(listb);
        listB.remove("");
        boolean containsAll = listA.containsAll(listB);
		return containsAll;
	}
	
}
