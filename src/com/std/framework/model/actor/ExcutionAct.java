package com.std.framework.model.actor;


import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.std.framework.model.connection.TransactionHolder;
import com.std.framework.model.ormap.ORMStore;
import com.std.framework.model.ormap.Tab2ObjContainer;

public class ExcutionAct<T> {

	private SqlAct<T> sqlAct = null;
	private ResultAct<T> resultAct = null;
	private Tab2ObjContainer tab2ObjContainer = null;

	public ExcutionAct(Class<?> clazz) {
		sqlAct = new SqlAct<T>(clazz);
		resultAct = new ResultAct<T>(clazz);
		tab2ObjContainer = ORMStore.getT2oContainer(clazz.getName());
	}

	/**
	 * 类->数据库表 ，基础增加语句
	 */
	public String excuteSave(Object tInstance) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String pkFromSeq = "";
		try {
			conn = TransactionHolder.getConn();
			String saveSql = sqlAct.createSaveSql();
			pstmt = conn.prepareStatement(saveSql);
			Map<String, String> colMap = tab2ObjContainer.getColMap();
			Map<String, String> pkMap = tab2ObjContainer.getPkMap();
			Iterator<String> filedIterator = colMap.values().iterator();
			for (int index = 1; filedIterator.hasNext(); index++) {
				String fieldName = filedIterator.next();
				if (pkMap.values().contains(fieldName)) {
					pkFromSeq = sqlAct.getPKFromSeq(conn, tInstance);
					pstmt.setString(index, pkFromSeq);
				} else {
					prepareStatementSql(fieldName, tInstance, pstmt, index);
				}
			}
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			pstmt.close();
			TransactionHolder.returnConnection(conn);
		}
		return pkFromSeq;
	}

	/**
	 * 类->数据库表 ，基础删除语句
	 */
	public boolean excuteDelete(Object tInstance) throws SQLException {
		String delSql = sqlAct.createDeleteSql();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = TransactionHolder.getConn();
			pstmt = conn.prepareStatement(delSql);
			Map<String, String> pkMap = tab2ObjContainer.getPkMap();
			Iterator<String> filedIterator = pkMap.values().iterator();
			for (int index = 1; filedIterator.hasNext(); index++) {
				prepareStatementSql(filedIterator.next(), tInstance, pstmt, index);
			}
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			pstmt.close();
			TransactionHolder.returnConnection(conn);
		}

		return true;
	}

	/**
	 * 类->数据库表 ，基础修改语句
	 */
	public boolean excuteUpdate(Object tInstance) throws SQLException {
		String updateSql = sqlAct.createUpdateSql();

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = TransactionHolder.getConn();
			pstmt = conn.prepareStatement(updateSql);
			Map<String, String> colMap = tab2ObjContainer.getColMap();
			Map<String, String> pkMap = tab2ObjContainer.getPkMap();
			Collection<String> colValues = colMap.values();
			Collection<String> pkValues = pkMap.values();
			List<String> paramValues = new LinkedList<String>();
			paramValues.addAll(colValues);
			paramValues.addAll(pkValues);
			Iterator<String> filedIterator = paramValues.iterator();
			for (int index = 1; filedIterator.hasNext(); index++) {
				prepareStatementSql(filedIterator.next(), tInstance, pstmt, index);
			}
			pstmt.execute();
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			pstmt.close();
			TransactionHolder.returnConnection(conn);
		}
		return true;
	}

	/**
	 * 类->数据库表 ，按照主键查询语句
	 */
	public T excuteFindByPK(Object tInstance) throws SQLException {
		T rtnObj = null;
		String findByPKSql = sqlAct.createFindByPKSql();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = TransactionHolder.getConn();
			pstmt = conn.prepareStatement(findByPKSql);
			Map<String, String> pkMap = tab2ObjContainer.getPkMap();
			Iterator<String> filedIterator = pkMap.values().iterator();
			for (int index = 1; filedIterator.hasNext(); index++) {
				prepareStatementSql(filedIterator.next(), tInstance, pstmt, index);
			}
			rs = pstmt.executeQuery();
			rtnObj = resultAct.resultFindByPK(tInstance.getClass(), rs);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			rs.close();
			pstmt.close();
			TransactionHolder.returnConnection(conn);
		}
		return rtnObj;
	}

	/**
	 * 预处理语句?值填充
	 */
	private void prepareStatementSql(String fieldName, Object tInstance, PreparedStatement pstmt, int index)
			throws Exception {
		Field field = tInstance.getClass().getDeclaredField(fieldName);
		if (!field.isAccessible()) {
			field.setAccessible(true);
		}
		Object value = field.get(tInstance);
		String colValue = ColumnAct.castToColumnType(value, field.getType());
		pstmt.setString(index, colValue);
	}

	/**
	 * 类->数据库表 ，查询全部语句
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<T> excuteFindAll(Class tClass) throws SQLException {
		List<T> list = new ArrayList<T>();
		String findAllSql = sqlAct.createFindAllSql(tClass);

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = TransactionHolder.getConn();
			pstmt = conn.prepareStatement(findAllSql);
			rs = pstmt.executeQuery();
			list = resultAct.resultFindAll(tClass, rs);
		} catch (Exception e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			rs.close();
			pstmt.close();
			TransactionHolder.returnConnection(conn);
		}
		return list;
	}
}
