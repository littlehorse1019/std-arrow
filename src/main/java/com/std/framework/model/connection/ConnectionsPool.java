package com.std.framework.model.connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
import org.w3c.dom.Node;

public class ConnectionsPool {

    private final static Object                   syncLock               = new Object();
    private static       ConnectionsPool          connPool               = null;
    private              String                   jdbcDriver             = ""; // ���ݿ�����
    private              String                   dbUrl                  = ""; // ���� URL
    private              String                   dbUsername             = ""; // ���ݿ��û���
    private              String                   dbPassword             = ""; // ���ݿ��û�����
    private              int                      initialConnections     = 20; // ���ӳصĳ�ʼ��С
    private              int                      incrementalConnections = 10;// ���ӳ��Զ����ӵĴ�С
    private              int                      maxConnections         = 500; // ���ӳ����Ĵ�С
    private              Vector<PooledConnection> connections            = null; // ������ӳ������ݿ����ӵ����� , ��ʼʱΪ null,���д�ŵĶ���Ϊ PooledConnection ��


    private ConnectionsPool () {
    }

    public static ConnectionsPool instance () {
        if (connPool == null) {
            synchronized (syncLock) {
                connPool = new ConnectionsPool();
            }
        }
        return connPool;
    }

    /**
     * ����һ�����ݿ����ӳأ����ӳ��еĿ������ӵ������������Ա initialConnections �����õ�ֵ
     */
    public synchronized void createPool () throws Exception {
        // ȷ�����ӳ�û�д���
        // ������ӳؼ��������ˣ��������ӵ����� connections ����Ϊ��
        if (connections != null) {
            return; // ��������������򷵻�
        }
        // ʵ���� JDBC Driver ��ָ����������ʵ��
        Class.forName(this.jdbcDriver).newInstance();// ע�� JDBC ��������
        // �����������ӵ����� , ��ʼʱ�� 0 ��Ԫ��
        connections = new Vector<>();
        // ���� initialConnections �����õ�ֵ���������ӡ�
        createConnections(this.initialConnections);
    }

    /**
     * ������ numConnections ָ����Ŀ�����ݿ����� , ������Щ���� ���� connections ������
     */
    private void createConnections (int numConnections) throws SQLException {
        // ѭ������ָ����Ŀ�����ݿ�����
        for (int x = 0; x < numConnections; x++) {
            // �Ƿ����ӳ��е����ݿ����ӵ����������ﵽ������ֵ�����Ա maxConnections
            // ָ������� maxConnections Ϊ 0 ��������ʾ��������û�����ơ�
            // ��������������ﵽ��󣬼��˳���
            if (this.maxConnections > 0 && this.connections.size() >= this.maxConnections) {
                break;
            }
            // ����һ�����ӵ����ӳ��У����� connections �У�
            try {
                connections.addElement(new PooledConnection(newConnection()));
            } catch (SQLException e) {
                System.out.println(" �������ݿ�����ʧ�ܣ� " + e.getMessage());
                throw e;
            }
        }

    }

    /**
     * ����һ���µ����ݿ����Ӳ�������
     */
    private Connection newConnection () throws SQLException {
        // ����һ�����ݿ�����
        Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        // ������ǵ�һ�δ������ݿ����ӣ���������ݿ⣬��ô����ݿ�����֧�ֵ����ͻ�������Ŀ   connections.size()==0 ��ʾĿǰû�����Ӽ�������
        if (connections.size() == 0) {
            DatabaseMetaData metaData             = conn.getMetaData();
            int              driverMaxConnections = metaData.getMaxConnections();
            // ���ݿⷵ�ص� driverMaxConnections ��Ϊ 0 ����ʾ�����ݿ�û������������ƣ������ݿ������������Ʋ�֪��
            // driverMaxConnections Ϊ���ص�һ����������ʾ�����ݿ�����ͻ����ӵ���Ŀ
            // ������ӳ������õ�������������������ݿ������������Ŀ , �������ӳص����������ĿΪ���ݿ�����������Ŀ
            if (driverMaxConnections > 0 && this.maxConnections > driverMaxConnections) {
                this.maxConnections = driverMaxConnections;
            }
        }
        return conn; // ���ش������µ����ݿ�����
    }

    /**
     * ͨ������ getFreeConnection() ��������һ�����õ����ݿ����� , �����ǰû�п��õ����ݿ����ӣ����Ҹ�������ݿ����Ӳ��ܴ����������ӳش�С�����ƣ����˺����ȴ�һ���ٳ��Ի�ȡ��
     */
    public synchronized Connection applyConnection () throws SQLException {
        // ȷ�����ӳؼ�������
        if (connections == null) {
            return null; // ���ӳػ�û�������򷵻� null
        }
        Connection conn = applyFreeConnection(); // ���һ�����õ����ݿ�����
        // ���Ŀǰû�п���ʹ�õ����ӣ������е����Ӷ���ʹ����
        while (conn == null) {
            // ��һ������
            wait(250);
            conn = applyFreeConnection(); // �������ԣ�ֱ����ÿ��õ����ӣ����getFreeConnection() ���ص�Ϊ null ���������һ�����Ӻ�Ҳ���ɻ�ÿ�������
        }
        return conn;// ���ػ�õĿ��õ�����
    }

    /**
     * �����������ӳ����� connections �з���һ�����õĵ����ݿ����ӣ������ǰû�п��õ����ݿ����ӣ������������ incrementalConnections
     * ���� ��ֵ�����������ݿ����ӣ����������ӳ��С�
     * ������������е������Զ���ʹ���У��򷵻� null
     */
    private Connection applyFreeConnection () throws SQLException {
        // �����ӳ��л��һ�����õ����ݿ�����
        Connection conn = findFreeConnection();
        if (conn == null) {
            // ���Ŀǰ���ӳ���û�п��õ�����,����һЩ����
            createConnections(incrementalConnections);
            // ���´ӳ��в����Ƿ��п�������
            conn = findFreeConnection();
            if (conn == null) {
                // ����������Ӻ��Ի�ò������õ����ӣ��򷵻� null
                return null;
            }
        }
        return conn;
    }

    /**
     * �������ӳ������е����ӣ�����һ�����õ����ݿ����ӣ� ���û�п��õ����ӣ����� null
     */
    private Connection findFreeConnection () throws SQLException {
        Connection       conn = null;
        PooledConnection pConn;
        // ������ӳ����������еĶ���
        Enumeration<PooledConnection> enumerate = connections.elements();
        // �������еĶ��󣬿��Ƿ��п��õ�����
        while (enumerate.hasMoreElements()) {
            pConn = enumerate.nextElement();
            if (!pConn.isBusy()) {
                // ����˶���æ�������������ݿ����Ӳ�������Ϊæ
                conn = pConn.getConnection();
                pConn.setBusy(true);
                break;
            }
        }
        return conn;// �����ҵ����Ŀ�������
    }

    /**
     * �˺�������һ�����ݿ����ӵ����ӳ��У����Ѵ�������Ϊ���С� ����ʹ�����ӳػ�õ����ݿ����Ӿ�Ӧ�ڲ�ʹ�ô�����ʱ��������
     */
    public void releaseConnection (Connection conn) {
        // ȷ�����ӳش��ڣ��������û�д����������ڣ���ֱ�ӷ���
        if (connections == null) {
            System.out.println(" ���ӳز����ڣ��޷����ش����ӵ����ӳ��� !");
            return;
        }
        PooledConnection              pConn     = null;
        Enumeration<PooledConnection> enumerate = connections.elements();
        // �������ӳ��е��������ӣ��ҵ����Ҫ���ص����Ӷ���
        while (enumerate.hasMoreElements()) {
            pConn = enumerate.nextElement();
            // ���ҵ����ӳ��е�Ҫ���ص����Ӷ���
            if (conn == pConn.getConnection()) {
                // �ҵ��� , ���ô�����Ϊ����״̬
                pConn.setBusy(false);
                break;
            }
        }
    }

    /**
     * ˢ�����ӳ������е����Ӷ���
     */
    public synchronized void refreshConnections () throws SQLException {
        // ȷ�����ӳؼ����´���
        if (connections == null) {
            System.out.println(" ���ӳز����ڣ��޷�ˢ�� !");
            return;
        }
        PooledConnection              pConn     = null;
        Enumeration<PooledConnection> enumerate = connections.elements();
        while (enumerate.hasMoreElements()) {
            // ���һ�����Ӷ���
            pConn = enumerate.nextElement();
            // �������æ��� 5 �� ,5 ���ֱ��ˢ��
            if (pConn.isBusy()) {
                wait(5000); // �� 5 ��
            }
            // �رմ����ӣ���һ���µ����Ӵ�������
            closeConnection(pConn.getConnection());
            pConn.setConnection(newConnection());
            pConn.setBusy(false);
        }
    }

    /**
     * �ر����ӳ������е����ӣ���������ӳء�
     */
    public synchronized void closeConnectionPool () throws SQLException {
        // ȷ�����ӳش��ڣ���������ڣ�����
        if (connections == null) {
            System.out.println(" ���ӳز����ڣ��޷��ر� !");
            return;
        }
        PooledConnection              pConn     = null;
        Enumeration<PooledConnection> enumerate = connections.elements();
        while (enumerate.hasMoreElements()) {
            pConn = enumerate.nextElement();
            // ���æ���� 5 ��
            if (pConn.isBusy()) {
                wait(5000); // �� 5 ��
            }
            // 5 ���ֱ�ӹر���
            closeConnection(pConn.getConnection());
            // �����ӳ�������ɾ����
            connections.removeElement(pConn);
        }
        // �����ӳ�Ϊ��
        connections = null;
    }

    /**
     * �ر�һ�����ݿ�����
     */
    private void closeConnection (Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(" �ر����ݿ����ӳ��� " + e.getMessage());
        }
    }

    /**
     * ʹ����ȴ������ĺ�����
     */

    private void wait (int mSeconds) {
        try {
            Thread.sleep(mSeconds);
        } catch (InterruptedException e) {
        }
    }

    /**
     * �������ӳصĳ�ʼ��С
     */

    public int getInitialConnections () {
        return this.initialConnections;
    }

    /**
     * �������ӳصĳ�ʼ��С
     */

    public void setInitialConnections (int initialConnections) {
        this.initialConnections = initialConnections;
    }

    /**
     * �������ӳ��Զ����ӵĴ�С
     */

    public int getIncrementalConnections () {
        return this.incrementalConnections;
    }

    /**
     * �������ӳ��Զ����ӵĴ�С
     */

    public void setIncrementalConnections (int incrementalConnections) {
        this.incrementalConnections = incrementalConnections;
    }

    /**
     * �������ӳ������Ŀ�����������
     */

    public int getMaxConnections () {
        return this.maxConnections;
    }

    /**
     * �������ӳ��������õ���������
     */
    public void setMaxConnections (int maxConnections) {
        this.maxConnections = maxConnections;
    }

    /**
     * ��ȡ���ݿ������ļ��������Ӳ������浽��̬������
     */
    private void initConnPoolDataSource (Node dataSourceNode) throws Exception {
        DataSourceInfo.init(dataSourceNode);
        dbUsername = DataSourceInfo.getUser();
        dbPassword = DataSourceInfo.getPassword();
        jdbcDriver = DataSourceInfo.getDriver();
        dbUrl = DataSourceInfo.getUrl();
        if (DataSourceInfo.getPoolsize() > 0) {
            initialConnections = DataSourceInfo.getPoolsize();
        }
    }

    public void loadDataSource (Node dataSourceNode) throws Exception {
        initConnPoolDataSource(dataSourceNode);
        createPool();
    }

    public int getCurrentPoolSize () {
        return connections.size();
    }

    public int getCurrentFreeConnPool () {
        int freeSize = 0;
        for (PooledConnection pooledConn : connections) {
            if (!pooledConn.isBusy()) {
                freeSize++;
            }
        }
        return freeSize;
    }


}
