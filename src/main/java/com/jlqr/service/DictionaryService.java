package com.jlqr.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jlqr.common.ServiceUtil;
import com.jlqr.common.model.Dictionary;

public class DictionaryService extends ServiceUtil {
	public Page<Dictionary> dictionaryPaginate(Controller controller) throws Exception {
		return this.paginate(Dictionary.class, controller);
	}
	public List<Dictionary> dictionaryList(Controller controller) throws Exception {
		return this.list(Dictionary.class, controller);
	}
	public Dictionary findDictionaryById(Integer id) throws Exception {
		return Dictionary.dao.findById(id);
	}
	public List<Dictionary> findDictionaryListByPId(Integer pid) throws Exception {
		return Dictionary.dao.find("select * from dictionary where pid = "+pid);
	}
	public void dictionarySave(Dictionary dictionary) throws Exception {
		if(null != dictionary) {
			Dictionary DictionaryParent = this.findDictionaryById(dictionary.getInt("pid"));//Dictionary.dao.findById(dictionary.getInt("pid"));
			if(null == dictionary.getInt("id")) {
				Dictionary DictionaryMax = Dictionary.dao.findFirst("select max(id) as id from dictionary");
				
				//自增长id
				if(null != DictionaryMax && null != DictionaryMax.getInt("id")) {
					dictionary.set("id", DictionaryMax.getInt("id")+1);
				} else {
					dictionary.set("id", 1);
				}
				
				//组装path和pathname
				dictionary.set("name", dictionary.getStr("name").replaceAll("\\#", ""));
				if(null != DictionaryParent) {
					dictionary.set("path", DictionaryParent.getStr("path")+dictionary.getInt("id")+"#");
					dictionary.set("pathname", DictionaryParent.getStr("pathname")+dictionary.getStr("name")+"#");
				} else {
					dictionary.set("path", "#"+dictionary.getInt("id")+"#");
					dictionary.set("pathname", "#"+dictionary.getStr("name")+"#");
				}
				
				dictionary.save();
			} else {
				//组装path和pathname
				dictionary.set("name", dictionary.getStr("name").replaceAll("\\#", ""));
				if(null != DictionaryParent) {
					dictionary.set("pathname", DictionaryParent.getStr("pathname")+dictionary.getStr("name")+"#");
				} else {
					dictionary.set("pathname", "#"+dictionary.getStr("name")+"#");
				}
				
				//修改该pathname的子类
				Dictionary dictionaryOld = this.findDictionaryById(dictionary.getInt("id"));//Dictionary.dao.findById(dictionary.getInt("id"));
				List<Dictionary> dictionaryList = Dictionary.dao.find("select * from dictionary where path like '"+dictionaryOld.getStr("path")+"%' and path <> '"+dictionaryOld.getStr("path")+"'");
				for (Dictionary _dictionary : dictionaryList) {
					_dictionary.set("pathname", _dictionary.getStr("pathname").replace(dictionaryOld.getStr("pathname"), _dictionary.getStr("pathname")));
					_dictionary.update();
				}
				
				dictionary.update();
			}
		}
	}
	public void deleteDictionaryById(Integer dictionaryId) throws Exception {
		Dictionary dictionary = this.findDictionaryById(dictionaryId);
		if(null != dictionary) {
			List<Dictionary> dictionaryList = Dictionary.dao.find("select * from dictionary where path like '"+dictionary.getStr("path")+"%'");
			if(dictionaryList.size() > 0) {
				Integer[] deleteDictionaryIds = new Integer[dictionaryList.size()];
				for (int i=0; i<dictionaryList.size(); i++) {
					deleteDictionaryIds[i] = dictionaryList.get(i).getInt("id");
				}
				Db.update("delete from dictionary where id in("+StringUtils.join(deleteDictionaryIds, ",")+")");
			}
		}
	}
	

	
}
