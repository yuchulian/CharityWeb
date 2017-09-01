package com.jlqr.controller.page;

import org.apache.commons.lang.StringUtils;

import com.jlqr.common.ControllerUtil;
import com.jlqr.common.model.Dictionary;
import com.jlqr.service.DictionaryService;
public class DictionaryPage extends ControllerUtil{

	private DictionaryService dictionaryService = new DictionaryService();
	
	public void index() {
		render("dictionaryIndex.html");
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
	
}
