package com.std.framework.context;

import com.std.framework.container.BaseContainerXMLParser;
import com.std.framework.container.ContainerManager;
import com.std.framework.container.c.ControllerManager;
import com.std.framework.container.m.ModelManager;
import com.std.framework.container.v.ViewManager;
import com.std.framework.context.plugin.ControllerPlugin;
import com.std.framework.context.plugin.ModelPlugin;
import com.std.framework.context.plugin.PluginBox;
import com.std.framework.context.plugin.ViewPlugin;
import com.std.framework.controller.timer.JobExcutor;
import com.std.framework.core.log.LogFactory;
import com.std.framework.core.xml.XMLValidator;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Luox std���������ĳ�ʼ����ڣ�ContextLoaderListener
 */
public class ContextLoaderListener implements ServletContextListener {

    private final String CONFIG_FILE_PROPERTY = "config";

    public void contextInitialized (ServletContextEvent sec) {
        try {

            writeContextLog("Std��ܳ�ʼ�����ؿ�ʼ ");

            initSysEnv(sec);

            initMVCContext(sec);

            initScheduleJobs(sec);

            initPlugins(sec);

            writeContextLog("Std��ܳ�ʼ�����ؽ��� ");

        } catch (Exception e) {
            e.printStackTrace();
            this.contextDestroyed(sec);
        }
    }

    public void contextDestroyed (ServletContextEvent sec) {
        System.out.println("ϵͳ��ֹ������쳣��Std Context�Զ�����..");
        System.exit(0);
    }

    private void initSysEnv (ServletContextEvent sec) {
        ContainerManager.loadSysParams();
        LogFactory.loadProperties();
        ClassScanner.loadProperties();
    }

    private void initMVCContext (ServletContextEvent sec) throws Exception {
        validConfig(sec);
        ModelManager.loadModelContext();
        ControllerManager.loadCtrlContext();
        ViewManager.loadViewContext();
    }

    private void validConfig (ServletContextEvent sec) throws Exception {
        String configFilePath = (String) sec.getServletContext().getAttribute(CONFIG_FILE_PROPERTY);
        BaseContainerXMLParser.setConfigResource(configFilePath);
        boolean valid = XMLValidator.validMVCConfig();
        if (!valid) {
            throw new Exception(BaseContainerXMLParser.getConfigResource() + "�����ļ���ʽУ�����!");
        }
    }

    private void initScheduleJobs (ServletContextEvent sec) {
        JobExcutor.runJobs();
    }

    private void initPlugins (ServletContextEvent sec) {
        PluginBox.addPlugin(new ModelPlugin());
        PluginBox.addPlugin(new ControllerPlugin());
        PluginBox.addPlugin(new ViewPlugin());
        PluginBox.runPlugins();
    }

    private void writeContextLog (String logStr) {
        System.out.println("========================================================");
        System.out.println("======       " + logStr + " ��" + System.currentTimeMillis() + "       ======");
        System.out.println("========================================================");
    }

}
