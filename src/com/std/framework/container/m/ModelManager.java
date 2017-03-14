package com.std.framework.container.m;


import com.std.framework.container.ContainerManager;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.model.connection.AutoReconnection;
import com.std.framework.model.connection.ConnectionsPool;
import com.std.framework.model.orm.ORMStore;
import org.w3c.dom.Node;

/**
 * @author Luox Model模块上下文初始化入口
 */
public class ModelManager extends ContainerManager {

    private static final ModelXMLParserBase modelXMLParser = new ModelXMLParserBase();
    private static Log logger = LogFactory.getLogger();

    public static void loadModelContext() throws Exception {
        loadConnectionPool();
        loadORM();
        new AutoReconnection().holdConnection();
    }

    private static void loadConnectionPool() throws Exception {
        logger.debug(">>>>>Stupideer 框架装载... >>>>>>>初始化数据库连接池...");
        Node dataSourceNode = modelXMLParser.getDataSourceNode();
        ConnectionsPool.instance().loadDataSource(dataSourceNode);
    }

    private static void loadORM() throws Exception {
        logger.debug(">>>>>Stupideer 框架装载... >>>>>>>初始化Ormmaping对象...");
        ORMStore.instance().loadORM();
    }
}
