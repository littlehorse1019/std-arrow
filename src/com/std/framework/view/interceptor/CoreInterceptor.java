package com.std.framework.view.interceptor;

import com.std.framework.view.handle.CoreInvocation;

/**
 * @author Luox 拦截器处理基类
 */
public abstract class CoreInterceptor {

    public void intercept(CoreInvocation invocation) throws Exception {
        before(invocation);
        // 调用下一个拦截器，如果拦截器不存在，则执行Action
        invocation.invoke();
        after(invocation);
    }

    public abstract void before(CoreInvocation invocation) throws Exception;

    public abstract void after(CoreInvocation invocation) throws Exception;

}