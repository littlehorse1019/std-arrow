package com.std.framework.core.log;

/**
 * @author Luox 控制台日志输出类
 */

public class ConsoleOutput implements LogOutput {

    private LogEnum le = null;

    public ConsoleOutput(LogEnum le) {
        this.le = le;
    }

    @Override
    public void writeLog(Object msgObj) throws Exception {
        Thread currentThread = Thread.currentThread();
        StringBuilder sb = new StringBuilder("LOG");
        sb.append("[").append(le.toString()).append("]->");
        // 通过方法调用栈数组获得实际调用类名称
        sb.append(currentThread.getStackTrace()[4].getClassName()).append("|");
        sb.append(currentThread.getId()).append("@").append(currentThread.getName()).append("| :: ");
        sb.append(msgObj.toString());
        System.out.println(sb);
    }

}
