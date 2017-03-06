package com.std.framework.model.connection;

import java.sql.SQLException;

public class Transaction {

    public static Transaction getCurrentTransaction() {
        return new Transaction();
    }

    /**
     * 开启事务，保存本地Theadlocal Connection
     */
    public void begin() {
        try {
            TransactionHolder.beginConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务，释放本地Theadlocal Connection
     */
    public void commit() {
        try {
            TransactionHolder.commitConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
