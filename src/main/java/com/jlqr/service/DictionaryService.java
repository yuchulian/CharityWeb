package com.jlqr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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

	public List<HashMap> employDiplomaSelect(Controller controller,Integer employSpeciality) throws Exception {
		List<HashMap> employDiplomaSelectList = new ArrayList<HashMap>();
		List<Record> employDiplomaList = this.list(controller, "SELECT * FROM  dictionary where dictionary_id_path like ',57,%'", "GROUP BY dictionary_id_path");
		String[] pathList = {}, pathnameList = {};
		List<Integer> cidList = new ArrayList<Integer>();
		int  pathLength = 0, pid = 0, cid = 0;
		HashMap cidMap = new HashMap(), employDiplomaMap = new HashMap();
		boolean hasCid = false, hasPid = false;
		for (Record employDiploma : employDiplomaList) {
			pathList = StringUtils.defaultString(employDiploma.getStr("dictionary_id_path"), "").split(",");
			pathnameList = StringUtils.defaultString(employDiploma.getStr("dictionary_name_path"), "").split(",");
			pathLength = pathList.length;
			if(pathLength == pathnameList.length) {
				for (int i=1; i<pathLength; i++) {
					cid = Integer.parseInt(pathList[i]);
					if(!cidMap.containsKey(cid)) {
						if(i == 1)
							pid = 0;
						else
							pid = Integer.parseInt(pathList[i-1]);
					       	employDiplomaMap = new HashMap();
					      	employDiplomaMap.put("pid", pid);
					       	employDiplomaMap.put("id", cid);
				        		employDiplomaMap.put("name", pathnameList[i]);
						employDiplomaMap.put("children", new ArrayList<HashMap>());					
						employDiplomaSelectList = mergeList(cidMap.containsKey(pid), false, employDiplomaSelectList, employDiplomaMap);						
						cidMap.put(cid, null);
						cidList.add(cid);
					}
				}
			}
		}
		
		for(int i=cidList.size()-1; i>-1; i--) {
			employDiplomaSelectList = mergeList(false, employDiplomaSelectList, cidList.get(i));
		}
		
		return employDiplomaSelectList;
	}
	
	private List<HashMap> mergeList(boolean hasPid, boolean isFinish, List<HashMap> employDiplomaSelectList, HashMap employDiplomaMap) {
		if(!isFinish) {
			List<HashMap> _employDiplomaSelectList = null;
			if(hasPid){
				for (HashMap _newsChannelStatisticsMap : employDiplomaSelectList) {
					_employDiplomaSelectList = (List<HashMap>)_newsChannelStatisticsMap.get("children");
					if(Integer.parseInt(employDiplomaMap.get("pid").toString()) == Integer.parseInt(_newsChannelStatisticsMap.get("id").toString())) {
						_employDiplomaSelectList.add(employDiplomaMap);
						isFinish = true;
					} else {
						mergeList(hasPid, isFinish, _employDiplomaSelectList, employDiplomaMap);
					}
				}
			} else {
				employDiplomaSelectList.add(employDiplomaMap);
			}
		}
		return employDiplomaSelectList;
	}
	
	private List<HashMap> mergeList(boolean isFinish, List<HashMap> employDiplomaSelectList, int id) {
		if(!isFinish) {
			List<HashMap> _employDiplomaSelectList = null;
			for (HashMap _newsChannelStatisticsMap : employDiplomaSelectList) {
				_employDiplomaSelectList = (List<HashMap>)_newsChannelStatisticsMap.get("children");
				if(id != Integer.parseInt(_newsChannelStatisticsMap.get("id").toString())) {
					mergeList(isFinish, _employDiplomaSelectList, id);
				}
			}
		}
		return employDiplomaSelectList;
	}
	
}
