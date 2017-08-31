package com.jlqr.controller.page;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jlqr.common.model.Dictionary;
import com.jlqr.service.DictionaryService;
@Clear(CacheInterceptor.class)
public class DictionaryPage extends Controller{

	private DictionaryService dictionaryService = new DictionaryService();
	
	public void dictionaryIndex() {
//		dictionaryService.cleanUpdictionary(0, null);
	}
	
	public void dictionaryList() {
		HashMap returnMap = new HashMap();
		try {
			String pid = dictionaryService.getPara(this, "pid", "0");
			if("0".equals(pid)) {
				returnMap.put("dictionary", new Dictionary());
			} else {
				Dictionary dictionary = dictionaryService.findDictionaryById(Integer.parseInt(pid));
				if(null == dictionary)
					returnMap.put("dictionary", new Dictionary());
				else
					returnMap.put("dictionary", dictionary);
			}
			returnMap.put("dictionaryList", dictionaryService.dictionaryList(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
		renderJson(returnMap);
	}
	
	public void dictionaryEdit() {
		Dictionary dictionary = new Dictionary();
		try {
			if(StringUtils.isNotBlank(getPara("id"))) {
				dictionary = dictionaryService.findDictionaryById(Integer.parseInt(getPara("id")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setAttr("dictionary", dictionary);
	}
	
	public void dictionarySave() {
		HashMap returnMsg = new HashMap();
		try {
			Dictionary dictionary = getModel(Dictionary.class, "dictionary");
			dictionaryService.dictionarySave(dictionary);
			returnMsg.put("content", "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "保存失败");
		}
		renderJson(returnMsg);
	}
	
	public void dictionaryDelete() {
		HashMap returnMsg = new HashMap();
		try {
			dictionaryService.deleteDictionaryById(getParaToInt("id"));
			returnMsg.put("content", "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			returnMsg.put("content", "删除失败");
		}
		renderJson(returnMsg);
	}
	
	public void dictionaryPaginate() {
		try {
			renderJson(dictionaryService.dictionaryPaginate(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
