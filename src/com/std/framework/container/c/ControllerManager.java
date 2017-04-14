package com.std.framework.container.c;

import com.std.framework.container.ContainerManager;
import com.std.framework.controller.aop.AOPCache;
import com.std.framework.controller.aop.AOPValidator;
import com.std.framework.controller.ioc.IOCCache;
import com.std.framework.controller.ioc.IOCValidator;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;


/**
 * @author Luox Controllerģ�������ĳ�ʼ�����
 */
public class ControllerManager extends ContainerManager {

    private static Log logger = LogFactory.getLogger();

    public static void loadCtrlContext () throws Exception {
        loadAOP();
        loadIOC();
    }

    private static void loadAOP () throws Exception {
        logger.debug(">>>>>Stupideer ���װ��... >>>>>>>��֤AOP����...");
        boolean valid = new AOPValidator().valid();
        logger.debug(">>>>>Stupideer ���װ��... >>>>>>>����AOP����...");
        AOPCache aopCache = AOPCache.instance();
        aopCache.loadAOP(valid);
    }

    private static void loadIOC () throws Exception {
        logger.debug(">>>>>Stupideer ���װ��... >>>>>>>��֤IOC����...");
        if (new IOCValidator().valid()) {
            logger.debug(">>>>>Stupideer ���װ��... >>>>>>>����IOC����...");
            IOCCache iocCache = IOCCache.instance();
            iocCache.loadIOC();
        }
    }

}
