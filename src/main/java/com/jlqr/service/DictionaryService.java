package com.jlqr.service;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
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
	public List<Dictionary> findDictionaryListByPId(Integer dictionary_pid) throws Exception {
		return Dictionary.dao.find("select * from dictionary where dictionary_pid = "+dictionary_pid);
	}
	public void dictionarySave(Dictionary dictionary) throws Exception {
		if(null != dictionary) {
			Dictionary dictionaryParent = this.findDictionaryById(dictionary.getDictionaryPid());
			dictionary.setDictionaryName(dictionary.getDictionaryName().replaceAll("\\,", ""));
			if(null == dictionary.getId()) {
				dictionary.setId(getMaxColumn(Dictionary.class, "id") + 1);
				
				//组装path和pathname
				if(null != dictionaryParent) {
					dictionary.setDictionaryIdPath(dictionaryParent.getDictionaryIdPath()+dictionary.getId()+",");
					dictionary.setDictionaryNamePath(dictionaryParent.getDictionaryNamePath()+dictionary.getDictionaryName()+",");
				} else {
					dictionary.setDictionaryIdPath(","+dictionary.getId()+",");
					dictionary.setDictionaryNamePath(","+dictionary.getDictionaryName()+",");
				}
				
				dictionary.save();
			} else {
				//组装path和pathname
				if(null != dictionaryParent) {
					dictionary.setDictionaryNamePath(dictionaryParent.getDictionaryNamePath()+dictionary.getDictionaryName()+",");
				} else {
					dictionary.setDictionaryNamePath(","+dictionary.getDictionaryName()+",");
				}
				
				//修改该pathname的子类
				Dictionary dictionaryOld = this.findDictionaryById(dictionary.getId());
				List<Dictionary> dictionaryList = Dictionary.dao.find("select * from dictionary where dictionary_id_path like '"+dictionaryOld.getDictionaryIdPath()+"%' and dictionary_id_path <> '"+dictionaryOld.getDictionaryIdPath()+"'");
				for (Dictionary _dictionary : dictionaryList) {
					_dictionary.setDictionaryNamePath(_dictionary.getDictionaryNamePath().replace(dictionaryOld.getDictionaryNamePath(), dictionary.getDictionaryNamePath()));
					_dictionary.update();
				}
				
				dictionary.update();
			}
		}
	}
	public void deleteDictionaryById(Integer dictionaryId) throws Exception {
		Dictionary dictionary = this.findDictionaryById(dictionaryId);
		Db.update("delete from dictionary where dictionary_id_path like '"+dictionary.getDictionaryIdPath()+"%'");
	}
	public List<Dictionary> dictionaryByPid(Integer pid) throws Exception{
		return Dictionary.dao.find("select * from dictionary where dictionary_pid =?",pid);
	}
	
	
}
