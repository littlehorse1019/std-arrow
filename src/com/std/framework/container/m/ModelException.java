package com.std.framework.container.m;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;

@SuppressWarnings("serial")
public class ModelException extends RuntimeException {

    private static final Log logger = LogFactory.getLogger();

    public ModelException() {
        super();
        logger.error("");
    }

    public ModelException(String message) {
        super(message);
    }

    public ModelException(Throwable cause) {
        super(cause);
    }

    public ModelException(String message, Throwable cause) {
        super(message, cause);
    }
}
