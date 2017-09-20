package com.jlqr.controller.data;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;
import com.jlqr.common.ControllerUtil;

/**
 * 文件下载
 * @author LiQiRan
 * @date 2017-5-18 16:20:34
 */
public class DownloadData extends ControllerUtil{
	
	private static String defaultImgPath = "";
	private static File defaultImgFile = null;

	public void index() {
		try {
			String filePath = getPara("filePath"), fileName = StringUtils.defaultString(getPara("fileName"), "").replaceAll("[^0-9a-zA-Z\\u4e00-\\u9fa5]", "");
			if(StringUtils.isNotBlank(filePath)) {
				filePath = filePath.replaceAll("^[^0-9a-zA-Z]*", "/");
				File file = new File(PropKit.get("downloadPath")+filePath);
				if(file.exists()) {
					if(StringUtils.isBlank(fileName)) {
						renderFile(file);
					} else {
						String fileFormat = "";
						Pattern pattern = Pattern.compile("\\.[^\\.][a-zA-Z0-9]+$");
						String originalFileName = file.getName();
						Matcher matcher = pattern.matcher(originalFileName);
						if(matcher.find()) {
							fileFormat = matcher.group();
						}
						renderFile(file, fileName+fileFormat);
					}
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderError(404);
	}
	
	public void img() {
		try {
			String filePath = getPara("filePath");
			if(StringUtils.isNotBlank(filePath)) {
				filePath = filePath.replaceAll("^[^0-9a-zA-Z]*", "/");
				File imgFile = new File(PropKit.get("downloadPath")+filePath);
				if(imgFile.exists()) {
					renderFile(imgFile);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isBlank(defaultImgPath)) {
			defaultImgPath = getRequest().getSession().getServletContext().getRealPath("/")+"\\img\\wu.jpg";
		}
		if(null == defaultImgFile) {
			defaultImgFile = new File(defaultImgPath);
		}
		renderFile(defaultImgFile);
	}
	
}
