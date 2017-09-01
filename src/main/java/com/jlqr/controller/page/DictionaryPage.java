package com.jlqr.controller.page;

import org.apache.commons.lang.StringUtils;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheInterceptor;
import com.jlqr.common.model.Dictionary;
import com.jlqr.service.DictionaryService;
@Clear(CacheInterceptor.class)
public class DictionaryPage extends Controller{

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
