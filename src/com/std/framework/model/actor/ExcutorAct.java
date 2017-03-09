package com.std.framework.model.actor;


import com.std.framework.core.util.ReflectionUtil;
import com.std.framework.model.connection.TransactionHolder;
import com.std.framework.model.orm.ORMStore;
import com.std.framework.model.orm.Tab2ObjContainer;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExcutorAct<T> {

    private SqlAct sqlAct = null;
    private ResultAct<T> resultAct = null;
    private Tab2ObjContainer t2oContainer = null;

    ExcutorAct(Class<?> clazz) {
        sqlAct = new SqlAct(clazz);
        resultAct = new ResultAct<>(clazz);
        t2oContainer = ORMStore.getT2oContainer(clazz.getName());
    }

    /**
     * 类->数据库表 ，基础增加语句
     */
    T excuteSave(T entity) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = TransactionHolder.getConn();
            String saveSql = sqlAct.createSaveSql();
            pstmt = conn.prepareStatement(saveSql);
            Map<String, String> fieldsName = t2oContainer.getFieldNames();
            Iterator<String> filedIterator = fieldsName.values().iterator();
            for (int index = 1; filedIterator.hasNext(); index++) {
                String fieldName = filedIterator.next();
                prepareStatementSql(fieldName, entity, pstmt, index);
            }
            pstmt.execute();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                Object primaryKey = rs.getObject(1);
                ReflectionUtil.setFieldForObject(entity, t2oContainer.getPrimaryKeyName(), primaryKey);
            }
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            pstmt.close();
            TransactionHolder.releaseConnection(conn);
        }
        return entity;
    }

    /**
     * 类->数据库表 ，基础删除语句
     */
    boolean excuteDelete(Object tInstance) throws SQLException {
        String delSql = sqlAct.createDeleteSql();

        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = TransactionHolder.getConn();
            pstmt = conn.prepareStatement(delSql);
            String primaryKeyName = t2oContainer.getPrimaryKeyName();
            prepareStatementSql(primaryKeyName, tInstance, pstmt, 1);
            pstmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            pstmt.close();
            TransactionHolder.releaseConnection(conn);
        }

        return true;
    }

    /**
     * 类->数据库表 ，基础修改语句
     */
    boolean excuteUpdate(Object tInstance) throws SQLException {
        String updateSql = sqlAct.createUpdateSql();

        Connection conn = null;
        try {
            conn = TransactionHolder.getConn();
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                Map<String, String> fieldNames = t2oContainer.getFieldNames();
                String primaryKeyName = t2oContainer.getPrimaryKeyName();
                Collection<String> fields = fieldNames.values();
                List<String> paramValues = new LinkedList<>();
                paramValues.addAll(fields);
                paramValues.add(primaryKeyName);
                Iterator<String> filedIterator = paramValues.iterator();
                for (int index = 1; filedIterator.hasNext(); index++) {
                    prepareStatementSql(filedIterator.next(), tInstance, pstmt, index);
                }
                pstmt.execute();
            }
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
        } finally {
            TransactionHolder.releaseConnection(conn);
        }
        return true;
    }

    /**
     * 类->数据库表 ，按照主键查询语句
     */
    T excuteFindByPK(T tInstance) throws SQLException {
        T rtnObj = null;
        String findByPKSql = sqlAct.createFindByPKSql();
        Connection conn = null;
        try {
            conn = TransactionHolder.getConn();
            try (PreparedStatement pstmt = conn.prepareStatement(findByPKSql)) {
                String primaryKeyName = t2oContainer.getPrimaryKeyName();
                prepareStatementSql(primaryKeyName, tInstance, pstmt, 1);
                try (ResultSet rs = pstmt.executeQuery()) {
                    rtnObj = resultAct.resultFindByPK(tInstance.getClass(), rs);
                }
            }
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
        } finally {
            TransactionHolder.releaseConnection(conn);
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
    List<T> excuteFindAll(Class tClass) throws SQLException {
        List<T> list = new ArrayList<>();
        String findAllSql = sqlAct.createFindAllSql();
        Connection conn = null;
        try {
            conn = TransactionHolder.getConn();
            try (PreparedStatement pstmt = conn.prepareStatement(findAllSql);
                 ResultSet rs = pstmt.executeQuery()) {
                list = resultAct.resultFindAll(tClass, rs);
            }
        } catch (Exception e) {
            if (conn != null && !conn.getAutoCommit()) {
                conn.rollback();
            }
            e.printStackTrace();
        } finally {
            TransactionHolder.releaseConnection(conn);
        }
        return list;
    }
}
