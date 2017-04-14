package test.intcept;

import com.std.framework.annotation.Global;
import com.std.framework.view.handle.CoreInvocation;
import com.std.framework.view.interceptor.BaseInterceptor;


@Global (order = 1)
public class GlobalInterceptor1 extends BaseInterceptor {

    @Override
    public void before (CoreInvocation invocation) throws Exception {
        System.out.println("GlobalInterceptor1 Before Interceptor !! ");
    }

    @Override
    public void after (CoreInvocation invocation) throws Exception {
        System.out.println("GlobalInterceptor1 After Interceptor !! ");
    }

}
