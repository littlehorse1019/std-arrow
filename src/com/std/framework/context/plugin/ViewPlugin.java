package com.std.framework.context.plugin;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;


public class ViewPlugin implements ContextPlugin {

    private static Log logger = LogFactory.getLogger();

    @Override
    public void plugin () {
        logger.debug("º”‘ÿœ‘ æ≤„≤Âº˛ : " + this.getClass().getName() + "...");
    }

}
