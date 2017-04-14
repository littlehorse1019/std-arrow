package com.std.framework.model.connection;


import com.std.framework.container.m.ModelXMLConstants;
import org.w3c.dom.Node;

/**
 * @author Luox 数据源基础属性
 */
public class DataSourceInfo {

    private static String user;
    private static String password;
    private static String driver;
    private static String url;
    private static int    poolsize;

    public static String getUser () {
        return user;
    }

    public static void setUser (String user) {
        DataSourceInfo.user = user;
    }

    public static String getPassword () {
        return password;
    }

    public static void setPassword (String password) {
        DataSourceInfo.password = password;
    }

    public static String getDriver () {
        return driver;
    }

    public static void setDriver (String driver) {
        DataSourceInfo.driver = driver;
    }

    public static String getUrl () {
        return url;
    }

    public static void setUrl (String url) {
        DataSourceInfo.url = url;
    }

    public static int getPoolsize () {
        return poolsize;
    }

    public static void setPoolsize (int poolsize) {
        DataSourceInfo.poolsize = poolsize;
    }

    /**
     * @return 读取数据库连接文件，将连接参数保存到静态变量中
     * @author Luox
     */
    public static void init (Node dataSourceNode) throws Exception {

        try {
            for (Node node = dataSourceNode.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    String name  = node.getNodeName();
                    String value = node.getFirstChild().getNodeValue();
                    if (ModelXMLConstants.USER.equalsIgnoreCase(name)) {
                        setUser(value);
                    } else if (ModelXMLConstants.PASSWORD.equals(name)) {
                        setPassword(value);
                    } else if (ModelXMLConstants.DRIVER.equalsIgnoreCase(name)) {
                        setDriver(value);
                    } else if (ModelXMLConstants.URL.equalsIgnoreCase(name)) {
                        setUrl(value);
                    } else if (ModelXMLConstants.POOLSIZE.equalsIgnoreCase(name)) {
                        setPoolsize(Integer.parseInt(value));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
