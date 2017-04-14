package com.std.framework.view.interceptor;

import com.std.framework.view.handle.CoreInvocation;

/**
 * @author Luox �������������
 */
public abstract class BaseInterceptor {

    public void intercept (CoreInvocation invocation) throws Exception {
        before(invocation);
        // ������һ������������������������ڣ���ִ��Action
        invocation.invoke();
        after(invocation);
    }

    public abstract void before (CoreInvocation invocation) throws Exception;

    public abstract void after (CoreInvocation invocation) throws Exception;

}
