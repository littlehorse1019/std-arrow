package com.std.framework.model.ormap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.std.framework.annotation.PrimaryKey;
import com.std.framework.model.actor.BaseSqlGenerator;

/**
 * @author Luox 对象->数据表映射载体
 */
public class Obj2TabContainer {

	private Map<String, String> tabMap = null;
	private Map<String, String> seqMap = null;
	private Map<String, String> colMap = null;
	private Map<String, String> pkMap = null;
	private Map<String, String> baseSqlMap = null;

	public Map<String, String> getTabMap() {
		return tabMap;
	}

	public void setTabMap(Map<String, String> tabMap) {
		this.tabMap = tabMap;
	}

	public Map<String, String> getSeqMap() {
		return seqMap;
	}

	public void setSeqMap(Map<String, String> seqMap) {
		this.seqMap = seqMap;
	}

	public Map<String, String> getColMap() {
		return colMap;
	}

	public void setColMap(Map<String, String> colMap) {
		this.colMap = colMap;
	}

	public Map<String, String> getBaseSqlMap() {
		return baseSqlMap;
	}

	public void setBaseSqlMap(Map<String, String> baseSqlMap) {
		this.baseSqlMap = baseSqlMap;
	}

	public Map<String, String> getPkMap() {
		return pkMap;
	}

	public void setPkMap(Map<String, String> pkMap) {
		this.pkMap = pkMap;
	}

	public void loadMapping(String className, MappingRule mappingRule) {
		try {
			fillTabMap(className, mappingRule);
			fillSeqMap(className, mappingRule);
			fillColMap(className, mappingRule);

			ORMValidator ormValid = new ORMValidator();
			// 测试表名，序列和列名的映射关系
			boolean testResult = ormValid.validOrmmap(this);
			// 测试通过则生成基础操作Sql
			if (testResult) {
				BaseSqlGenerator bsGenerator = new BaseSqlGenerator();
				baseSqlMap = bsGenerator.genBaseSqlMap(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取表的表名 Class->Table Mapping
	 */
	public void fillTabMap(String className, MappingRule mappingRule) throws Exception {
		tabMap = new HashMap<String, String>();
		String simpleName = className.substring(className.lastIndexOf(".") + 1);
		tabMap.put(simpleName, mappingRule.ObjMapTab(simpleName).toUpperCase());
	}

	/**
	 * 获取表主键序列的 Class->Table Mapping
	 */
	public void fillSeqMap(String className, MappingRule mappingRule) throws Exception {
		seqMap = new HashMap<String, String>();
		String simpleName = className.substring(className.lastIndexOf(".") + 1);
		seqMap.put(simpleName, mappingRule.ObjMapSeq(simpleName).toUpperCase());
	}

	/**
	 * 获取表的字段Class->Table Mapping 使用LinkedHashMap，确保在用Iterator迭代的时候，保证和反射插入的时候值的顺序一样。
	 */
	public void fillColMap(String className, MappingRule mappingRule) throws Exception {
		colMap = new LinkedHashMap<String, String>();
		pkMap = new LinkedHashMap<String, String>();
		Class<?> ormClass = Class.forName(className);
		Field[] fields = ormClass.getDeclaredFields();
		for (Field field : fields) {
			String fieldName = field.getName();
			colMap.put(fieldName, mappingRule.ObjMapCol(fieldName).toUpperCase());
			if (field.getAnnotations() != null && field.getAnnotation(PrimaryKey.class) != null) {
				pkMap.put(fieldName, mappingRule.ObjMapCol(fieldName).toUpperCase());
			}
		}
	}
}
