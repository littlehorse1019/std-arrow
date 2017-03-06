package com.std.framework.core.log;

import com.std.framework.core.util.PathUtil;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author Luox servlet启动时读取properties配置，加载log工具初始化设置
 */

public class LogProps {

    private static LogEnum currentLevel = null;
    private static String outStreamTarget = null;
    private static String filePath = null;

    private LogProps() {
    }

    ;

    public static void reloadProp() {
        Properties prop = new Properties();
        try {
            prop.load(new InputStreamReader(new FileInputStream(PathUtil.getRootClassPath() + Log.LOG_OUT_FILE_NAME)));
            currentLevel = LogEnum.getLogLevel(prop.getProperty(Log.LOG_LEVEL).trim());
            outStreamTarget = prop.getProperty(Log.LOG_OUTPUT).trim();
            filePath = prop.getProperty(Log.LOG_FILE_PATH).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LogEnum getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(LogEnum currentLevel) {
        LogProps.currentLevel = currentLevel;
    }

    public static String getOutStreamTarget() {
        return outStreamTarget;
    }

    public static void setOutStreamTarget(String outStreamTarget) {
        LogProps.outStreamTarget = outStreamTarget;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        LogProps.filePath = filePath;
    }

}
