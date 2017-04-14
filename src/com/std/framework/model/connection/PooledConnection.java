package com.std.framework.model.connection;

import java.sql.Connection;

/**
 * @author Luox ���ӳ��е�Connection�������Ƿ�Busy(��ʹ��)���� ���ڱ������ӳ������Ӷ������ ��������������Ա��һ�������ݿ�����ӣ���һ����ָʾ�������Ƿ�����ʹ�õı�־��
 */
public class PooledConnection {

    private Connection connection = null;// ���ݿ�����
    private boolean    busy       = false; // �������Ƿ�����ʹ�õı�־��Ĭ��û������ʹ��

    // ���캯��������һ�� Connection ����һ�� PooledConnection ����
    public PooledConnection (Connection connection) {
        this.connection = connection;
    }

    // ���ش˶����е�����
    public Connection getConnection () {
        return connection;
    }

    // ���ô˶��������
    public void setConnection (Connection connection) {
        this.connection = connection;
    }

    // ��ö��������Ƿ�æ
    public boolean isBusy () {
        return busy;
    }

    // ���ö��������æµ״̬
    public void setBusy (boolean busy) {
        this.busy = busy;
    }
}
