package com.std.intcept;

import com.std.framework.annotation.Global;
import com.std.framework.view.handle.CoreInvocation;
import com.std.framework.view.interceptor.BaseInterceptor;


@Global (order = 2)
public class GlobalInterceptor2 extends BaseInterceptor {

    @Override
    public void before (CoreInvocation invocation) throws Exception {
        System.out.println("GlobalInterceptor2 Before Interceptor !! ");
    }

    @Override
    public void after (CoreInvocation invocation) throws Exception {
        System.out.println("GlobalInterceptor2 After Interceptor !! ");
    }

}
