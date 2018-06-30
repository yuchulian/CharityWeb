package com.admin.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.jfinal.kit.PropKit;

/**
 * 封装复制、剪切、删除文件操作
 * @author LiQiRan
 * @date 2017-5-18 16:20:34
 * 
 */
public class FileUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	/**
	 * 复制文件
	 * @param originalPath 原始文件路径
	 * @param newPath 新的文件路径
	 */
	public static String copy(String originalPath, String newPath) {
		return copy(originalPath, newPath, false);
	}
	
	/**
	 * 剪切文件
	 * @param originalPath 原始文件路径
	 * @param newPath 新的文件路径
	 */
	public static String cut(String originalPath, String newPath) {
		return copy(originalPath, newPath, true);
	}
	
	/**
	 * 删除文件
	 * @param originalPath 文件路径
	 */
	public static void delete(String originalPath) {
		try {
			String downloadPath = PropKit.get("downloadPath");
			File originalFile = new File(downloadPath+originalPath);
			if(originalFile.exists()) {
				originalFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 复制文件
	 * @param originalPath 原始文件路径
	 * @param newPath 新的文件路径
	 * @param deleteOriginal 是否删除原文件，true为删除
	 */
	private static String copy(String originalPath, String newPath, Boolean deleteOriginal) {
		try {
			if(StringUtils.isNotEmpty(originalPath) && StringUtils.isNotEmpty(newPath)) {
				String downloadPath = PropKit.get("downloadPath");
				File newDirectory = new File(downloadPath+newPath);
				if(!newDirectory.exists() && !newDirectory.isDirectory()) {
					newDirectory.mkdirs();
				}
				
				String originalFileName = "";
				Pattern pattern = Pattern.compile("\\/[^\\/]+\\.[^\\.][a-zA-Z0-9]+$");
				Matcher matcher = pattern.matcher(originalPath);
				if(matcher.find()) {
					originalFileName = matcher.group();
				}


				String thisDate = sdf.format(new Date());
				String directory = downloadPath+newPath+"/"+thisDate;
				newDirectory = new File(directory);
				if(!newDirectory.exists() && !newDirectory.isDirectory()) {
					newDirectory.mkdirs();
				}
				File newFile = new File(downloadPath+newPath+"/"+thisDate+originalFileName);
				
				File originalFile = new File(downloadPath+originalPath);
				if(originalFile.exists()) {
					copy(originalFile, newFile);
					if(deleteOriginal) {
						originalFile.delete();
					}
					return newPath+"/"+thisDate+originalFileName;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 复制文件
	 * @param originalFile 原始文件
	 * @param newFile 新的文件
	 */
	private static void copy(File originalFile, File newFile) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(originalFile);
			fo = new FileOutputStream(newFile);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean deletefile(String path){
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		if (!file.isDirectory()) {
			return false;
		}
		String[] str = file.list();
		for (int i = 0; i < str.length; i++) {
			File fi = new File(path + "/" + str[i]);
			if (path.endsWith(file.separator)) {
				fi = new File(path + str[i]);
			} else {
				fi = new File(path + fi.separator + str[i]);
			}

			if(fi.exists()||fi.list().length==0){
				File myFilePath = new File(path+"/"+str[i]);   
				myFilePath.delete();
			}
			if(fi.isDirectory())//如果文件假内还有 就继续调用本方法       
			{          
				deletefile(path+"/"+str[i]);      
			}else{
				fi.delete();
			}

		}
		return true;
	} 
}