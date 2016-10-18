package test.intcept;

import com.std.framework.view.handle.CoreInvocation;
import com.std.framework.view.interceptor.CoreInterceptor;


public class MethodInterceptor1 extends CoreInterceptor{

	@Override
	public void before(CoreInvocation invocation) throws Exception {
		System.out.println("MethodInterceptor1 Before Interceptor !! ");
	}

	@Override
	public void after(CoreInvocation invocation) throws Exception {
		System.out.println("MethodInterceptor1 After Interceptor !! ");
	}
}
