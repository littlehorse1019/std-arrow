package com.std.framework.core.log;

import java.lang.reflect.Field;

/**
 * @author Luox ��־��������ö���࣬��־�������levelNum���й��ˣ����ڵ�ǰlevel�Ķ����
 */

public enum LogEnum {

    ALL(1), DEBUG(2), INFO(3), WARN(4), ERROR(5), OFF(6);

    // ����˽�б���
    private int levelNum;

    // ���캯����ö������ֻ��Ϊ˽��
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
            throw new Exception("������ָ����Level����(ALL,ERROR,WARN,INFO,DEBUG,OFF)");
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
