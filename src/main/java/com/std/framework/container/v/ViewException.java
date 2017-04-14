package com.std.framework.container.v;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;

@SuppressWarnings ("serial")
public class ViewException extends RuntimeException {

    private static final Log logger = LogFactory.getLogger();

    public ViewException () {
        super();
        logger.error("");
    }

    public ViewException (String message) {
        super(message);
    }

    public ViewException (Throwable cause) {
        super(cause);
    }

    public ViewException (String message, Throwable cause) {
        super(message, cause);
    }
}
