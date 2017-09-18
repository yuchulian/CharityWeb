package com.jlqr.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.apache.commons.lang.StringUtils;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Record;
import com.jlqr.common.model.Dictionary;
import com.jlqr.common.model.LoginInfo;
import com.jlqr.common.model.RoleInfo;

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
	
	/**
	 * 将字典集合转换为zTree对象
	 */
	public static List<Record> toDictionaryList(List<Dictionary> dictionaryList, LoginInfo loginInfo) {
		List<Record> recordList = new ArrayList<Record>();
		Record record = null;
		if(null != dictionaryList) {
			for (Dictionary dictionary : dictionaryList) {
				record = new Record();
				record.set("id", dictionary.getId());
				record.set("pId", dictionary.getDictionaryPid());
				record.set("name", dictionary.getDictionaryName());
				record.set("open", true);
				if(StringUtils.defaultIfEmpty(loginInfo.getDepartmentId(), "").indexOf(","+dictionary.getId()+",") > -1) {
					record.set("checked", true);
				}
				recordList.add(record);
			}
		}
		return recordList;
	}
	
	/**
	 * 将集合转换为zTree对象
	 */
	public static List<Record> toRoleInfoList(List<RoleInfo> roleInfoList, LoginInfo loginInfo) {
		List<Record> recordList = new ArrayList<Record>();
		Record record = null;
		if(null != roleInfoList) {
			for (RoleInfo roleInfo : roleInfoList) {
				record = new Record();
				record.set("id", roleInfo.getId());
				record.set("pId", 0);
				record.set("name", roleInfo.getRoleName());
				record.set("open", true);
				if(StringUtils.defaultString(loginInfo.getRoleId(), "").indexOf(","+roleInfo.getId()+",") > -1) {
					record.set("checked", true);
				}
				recordList.add(record);
			}
		}
		return recordList;
	}
	
}
