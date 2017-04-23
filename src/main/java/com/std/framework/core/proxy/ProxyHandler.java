package com.std.framework.core.proxy;

import java.lang.reflect.Method;

/**
 * 代理类的代理程序接口
 */
public interface ProxyHandler {

    // 代理对象的实际调用方法
    Object invoke (Method method, Object... args);
}
