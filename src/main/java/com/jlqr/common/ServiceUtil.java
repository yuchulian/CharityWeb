package com.jlqr.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;

/**
 * 封装列表查询、分页查询
 * 集成查询条件、排序
 * @author LiQiRan
 * @date 2017-5-18 16:19:37
 */
public abstract class ServiceUtil {

	/**
	 * 列表查询，支持查询条件、排序
	 * @param modelClass 表对应的model
	 * @param controller
	 */
	public <T> List<T> list(Class<T> modelClass, Controller controller) throws Exception {
		Object temp = createInstance(modelClass);
		Model<?> model = (Model<?>)temp;
		
		StringBuffer select = new StringBuffer(), sqlExceptSelect = new StringBuffer();
		Table table = TableMapping.me().getTable((Class<? extends Model>) modelClass);
		List<StringBuffer> sb = this._sqlSelectAndExcept(table.getName(), controller);
		select = sb.get(0);
		sqlExceptSelect = sb.get(1);
		
		return (List<T>) model.find(select.toString() +" "+ sqlExceptSelect.toString());
	}
	
	/**
	 * 列表查询，自定义查询语句，支持查询条件，不支持排序
	 * @param sqlExceptSelect1 前半部分查询条件，以where 1=1结尾
	 * @param sqlExceptSelect2 后半部分查询条件
	 */
	public List<Record> list(Controller controller, String sqlExceptSelect1, String sqlExceptSelect2) throws Exception {
		StringBuffer sqlCondition = new StringBuffer();
		List<StringBuffer> sb = this._sqlSelectAndExcept(" 自定义表名 ", controller);
		sqlCondition = sb.get(2);
		
		return Db.find(sqlExceptSelect1 +" "+ sqlCondition.toString() +" "+ sqlExceptSelect2);
	}
	
	/**
	 * 分页查询，支持查询条件、排序
	 * @param modelClass 表对应的model
	 * @param controller
	 */
	public <T> Page<T> paginate(Class<T> modelClass, Controller controller) throws Exception {
		Object temp = createInstance(modelClass);
		Model<?> model = (Model<?>)temp;
		
		StringBuffer select = new StringBuffer(), sqlExceptSelect = new StringBuffer();
		Table table = TableMapping.me().getTable((Class<? extends Model>) modelClass);
		List<StringBuffer> sb = this._sqlSelectAndExcept(table.getName(), controller);
		select = sb.get(0);
		sqlExceptSelect = sb.get(1);
		
		Integer pageNumber = controller.getParaToInt(0, 1), pageSize = controller.getParaToInt(1, 1);
		return (Page<T>) model.paginate(pageNumber, pageSize, select.toString(), sqlExceptSelect.toString());
	}
	
	/**
	 * 分页查询，自定义查询语句，支持查询条件，不支持排序
	 * @param controller
	 * @param select 前半部分查询条件，只能是selec *，*可以是表中字段
	 * @param sqlExceptSelect1 中半部分查询条件，以where 1=1结尾
	 * @param sqlExceptSelect2 后半部分查询条件，可加排序，如：order by id asc
	 */
	public Page<Record> paginate(Controller controller, String select, String sqlExceptSelect1, String sqlExceptSelect2) throws Exception {
		StringBuffer sqlCondition = new StringBuffer();
		List<StringBuffer> sb = this._sqlSelectAndExcept(" 自定义表名 ", controller);
		sqlCondition = sb.get(2);
		
		Integer pageNumber = controller.getParaToInt(0, 1), pageSize = controller.getParaToInt(1, 1);
		return Db.paginate(pageNumber, pageSize, select, sqlExceptSelect1 +" "+ sqlCondition.toString() + " " + sqlExceptSelect2);
	}
	
	/**
	 * 在controller中根据页面中传的key获取对应的值
	 * @param controller
	 * @param paraKey 需要得到参数对应的key
	 */
	public String getPara(Controller controller, String paraKey) {
		return this.getPara(controller, paraKey, "");
	}

	/**
	 * 在controller中根据页面中传的key获取对应的值
	 * @param controller
	 * @param paraKey 需要得到参数对应的key
	 * @param defaultValue 默认值
	 */
	public String getPara(Controller controller, String paraKey, String defaultValue) {
		String paraValue = controller.getPara(paraKey), prefixReg = "^\\([^\\)]*\\)";
		if(StringUtils.isBlank(paraValue)) {
			paraValue = defaultValue;
		}
		paraValue = paraValue.replaceFirst(prefixReg, "");
		if(StringUtils.isBlank(paraValue)) {
			paraValue = defaultValue;
		}
		return paraValue;
	}
	
	/**
	 * 获取某个字段的最大值
	 * @param modelClass 表对应的model
	 * @param column 表中的字段
	 */
	public <T>Integer getMaxColumn(Class<T> modelClass, String column) throws Exception {
		Table table = TableMapping.me().getTable((Class<? extends Model>) modelClass);
		Integer maxColumn = Db.findFirst("select max("+column+") maxColumn from "+table.getName()).getInt("maxColumn");
		return null == maxColumn?0:maxColumn;
	}

	/**
	 * 获取某个字段的最小值
	 * @param modelClass 表对应的model
	 * @param column 表中的字段
	 */
	public <T>Integer getMinColumn(Class<T> modelClass, String column) throws Exception {
		Table table = TableMapping.me().getTable((Class<? extends Model>) modelClass);
		Integer minColumn = Db.findFirst("select min("+column+") minColumn from "+table.getName()).getInt("minColumn");
		return null == minColumn?0:minColumn;
	}
	
	private List<StringBuffer> _sqlSelectAndExcept(String modelName, Controller controller) {
		List<StringBuffer> sb = new ArrayList<StringBuffer>();

		StringBuffer select = new StringBuffer(), sqlExceptSelect = new StringBuffer(), sqlCondition = new StringBuffer(), sort = new StringBuffer();
		
//		Map<String, String[]> paraMap = controller.getParaMap();
		Map<String, String> paraMap = new HashMap<String, String>();
		/*String requestMethod = controller.getRequest().getMethod();
		if("GET".equals(requestMethod)) {
			String parameter = controller.getRequest().getQueryString();
			if(StringUtils.isNotEmpty(parameter)) {
				Pattern pattern = Pattern.compile("[a-zA-Z]+[^\\&\\s]*=[^\\&\\s]+[^\\&]*");
				Matcher matcher = pattern.matcher(parameter);
				String[] kv = {};
				while (matcher.find()) {
					kv = matcher.group().split("=");
					paraMap.put(kv[0], this.decode(kv[1]));
				}
			}
		}*/
		
		Map<String, String[]> getParaMap = controller.getParaMap();
		Set<String> keySet = getParaMap.keySet();
		String[] getParaValue = {};
		for(String paraKey : keySet) {
			getParaValue = getParaMap.get(paraKey);
			paraMap.put(paraKey, StringUtils.join(getParaValue, ","));
		}
		
		
		keySet = paraMap.keySet();
		String paraValue = "", prefixReg = "^\\([^\\)]*\\)", symbolReg = "[^\\w\\sa-zA-Z]";//, paraReg = "^\\d+\\-\\d+$", paraStr = ""
		
		select.append("select ");
		if(paraMap.containsKey("column") && StringUtils.isNotEmpty(paraMap.get("column").trim())) {
			paraValue = paraMap.get("column") + " "; 
			select.append(paraValue);
			paraMap.remove("column");
		} else {
			select.append("* ");
		}
		
		if(paraMap.containsKey("sort")) {
			sort = new StringBuffer(paraMap.get("sort"));
			paraMap.remove("sort");
		}

		sqlExceptSelect.append("from "+modelName+" where 1=1 ");
		Pattern pattern = Pattern.compile(prefixReg);
		Matcher matcher = null;
		for(String paraKey : keySet) {
			paraValue = paraMap.get(paraKey);
			paraKey = paraKey.replaceAll(symbolReg, "");
//			paraValue = StringUtils.join(paraValue,"");
			
			matcher = pattern.matcher(paraValue);
			if(matcher.find()) {
				paraValue = paraValue.replaceFirst(prefixReg, "");
				controller.setAttr(paraKey, paraValue);
				sqlExceptSelect.append(this.sqlScript(paraKey, matcher.group(), paraValue));
				sqlCondition.append(this.sqlScript(paraKey, matcher.group(), paraValue));
			}
		}
		if(StringUtils.isNotEmpty(sort.toString())) {
			sqlExceptSelect.append("order by "+sort.toString());
		}
		
		sb.add(select);
		sb.add(sqlExceptSelect);
		sb.add(sqlCondition);
		//System.out.println(select.toString()+sqlExceptSelect.toString());
		return sb;
	}
	
	private String sqlScript(String key, String operator, String value) {
		StringBuffer sqlExceptSelect = new StringBuffer();
		if(StringUtils.isNotEmpty(value)) {
			String curvesReg = "^\\(([^\\)]*)\\)";
			operator = operator.replaceFirst(curvesReg, "$1");
			sqlExceptSelect.append(" and ");
			if(operator.matches("^(=|>=|<=|>|<|<>)$")) {
				sqlExceptSelect.append(key+" "+operator+" '"+value+"' ");
			} else if("like".equals(operator)) {
				sqlExceptSelect.append(key+" "+operator+" '%"+value+"%' ");
			} else if("likeLeft".equals(operator)) {
				sqlExceptSelect.append(key+" like '%"+value+"' ");
			} else if("likeRight".equals(operator)) {
				sqlExceptSelect.append(key+" like '"+value+"%' ");
			} else if("in".equals(operator)) {
				sqlExceptSelect.append(key+" in("+value+") ");
			} else if("between".equals(operator)) {
				String[] valueArray = value.split(",");
				if(valueArray.length == 2 && StringUtils.isNotEmpty(valueArray[0]) && StringUtils.isNotEmpty(valueArray[1])) {
					sqlExceptSelect.append(key+" between '"+valueArray[0]+"' and '"+valueArray[1]+"' ");
				} else
					return "";
			} else if("betweenDate".equals(operator)) {
				String[] valueArray = value.split(",");
				if(valueArray.length == 2 && StringUtils.isNotEmpty(valueArray[0]) && StringUtils.isNotEmpty(valueArray[1])) {
//					valueArray[0] = TimeUtil.getTime(valueArray[0]+" 00:00:00", "yyyy-MM-dd HH:mm:ss")+"";
//					valueArray[1] = TimeUtil.getTime(valueArray[1]+" 23:59:59", "yyyy-MM-dd HH:mm:ss")+"";
					sqlExceptSelect.append(key+" between '"+valueArray[0]+"' and '"+valueArray[1]+"' ");
				} else
					return "";
			}
		}
		return sqlExceptSelect.toString();
	}

	//张三
	private String encode(String url) {
		try {
			if(StringUtils.isNotEmpty(url)) {
				return URLEncoder.encode(url, "UTF-8");
			} else {
				url = "";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}

	//%E5%BC%A0%E4%B8%89
	private String decode(String url) {
		try {
			if(StringUtils.isNotEmpty(url)) {
				return URLDecoder.decode(url, "UTF-8");
			} else {
				url = "";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}

	private static <T> T createInstance(Class<T> objClass) {
		try {
			return objClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	/**
	 * 简单的字符拼接，拼接成sql语句
	 * @param values 先要获取到的信息，如果要全部可以为0=null，
	 * @param map 条件
	 * @param dbTable 数据表名
	 * @return SimpleSql类，有字符串sql，和列表list，list用来存储sql中的条件的问号的值。
	 */
	protected SimpleSql getSimpleSql(List<String> values,Map<String,Object> map,String dbTable){
		List<Object> lists=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer("select ");
		if(values==null||values.size()==0){
			sql.append("*");
		}else{
			for(int i=0;i<values.size();i++){
				sql.append(values.get(i));
				if(i<(values.size()-1)){
					sql.append(",");
				}
			}
		}
		sql.append(" from ");
		sql.append(dbTable);
		sql.append(" where 1=1 ");
		if(map!=null){
			for(Map.Entry<String, Object> entry:map.entrySet()){
				sql.append("and ");
				sql.append(entry.getKey());
				sql.append("=? ");
				lists.add(entry.getValue());
			}
		}
		return new SimpleSql(sql.toString(),lists);
	}
	protected class SimpleSql{
		private String sql;
		private List<Object> list;
		public SimpleSql(String sql,List<Object> list) {
			this.sql=sql;
			this.list=list;
		}
		public String getSql() {
			return sql;
		}
		public void setSql(String sql) {
			this.sql = sql;
		}
		public List<Object> getList() {
			return list;
		}
		public void setList(List<Object> list) {
			this.list = list;
		}
	}
}
