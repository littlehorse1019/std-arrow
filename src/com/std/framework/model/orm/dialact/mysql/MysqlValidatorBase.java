package com.std.framework.model.orm.dialact.mysql;

import com.std.framework.model.connection.ConnectionsPool;
import com.std.framework.model.orm.BaseORMValidator;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/2/28.
 */
public class MysqlValidatorBase extends BaseORMValidator {

    public boolean validTab(String tableName) throws Exception {
        Connection conn = ConnectionsPool.instance().applyConnection();
        // �齨����Sql
        StringBuilder testSql = new StringBuilder();
        testSql.append(" SELECT * FROM INFORMATION_SCHEMA.TABLES T WHERE T.TABLE_NAME = '");
        testSql.append(tableName);
        testSql.append("'");
        // ִ�в���Sql
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(testSql.toString());
            if (!rs.next()) {
                throw new Exception("��" + tableName + "��������!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
        }
        ConnectionsPool.instance().releaseConnection(conn);
        return true;
    }

    public boolean validSeq(Map<String, String> seqMap) throws Exception {
        return true;
    }

    public boolean validCol(String tableName, Map<String, String> colMap) throws Exception {
        Connection conn = ConnectionsPool.instance().applyConnection();
        // �齨����Sql
        StringBuilder testSql = new StringBuilder();
        testSql.append(" SELECT * FROM INFORMATION_SCHEMA.COLUMNS T WHERE T.TABLE_NAME = '");
        testSql.append(tableName);
        testSql.append("'");
        // ִ�в���Sql
        List<String> colArray = new ArrayList<String>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(testSql.toString());
            while (rs.next()) {
                colArray.add(rs.getString("COLUMN_NAME").toLowerCase());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rs.close();
            stmt.close();
        }

        Iterator<String> colIterator = colMap.values().iterator();
        while (colIterator.hasNext()) {
            String colName = colIterator.next().toLowerCase();
            if (!colArray.contains(colName)) {
                throw new Exception("��" + tableName + "��û���ҵ�����Ϊ" + colName + "����!");
            }
        }
        ConnectionsPool.instance().releaseConnection(conn);
        return true;
    }
}
