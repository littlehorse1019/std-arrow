package com.std.framework.core.proxy;

import java.lang.reflect.Method;

/**
 * ������Ĵ������ӿ�
 */
public interface ProxyHandler {

    // ��������ʵ�ʵ��÷���
    public Object invoke (Method method, Object... args);
}
