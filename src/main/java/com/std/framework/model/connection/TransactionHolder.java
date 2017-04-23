package com.std.framework.model.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Luox 数据库事务关联conn持有者
 */

public class TransactionHolder {

    private static final Integer                 txRunning        = 1;
    private static final Integer                 txFinished       = 0;
    private static       ThreadLocal<Connection> threadConnection = new ThreadLocal<>();
    private static       ThreadLocal<Integer>    transStatus      = new ThreadLocal<>();

    private TransactionHolder () {
    }

    public static void beginConnection () throws SQLException {
        Connection connection = ConnectionsPool.instance().applyConnection();
        connection.setAutoCommit(false);
        threadConnection.set(connection);
        transStatus.set(txRunning);
    }

    public static void commitConnection () throws SQLException {
        Connection connection = threadConnection.get();
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback();
            } finally {
                ConnectionsPool.instance().releaseConnection(connection);
            }
            threadConnection.remove();
            transStatus.set(txFinished);
        }
    }

    /**
     * 获取数据库连接池中的conn,设置autoCommit类型并加入到ThreadLocal中
     */
    public static Connection getConn () throws Exception {
        Connection conn = threadConnection.get();
        if (conn == null) {
            conn = ConnectionsPool.instance().applyConnection();
            conn.setAutoCommit(true);
        }
        return conn;
    }

    /**
     * 释放数据库连接，鉴别是否在事务执行当中
     */
    public static void releaseConnection (Connection connection) {
        if (transStatus.get() == null || transStatus.get() == txFinished) {
            ConnectionsPool.instance().releaseConnection(connection);
        }
    }

}
