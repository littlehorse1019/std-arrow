package com.std.framework.container.c;

import com.std.framework.container.ContainerManager;
import com.std.framework.controller.aop.AOPCache;
import com.std.framework.controller.aop.AOPValidator;
import com.std.framework.controller.ioc.IOCCache;
import com.std.framework.controller.ioc.IOCValidator;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;


/**
 * @author Luox Controller模块上下文初始化入口
 */
public class ControllerManager extends ContainerManager {

	private static Log logger = LogFactory.getLogger();

	public static void loadCtrlContext() throws Exception {
		loadAOP();
		loadIOC();
	}

	private static void loadAOP() throws Exception {
		logger.debug(">>>>>Stupideer 框架装载... >>>>>>>验证AOP配置...");
		boolean valid = new AOPValidator().valid();
		logger.debug(">>>>>Stupideer 框架装载... >>>>>>>加载AOP配置...");
		AOPCache aopCache = AOPCache.instance();
		aopCache.loadAOP(valid);
	}
	
	private static void loadIOC() throws Exception {
		logger.debug(">>>>>Stupideer 框架装载... >>>>>>>验证IOC配置...");
		if (new IOCValidator().valid()) {
			logger.debug(">>>>>Stupideer 框架装载... >>>>>>>加载IOC配置...");
			IOCCache iocCache = IOCCache.instance();
			iocCache.loadIOC();
		}
	}

}
