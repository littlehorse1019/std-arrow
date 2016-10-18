package com.std.framework.model.actor;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.model.ormap.ORMStore;
import com.std.framework.model.ormap.Obj2TabContainer;

@SuppressWarnings("rawtypes")
public class SqlAct<T> {

	Log log = LogFactory.getLogger();

	Class clazz = null;
	private Obj2TabContainer obj2TabContainer = null;

	public SqlAct(Class clazz) {
		this.clazz = clazz;
		obj2TabContainer = ORMStore.getO2tContainer(clazz.getName());
	}

	/** 从ORMMAPING中获取基础语句，继续拼装成插入语句 **/
	public String createSaveSql() {
		String saveSql = "";
		saveSql = obj2TabContainer.getBaseSqlMap().get(BaseSqlEnum.Save.toString());
		log.debug(clazz.getName() + "==>saveSql:" + saveSql);
		return saveSql;
	}

	/** 从ORMMAPING中获取基础语句，继续拼装成删除语句 **/
	public String createDeleteSql() {
		String deleteSql = "";
		deleteSql = obj2TabContainer.getBaseSqlMap().get(BaseSqlEnum.Delete.toString());
		log.debug(clazz.getName() + "==>deleteSql:" + deleteSql);
		return deleteSql;
	}

	/** 从ORMMAPING中获取基础语句，继续拼装成更新语句 **/
	public String createUpdateSql() {
		String updateSql = "";
		updateSql = obj2TabContainer.getBaseSqlMap().get(BaseSqlEnum.Update.toString());
		log.debug(clazz.getName() + "==>updateSql:" + updateSql);
		return updateSql;
	}

	/** 从ORMMAPING中获取根据主键查询基础语句 **/
	public String createFindByPKSql() {
		String findByPKSql = "";
		findByPKSql = obj2TabContainer.getBaseSqlMap().get(BaseSqlEnum.FindByPK.toString());
		log.debug(clazz.getName() + "==>findByPKSql:" + findByPKSql);
		return findByPKSql;
	}

	/** 从ORMMAPING中获取查询全部基础语句 **/
	public String createFindAllSql(Class<? extends ModelAct> tClass) {
		String findAllSql = "";
		findAllSql = obj2TabContainer.getBaseSqlMap().get(BaseSqlEnum.FindAll.toString());
		log.debug(clazz.getName() + "==>findAllSql:" + findAllSql);
		return findAllSql;
	}

	public String getPKFromSeq(Connection conn, Object tInstance) throws SQLException {
		String seqName = obj2TabContainer.getSeqMap().get(tInstance.getClass().getSimpleName());
		Statement stmt = null;
		ResultSet rs = null;
		String primaryKey = "";
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT " + seqName + ".NEXTVAL FROM DUAL");
			if (rs.next()) {
				primaryKey = rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			stmt.close();
		}
		return primaryKey;
	}
}
