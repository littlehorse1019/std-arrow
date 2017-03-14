package test.intcept;

import com.std.framework.view.handle.CoreInvocation;
import com.std.framework.view.interceptor.BaseInterceptor;


public class ClassInterceptor2 extends BaseInterceptor {

    @Override
    public void before(CoreInvocation invocation) throws Exception {
        System.out.println("ClassInterceptor2 Before Interceptor !! ");
    }

    @Override
    public void after(CoreInvocation invocation) throws Exception {
        System.out.println("ClassInterceptor2 After Interceptor !! ");
    }
}
