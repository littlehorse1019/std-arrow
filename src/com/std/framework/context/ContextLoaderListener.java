package com.std.framework.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.std.framework.container.ContainerManager;
import com.std.framework.container.ContainerXMLParser;
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

/**
 * @author Luox std容器上下文初始化入口，ContextLoaderListener
 */
public class ContextLoaderListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sec) {
		try {

			writeContextLog("Std框架初始化加载开始 ");

			initSysEnv(sec);

			initMVCContext(sec);

			initScheduleJobs(sec);

			initPlugins(sec);

			writeContextLog("Std框架初始化加载结束 ");

		} catch (Exception e) {
			e.printStackTrace();
			this.contextDestroyed(sec);
		}
	}

	public void contextDestroyed(ServletContextEvent sec) {
		System.out.println("系统终止或出现异常，Std Context自动销毁..");
		System.exit(0);
	}

	
	private void initSysEnv(ServletContextEvent sec) {
		ContainerManager.loadSysParams();
		LogFactory.loadProperties();
		ClassScanner.loadProperties();
	}

	
	private void initMVCContext(ServletContextEvent sec) throws Exception {
		validConfig(sec);
		ModelManager.loadModelContext();
		ControllerManager.loadCtrlContext();
		ViewManager.loadViewContext();
	}

	
	private void validConfig(ServletContextEvent sec) throws Exception {
		String configFilePath = (String) sec.getServletContext().getAttribute(CONFIG_FILE_PROPERTY);
		ContainerXMLParser.setConfigResource(configFilePath);
		boolean valid = XMLValidator.validMVCConfig();
		if (!valid) {
			throw new Exception(ContainerXMLParser.getConfigResource() + "配置文件格式校验错误!");
		}
	}

	
	private void initScheduleJobs(ServletContextEvent sec) {
		JobExcutor.runJobs();
	}

	
	private void initPlugins(ServletContextEvent sec) {
		PluginBox.addPlugin(new ModelPlugin());
		PluginBox.addPlugin(new ControllerPlugin());
		PluginBox.addPlugin(new ViewPlugin());
		PluginBox.runPlugins();
	}

	
	private void writeContextLog(String... logStr) {
		System.out.println("========================================================");
		System.out.println("======       " + logStr + " ：" + System.currentTimeMillis() + "       ======");
		System.out.println("========================================================");
	}
	
	
	private final String CONFIG_FILE_PROPERTY = "config";
	
}