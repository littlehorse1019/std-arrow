package com.std.framework.model.connection;

import java.sql.SQLException;

public class Transaction {

    public static Transaction getCurrentTransaction () {
        return new Transaction();
    }

    /**
     * �������񣬱��汾��Theadlocal Connection
     */
    public void begin () {
        try {
            TransactionHolder.beginConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * �ύ�����ͷű���Theadlocal Connection
     */
    public void commit () {
        try {
            TransactionHolder.commitConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
