package com.std.framework.core.log;

/**
 * @author Luox 日志输出代理抽象类，用来整合文件，控制台，GUI组件，套接口服务和邮件等log输出方式
 */

public interface LogOutput {

	public void writeLog(Object msgObj) throws Exception;

}
