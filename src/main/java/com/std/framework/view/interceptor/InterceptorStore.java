package com.std.framework.view.interceptor;


import com.std.framework.model.connection.AutoTx;
import com.std.framework.view.handle.BaseAction;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Luox 拦截器总容器，负责存储和获取各种拦截器
 */
public class InterceptorStore {

    private final static Object           syncLock         = new Object();
    private static       InterceptorStore interceptorStore = null;

    private InterceptorStore () {
    }

    public static InterceptorStore instance () {
        if (interceptorStore == null) {
            synchronized (syncLock) {
                interceptorStore = new InterceptorStore();
            }
        }
        return interceptorStore;
    }

    public Queue<BaseInterceptor> getInterceptorQueue (BaseAction action, Method method) {
        Queue<BaseInterceptor> queue      = new LinkedList<>();
        String                 methodName = getMethodInterceptorMappingName(action.getClass(), method);
        if (!MethodInterceptorStation.isCleared(methodName)) {
            queue.addAll(GlobalInterceptorStation.getGlobalQueue());
            queue.addAll(ClassInterceptorStation.getQueueByClass(action.getClass().getName()));
            queue.addAll(MethodInterceptorStation.getQueueByMethod(methodName));
        }
        if (MethodInterceptorStation.isTransactional(methodName)) {
            queue.offer(new AutoTx());
        }
        return queue;
    }

    public void loadInterceptor () throws Exception {
        GlobalInterceptorStation.loadInterceptor();
        ClassInterceptorStation.loadInterceptor();
        MethodInterceptorStation.loadInterceptor();
    }

    private String getMethodInterceptorMappingName (Class<? extends BaseAction> clazz, Method method) {
        return clazz.getName() + "-" + method.getName();
    }

}
