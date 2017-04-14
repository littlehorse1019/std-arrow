package com.std.intcept;

import com.std.framework.view.handle.CoreInvocation;
import com.std.framework.view.interceptor.BaseInterceptor;


public class MethodInterceptor2 extends BaseInterceptor {

    @Override
    public void before (CoreInvocation invocation) throws Exception {
        System.out.println("MethodInterceptor2 Before Interceptor !! ");
    }

    @Override
    public void after (CoreInvocation invocation) throws Exception {
        System.out.println("MethodInterceptor2 After Interceptor !! ");
    }
}
