package com.std.framework.context.plugin;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;


public class ControllerPlugin implements ContextPlugin {

    private static Log logger = LogFactory.getLogger();

    @Override
    public void plugin () {
        logger.debug("���ؿ�������� : " + this.getClass().getName() + "...");
    }

}
