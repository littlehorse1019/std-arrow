package com.std.framework.container.m;


import com.std.framework.container.ContainerManager;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.model.connection.AutoReconnection;
import com.std.framework.model.connection.ConnectionsPool;
import com.std.framework.model.orm.ORMStore;
import org.w3c.dom.Node;

/**
 * @author Luox Modelģ�������ĳ�ʼ�����
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
        logger.debug(">>>>>Stupideer ���װ��... >>>>>>>��ʼ�����ݿ����ӳ�...");
        Node dataSourceNode = modelXMLParser.getDataSourceNode();
        ConnectionsPool.instance().loadDataSource(dataSourceNode);
    }

    private static void loadORM() throws Exception {
        logger.debug(">>>>>Stupideer ���װ��... >>>>>>>��ʼ��Ormmaping����...");
        ORMStore.instance().loadORM();
    }
}
