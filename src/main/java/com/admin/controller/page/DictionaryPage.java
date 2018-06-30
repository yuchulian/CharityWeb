package com.admin.controller.page;

import org.apache.commons.lang.StringUtils;

import com.admin.common.ControllerUtil;
import com.admin.common.model.Dictionary;
import com.admin.interceptor.NewService;
import com.common.service.DictionaryService;
public class DictionaryPage extends ControllerUtil{

	@NewService("DictionaryService")
	private DictionaryService dictionaryService;
	
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
