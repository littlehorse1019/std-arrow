package com.std.framework.core.log;

/**
 * @author Luox Log工具方法接口
 */

public interface Log {

    public static final String FILE_OUT_PUT      = "file";
    public static final String CONSOLE_OUT_PUT   = "console";
    public static final String LOG_OUT_FILE_NAME = "log.properties";
    public static final String LOG_LEVEL         = "level";
    public static final String LOG_OUTPUT        = "output";
    public static final String LOG_FILE_PATH     = "filepath";

    public void info (Object msgObj);

    public void warn (Object msgObj);

    public void error (Object msgObj);

    public void debug (Object msgObj);

    public void all (Object msgObj);

}
