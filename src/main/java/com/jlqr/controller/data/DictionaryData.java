package com.jlqr.controller.data;

import java.util.HashMap;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.interceptor.NewService;
import com.jlqr.service.DictionaryService;
public class DictionaryData extends ControllerUtil{

	@NewService("DictionaryService")
	private DictionaryService dictionaryService;
	
	public void dictionaryList() {
		HashMap returnMap = new HashMap();
		try {
			String dictionary_pid = dictionaryService.getPara(this, "dictionary_pid", "0");
			if("0".equals(dictionary_pid)) {
				returnMap.put("dictionary", new Dictionary());
			} else {
				Dictionary dictionary = dictionaryService.findDictionaryById(Integer.parseInt(dictionary_pid));
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
