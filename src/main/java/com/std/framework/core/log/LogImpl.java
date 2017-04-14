package com.std.framework.core.log;


/**
 * @author Luox Log方法实现类，高层结构，负责判断和发起调用，具体log数据流处理交给各个代理。
 */

public class LogImpl implements Log {

    private static LogEnum currentLevel = null;

    public LogImpl (LogEnum initLevel) {
        currentLevel = initLevel;
    }

    @Override
    public void info (Object msgObj) {
        if (currentLevel.getLevelNum() <= LogEnum.INFO.getLevelNum()) {
            LogOutput proxy = chooseOutPutWay(LogEnum.INFO);
            writeLog(proxy, msgObj);
        }
    }

    @Override
    public void warn (Object msgObj) {
        if (currentLevel.getLevelNum() <= LogEnum.WARN.getLevelNum()) {
            LogOutput proxy = chooseOutPutWay(LogEnum.WARN);
            writeLog(proxy, msgObj);
        }
    }

    @Override
    public void error (Object msgObj) {
        if (currentLevel.getLevelNum() <= LogEnum.ERROR.getLevelNum()) {
            LogOutput proxy = chooseOutPutWay(LogEnum.ERROR);
            writeLog(proxy, msgObj);
        }
        ((Exception) msgObj).printStackTrace();
    }

    @Override
    public void debug (Object msgObj) {
        if (currentLevel.getLevelNum() <= LogEnum.DEBUG.getLevelNum()) {
            LogOutput proxy = chooseOutPutWay(LogEnum.DEBUG);
            writeLog(proxy, msgObj);
        }
    }

    @Override
    public void all (Object msgObj) {
        if (currentLevel.getLevelNum() <= LogEnum.ALL.getLevelNum()) {
            LogOutput proxy = chooseOutPutWay(LogEnum.ALL);
            writeLog(proxy, msgObj);
        }
    }

    private LogOutput chooseOutPutWay (LogEnum le) {
        String outStreamTarget = LogProps.getOutStreamTarget();
        if (outStreamTarget.equals(Log.CONSOLE_OUT_PUT)) {
            return new ConsoleOutput(le);
        } else if (outStreamTarget.equals(Log.FILE_OUT_PUT)) {
            return new FileOutput(LogProps.getFilePath(), le);
        } else {
            return new ConsoleOutput(le);
        }
    }

    private void writeLog (LogOutput proxy, Object msgObj) {
        try {
            proxy.writeLog(msgObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
