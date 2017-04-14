package com.std.framework.view.handle;

import com.std.framework.view.interceptor.BaseInterceptor;
import com.std.framework.view.interceptor.InterceptorStore;
import java.lang.reflect.Method;
import java.util.Queue;

/**
 * @author Luox action invoke方法处理类，负责拦截器的调用和业务方法调用
 */
public class CoreInvocation {

    private Method     method;
    private BaseAction action;
    private Object[]   paramObj;
    private Queue<BaseInterceptor> interceptors = null;

    public CoreInvocation (Method method, BaseAction action) {
        this.method = method;
        this.action = action;
        this.paramObj = action.getParamObj();
        InterceptorStore interceptStore = InterceptorStore.instance();
        interceptors = interceptStore.getInterceptorQueue(action, method);
    }

    public BaseAction getAction () {
        return action;
    }

    public void invoke () throws Exception {
        BaseInterceptor interceptor = null;
        // 依次调用拦截器堆栈中的拦截器代码执行
        if ((interceptor = interceptors.poll()) != null) {
            interceptor.intercept(this);
        } else {
            invokeActionOnly();
        }
    }

    public void invokeActionOnly () throws Exception {
        if (paramObj == null) {
            method.invoke(action, new Object[]{});
        } else {
            method.invoke(action, paramObj);
        }
    }
}
