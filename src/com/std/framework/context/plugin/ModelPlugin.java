package com.std.framework.context.plugin;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;


public class ModelPlugin implements ContextPlugin {

    private static Log logger = LogFactory.getLogger();

    @Override
    public void plugin () {
        logger.debug("��������ģ�Ͳ�� : " + this.getClass().getName() + "...");
    }

}
