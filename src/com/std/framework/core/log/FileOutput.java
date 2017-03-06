package com.std.framework.core.log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Luox 文件日志输出类
 */

public class FileOutput implements LogOutput {

    private String fileName = "";
    private LogEnum le = null;

    public FileOutput(String fileName, LogEnum le) {
        this.fileName = fileName;
        this.le = le;
    }

    @Override
    public void writeLog(Object msgObj) throws Exception {
        File f = new File(fileName);
        FileOutputStream fio = new FileOutputStream(f);

        Thread currentThread = Thread.currentThread();
        StringBuilder sb = new StringBuilder("LOG");
        sb.append(le.toString()).append("->");
        // 通过方法调用栈数组获得实际调用类名称
        sb.append(currentThread.getStackTrace()[4].getClassName()).append("|");
        sb.append(currentThread.getId()).append("@").append(currentThread.getName()).append("| :: ");
        sb.append(msgObj.toString());

        fio.write(sb.toString().getBytes());
        fio.flush();
        fio.close();

    }

}
