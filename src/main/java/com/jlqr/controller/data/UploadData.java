package com.jlqr.controller.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;
import com.jlqr.common.ControllerUtil;
import com.jlqr.common.SystemUtil;

import net.sf.json.JSONObject;
import sun.misc.BASE64Decoder;

/**
 * 文件上传
 * @author LiQiRan
 * @date 2017-5-18 16:20:24
 */
public class UploadData extends ControllerUtil{
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public void index() {
		HashMap<String, Object> returnMap = getReturnMap();
		try {
			UploadFile file = getFile();
			String watermark = getPara("watermark");
	
			if(null != file) {
				String fileFormat = "";
				Pattern pattern = Pattern.compile("\\.[^\\.][a-zA-Z0-9]+$");
				String originalFileName = file.getOriginalFileName();
				Matcher matcher = pattern.matcher(originalFileName);
				if(matcher.find()) {
					fileFormat = matcher.group();
				}

				String fileName = "";
				pattern = Pattern.compile("[^\\/]+\\.[^\\.][a-zA-Z0-9]+$");
				matcher = pattern.matcher(originalFileName);
				if(matcher.find()) {
					fileName = matcher.group().replaceAll(fileFormat, "");
				}

				String thisDate = sdf.format(new Date());
				String directory = PropKit.get("uploadPath")+"/"+thisDate;
				File newDirectory = new File(directory);
				if(!newDirectory.exists() && !newDirectory.isDirectory()) {
					newDirectory.mkdirs();
				}
				String filePath = SystemUtil.getUUID()+fileFormat;
				File newFile = new File(directory+"/"+filePath);
				Boolean resetName = file.getFile().renameTo(newFile);
				if(resetName) {
//					MarkUtils.pressText(directory+"/"+filePath, watermark);
					returnMap.put("contentType", file.getContentType());
					returnMap.put("fileName", fileName);
					returnMap.put("filePath", "/temp/"+thisDate+"/"+filePath);
					
					returnMap.put("returnState", "success");
					returnMap.put("returnMsg", "操作成功");
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}

	public void ueditor() {
		// 百度编辑器加载出按钮图标前 会将所有控件的路径 先通过config.json
		// 文件加载出来(包括上传图片路径，视频路径等路径都是通过config.json 文件读取的)
		// 所以某些控件点击不了 是因为 config.json文件没有找到 或者是文件里面的路径有问题
		String action = getPara("action");
		JSONObject returnMsg = new JSONObject();
		if("config".equals(action)) {
			// 这里千万注意 "config.json" 文件前方的目录一定要正确
			try {
				String configPath = getRequest().getSession().getServletContext().getRealPath("/") + "\\Public\\js\\ueditor\\config.json";
				File configFile = new File(configPath);
				String encoding = "UTF-8";
				StringBuilder configBuilder = new StringBuilder();				
				if (configFile.isFile() && configFile.exists()) { // 判断文件是否存在
					InputStreamReader read = new InputStreamReader(new FileInputStream(configFile), encoding);// 考虑到编码格式
					BufferedReader bufferedReader = new BufferedReader(read);
					String lineTxt = null;
					while ((lineTxt = bufferedReader.readLine()) != null) {
						configBuilder.append(lineTxt);
					}
					read.close();
					renderText(configBuilder.toString());
					return;
				} else {
					//System.out.println("找不到指定的文件");
				}
			} catch (Exception e) {
				//System.out.println("读取文件内容出错");
				e.printStackTrace();
			}
		} else if("#uploadimage#uploadvideo#uploadfile#".indexOf("#"+action+"#") > -1) {
			// "upfile" 来自 config.json 中的 imageFieldName 配置项
			try {
				UploadFile file = getFile();
				String watermark = getPara("watermark");
		
				if(null != file) {
					String fileFormat = "";
					Pattern pattern = Pattern.compile("\\.[^\\.][a-zA-Z0-9]+$");
					String originalFileName = file.getOriginalFileName();
					Matcher matcher = pattern.matcher(originalFileName);
					if(matcher.find()) {
						fileFormat = matcher.group();
					}

					String fileName = "";
					pattern = Pattern.compile("[^\\/]+\\.[^\\.][a-zA-Z0-9]+$");
					matcher = pattern.matcher(originalFileName);
					if(matcher.find()) {
						fileName = matcher.group().replaceAll(fileFormat, "");
					}
					
					String thisDate = sdf.format(new Date());
					String directory = PropKit.get("ueditorPath")+"/"+thisDate;
					File newDirectory = new File(directory);
					if(!newDirectory.exists() && !newDirectory.isDirectory()) {
						newDirectory.mkdirs();
					}
					String filePath = SystemUtil.getUUID()+fileFormat;
					File newFile = new File(directory+"/"+filePath);
					Boolean resetName = file.getFile().renameTo(newFile);
					if(resetName) {
//						MarkUtils.pressText(directory+"/"+filePath, watermark);
						returnMsg.put("state", "SUCCESS");
						returnMsg.put("title", fileName);
						returnMsg.put("original", originalFileName);
						returnMsg.put("type", fileFormat);
						returnMsg.put("url", "/Download?filePath=/ueditor/"+thisDate+"/"+filePath+"&fileName="+fileName);
						returnMsg.put("size", newFile.length());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if("uploadscrawl".equals(action)) {
			String imgStr = getPara("upfile");
			String watermark = getPara("watermark");
			if(!StrKit.isBlank(imgStr)) {
				BASE64Decoder decoder = new BASE64Decoder();
				try {
					// 解密
					byte[] b = decoder.decodeBuffer(imgStr);
					// 处理数据
					for (int i = 0; i < b.length; ++i) {
						if (b[i] < 0) {
							b[i] += 256;
						}
					}
					
					String thisDate = sdf.format(new Date());
					String directory = PropKit.get("ueditorPath")+"/"+thisDate;
					
					boolean isTmep = getParaMap().containsKey("isTmep") && getParaToBoolean("isTmep");
					if(isTmep) {
						directory = PropKit.get("uploadPath")+"/"+thisDate;
					}
					
					File newDirectory = new File(directory);
					if(!newDirectory.exists() && !newDirectory.isDirectory()) {
						newDirectory.mkdirs();
					}
					String fileName = SystemUtil.getUUID();
					String filePath = fileName+".png";
					OutputStream out = new FileOutputStream(directory+"/"+filePath);
					out.write(b);
					out.flush();
					out.close();
					
					File newFile = new File(directory+"/"+filePath);
					if(newFile.exists()) {
//						MarkUtils.pressText(directory+"/"+filePath, watermark);
						if(isTmep) {
							returnMsg.put("fileName", fileName);
							returnMsg.put("filePath", "/temp/"+thisDate+"/"+filePath);
						} else {
							returnMsg.put("state", "SUCCESS");
							returnMsg.put("title", fileName);
							returnMsg.put("original", filePath);
							returnMsg.put("type", ".png");
							returnMsg.put("url", "/Download?filePath=/ueditor/"+thisDate+"/"+filePath);
							returnMsg.put("size", newFile.length());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			//System.out.println("没有实现【"+action+"】方法！");
		}
		renderText(returnMsg.toString());
	
	}
	
}
