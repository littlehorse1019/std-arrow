package com.std.framework.model.actor;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.std.framework.model.ormap.Obj2TabContainer;

/**
 * @author Luox 基础操作的 Sql 生成类(Oracle版本)
 */
public class BaseSqlGenerator {

	private Map<String, String> tabMap = null;
	private Map<String, String> colMap = null;
	private Map<String, String> pkMap = null;

	private void initMaps(Obj2TabContainer obj2Tab) {
		this.tabMap = obj2Tab.getTabMap();
		this.colMap = obj2Tab.getColMap();
		this.pkMap = obj2Tab.getPkMap();
	}

	/** 预装配表增删改查基础操作的 Sql 提高速率 */
	public Map<String, String> genBaseSqlMap(Obj2TabContainer obj2Tab) throws Exception {

		initMaps(obj2Tab);

		Map<String, String> baseSqlMap = new HashMap<String, String>();
		baseSqlMap.put(BaseSqlEnum.Save.toString(), prepareSaveSql(tabMap, colMap));
		baseSqlMap.put(BaseSqlEnum.Delete.toString(), prepareDeleteSql(tabMap, pkMap));
		baseSqlMap.put(BaseSqlEnum.Update.toString(), prepareUpdateSql(tabMap, colMap, pkMap));
		baseSqlMap.put(BaseSqlEnum.FindByPK.toString(), prepareFindByPKSql(tabMap, colMap, pkMap));
		baseSqlMap.put(BaseSqlEnum.FindAll.toString(), prepareFindAllSql(tabMap, colMap));
		return baseSqlMap;
	}

	/**
	 * 预填充Save基础操作Sql
	 */
	private static String prepareSaveSql(Map<String, String> tabMap, Map<String, String> colMap) throws Exception {
		StringBuilder saveSql = new StringBuilder("");
		saveSql.append("INSERT INTO ");
		Iterator<String> tabName = tabMap.values().iterator();
		if (tabName.hasNext()) {
			saveSql.append(tabName.next());
		}
		saveSql.append(" ( ");
		Iterator<String> colName = colMap.values().iterator();
		while (colName.hasNext()) {
			saveSql.append(colName.next() + ",");
		}
		saveSql.deleteCharAt(saveSql.length() - 1);
		saveSql.append(" ) VALUES ( ");
		for (int i = 0; i < colMap.keySet().size(); i++) {
			saveSql.append(" ?, ");
		}
		saveSql.delete(saveSql.lastIndexOf(","), saveSql.length()).append(" ) ");

		return saveSql.toString();
	}

	/**
	 * 预填充Delete基础操作Sql
	 */
	private static String prepareDeleteSql(Map<String, String> tabMap, Map<String, String> pkMap) throws Exception {
		StringBuilder deleteSql = new StringBuilder("");
		deleteSql.append(" DELETE FROM ");

		Iterator<String> tabIterator = tabMap.values().iterator();
		if (tabIterator.hasNext()) {
			deleteSql.append(tabIterator.next());
		}
		deleteSql.append(" WHERE 1 = 1 ");
		Iterator<String> pkIterator = pkMap.values().iterator();
		while (pkIterator.hasNext()) {
			deleteSql.append(" AND " + pkIterator.next() + " = ? ");
		}
		return deleteSql.toString();
	}

	/**
	 * 预填充Update基础操作Sql
	 */
	private static String prepareUpdateSql(Map<String, String> tabMap, Map<String, String> colMap,
			Map<String, String> pkMap) throws Exception {
		StringBuilder updateSql = new StringBuilder("");

		updateSql.append(" UPDATE ");
		Iterator<String> tabName = tabMap.values().iterator();
		if (tabName.hasNext()) {
			updateSql.append(tabName.next());
		}

		updateSql.append(" SET ");
		Iterator<String> colName = colMap.values().iterator();
		while (colName.hasNext()) {
			updateSql.append(colName.next() + " = ?, ");
		}
		updateSql.delete(updateSql.lastIndexOf(","), updateSql.length());

		updateSql.append(" WHERE 1 = 1 ");
		Iterator<String> pkIterator = pkMap.values().iterator();
		while (pkIterator.hasNext()) {
			updateSql.append(" AND " + pkIterator.next() + " = ? ");
		}

		return updateSql.toString();
	}

	/**
	 * 预填充FindByPK基础操作Sql
	 */
	private static String prepareFindByPKSql(Map<String, String> tabMap, Map<String, String> colMap,
			Map<String, String> pkMap) throws Exception {
		StringBuilder findByPKSql = new StringBuilder("");
		findByPKSql.append("SELECT ");
		Iterator<String> colName = colMap.values().iterator();

		while (colName.hasNext()) {
			findByPKSql.append(colName.next() + ",");
		}
		findByPKSql.deleteCharAt(findByPKSql.length() - 1);

		findByPKSql.append(" FROM ");
		Iterator<String> tabName = tabMap.values().iterator();
		if (tabName.hasNext()) {
			findByPKSql.append(tabName.next());
		}

		findByPKSql.append(" WHERE 1 = 1 ");
		Iterator<String> pkIterator = pkMap.values().iterator();
		while (pkIterator.hasNext()) {
			findByPKSql.append(" AND " + pkIterator.next() + " = ? ");
		}

		return findByPKSql.toString();
	}

	/**
	 * 预填充FindAll基础操作Sql
	 */
	private static String prepareFindAllSql(Map<String, String> tabMap, Map<String, String> colMap) throws Exception {
		StringBuilder findAllSql = new StringBuilder("");
		findAllSql.append("SELECT ");
		Iterator<String> colName = colMap.values().iterator();

		while (colName.hasNext()) {
			findAllSql.append(colName.next() + ",");
		}
		findAllSql.deleteCharAt(findAllSql.length() - 1);

		findAllSql.append(" FROM ");
		Iterator<String> tabName = tabMap.values().iterator();
		if (tabName.hasNext()) {
			findAllSql.append(tabName.next());
		}
		return findAllSql.toString();
	}

}
