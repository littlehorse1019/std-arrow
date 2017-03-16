package com.std.framework.model;

import com.std.framework.model.connection.TransactionHolder;
import com.std.framework.model.orm.MapRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Luox SQL�Զ������������
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ModelHelper {

    /**
     * ��Sql���ҵĽ����ŵ���T�У�ӳ���ϵ��Ormmap�д洢��Ϊ׼
     */
    public static <T> List<T> findClassListBySql(T t, MapRule maprule, String sql, List parameters) {
        Class<T> c = (Class<T>) t.getClass();
        ResultSet rs = null;
        try {
            rs = excuteQuerySql(sql, parameters);
            return ModelBuilder.buildClassResult(rs, c, maprule);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.getStatement().close();
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList();
    }

    /**
     * ��Sql���ҵĽ����ŵ���T�У�ӳ���ϵ��Ormmap�д洢��Ϊ׼
     */
    public static <T> List<T> findEntityListBySql(T t, String sql, List parameters) {
        Class<T> c = (Class<T>) t.getClass();
        ModelBuilder.checkOrm(c);
        ResultSet rs = null;
        try {
            rs = excuteQuerySql(sql, parameters);
            return ModelBuilder.buildClassResult(rs, c);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.getStatement().close();
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList();
    }

    /**
     * ��Sql���ҵĽ����ŵ�List<Map>�У�
     */
    public static List<Map<String, Object>> findMapListBySql(String sql, List parameters) {
        try (ResultSet rs = excuteQuerySql(sql, parameters)) {
            return ModelBuilder.buildMapResult(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * ִ��DML���
     */
    public static boolean excuteDMLSql(String sql, List parameters) throws SQLException {
        Connection conn = null;
        try {
            conn = TransactionHolder.getConn();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                if (parameters != null) {
                    for (int i = 0; i < parameters.size(); i++) {
                        pstmt.setObject(i + 1, parameters.get(i));
                    }
                }
                return pstmt.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null)
                conn.rollback();
        } finally {
            TransactionHolder.releaseConnection(conn);
        }
        return true;
    }

    private static ResultSet excuteQuerySql(String sql, List parameters) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt;
        ResultSet rs = null;
        try {
            conn = TransactionHolder.getConn();
            pstmt = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.size(); i++) {
                    pstmt.setObject(i + 1, parameters.get(i));
                }
            }
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null)
                conn.rollback();
        } finally {
            TransactionHolder.releaseConnection(conn);
        }
        return rs;
    }

}
