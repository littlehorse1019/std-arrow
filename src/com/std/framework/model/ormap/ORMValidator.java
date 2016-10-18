package com.std.framework.model.ormap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.std.framework.model.connection.ConnectionsPool;

/**
 * @author Luox ORMMAPING校验器，验证表，序列，字段是否在数据库中存在
 */
public class ORMValidator {

	private Map<String, String> tabMap = null;
	private Map<String, String> colMap = null;
	private Map<String, String> seqMap = null;

	private void initMaps(Obj2TabContainer obj2Tab) {
		this.tabMap = obj2Tab.getTabMap();
		this.seqMap = obj2Tab.getSeqMap();
		this.colMap = obj2Tab.getColMap();
	}

	/**
	 * 连通数据库从USER_TABLES,USER_SEQUENCES,USER_TAB_COLUMNS来判断表名， 序列和表列属性名是否正确。
	 */
	public boolean validOrmmap(Obj2TabContainer obj2Tab) throws Exception {
		try {
			initMaps(obj2Tab);
			validTab(tabMap);
			validSeq(seqMap);
			validCol(tabMap, colMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	private void validTab(Map<String, String> tabMap) throws Exception {
		Connection conn = ConnectionsPool.instance().getConnection();
		// 组建测试Sql
		StringBuilder testSql = new StringBuilder();
		testSql.append(" SELECT * FROM USER_TABLES T WHERE T.TABLE_NAME = '");
		Iterator<String> tabName = tabMap.values().iterator();
		String tableName = "";
		if (tabName.hasNext()) {
			tableName = tabName.next();
			testSql.append(tableName);
		} else {
			throw new Exception("该表创建ORMMAPING映射关系失败!");
		}
		testSql.append("'");
		// 执行测试Sql
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(testSql.toString());
			if (!rs.next()) {
				throw new Exception("表" + tableName + "并不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
		}
		ConnectionsPool.instance().returnConnection(conn);
	}

	private void validSeq(Map<String, String> seqMap) throws Exception {
		Connection conn = ConnectionsPool.instance().getConnection();
		// 组建测试Sql
		StringBuilder testSql = new StringBuilder();
		testSql.append(" SELECT * FROM USER_SEQUENCES T WHERE T.SEQUENCE_NAME = '");
		Iterator<String> seqName = seqMap.values().iterator();
		String sequenceName = "";
		if (seqName.hasNext()) {
			sequenceName = seqName.next();
			testSql.append(sequenceName);
		} else {
			throw new Exception("该表创建ORMMAPING映射关系失败!");
		}
		testSql.append("'");
		// 执行测试Sql
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(testSql.toString());
			if (!rs.next()) {
				throw new Exception("序列" + sequenceName + "并不存在!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
		}
		ConnectionsPool.instance().returnConnection(conn);
	}

	private void validCol(Map<String, String> tabMap, Map<String, String> colMap) throws Exception {
		Connection conn = ConnectionsPool.instance().getConnection();
		// 组建测试Sql
		StringBuilder testSql = new StringBuilder();
		testSql.append(" SELECT * FROM USER_TAB_COLUMNS T WHERE T.TABLE_NAME = '");
		Iterator<String> tabName = tabMap.values().iterator();
		String tableName = "";
		if (tabName.hasNext()) {
			tableName = tabName.next();
			testSql.append(tableName);
		} else {
			throw new Exception("该表创建ORMMAPING映射关系失败!");
		}
		testSql.append("'");
		// 执行测试Sql
		List<String> colArray = new ArrayList<String>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(testSql.toString());
			while (rs.next()) {
				colArray.add(rs.getString("COLUMN_NAME"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
		}

		Iterator<String> colIterator = colMap.values().iterator();
		while (colIterator.hasNext()) {
			String colName = colIterator.next();
			if (!colArray.contains(colName)) {
				throw new Exception("表" + tableName + "中没有找到列名为" + colName + "的列!");
			}
		}
		ConnectionsPool.instance().returnConnection(conn);
	}

}
