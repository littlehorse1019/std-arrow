package com.std.framework.core.log;

import java.lang.reflect.Field;

/**
 * @author Luox 日志级别设置枚举类，日志输出按照levelNum进行过滤，高于当前level的都输出
 */

public enum LogEnum {

    ALL(1), DEBUG(2), INFO(3), WARN(4), ERROR(5), OFF(6);

    // 定义私有变量
    private int levelNum;

    // 构造函数，枚举类型只能为私有
    private LogEnum (int levelNum) {
        this.levelNum = levelNum;
    }

    public static LogEnum getLogLevel (String level) throws Exception {
        Field[]   declaredFields = LogEnum.class.getDeclaredFields();
        LogEnum[] enumNameArray  = LogEnum.values();
        LogEnum   logEnum        = null;
        for (int i = 0; i < declaredFields.length; i++) {
            Field field = declaredFields[i];
            if (field.getType() == LogEnum.class && field.getName().equals(level)) {
                logEnum = enumNameArray[i];
                break;
            }
        }
        if (logEnum != null) {
            return logEnum;
        } else {
            throw new Exception("不存在指定的Level名称(ALL,ERROR,WARN,INFO,DEBUG,OFF)");
        }
    }

    public int getLevelNum () {
        return levelNum;
    }

    public String toString (LogEnum le) {
        switch (le) {
            case DEBUG:
                return "[DEBUG]";
            case INFO:
                return "[INFO]";
            case WARN:
                return "[WARN]";
            case ERROR:
                return "[ERROR]";
            case ALL:
                return "[ALL]";
            default:
                return "[LOG]";
        }
    }
}
