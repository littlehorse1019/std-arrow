package com.std.framework.view.interceptor;


import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Queue;

import com.std.framework.model.connection.AutoTx;
import com.std.framework.view.handle.CoreAction;

/**
 * @author Luox 拦截器总容器，负责存储和获取各种拦截器
 */
public class InterceptorStore {

	private static InterceptorStore interceptorStore = null;

	private InterceptorStore() {
	}

	private final static Object syncLock = new Object();  
	public static InterceptorStore instance() {
		if (interceptorStore == null) {
			synchronized (syncLock) {
				interceptorStore = new InterceptorStore();
			}
		}
		return interceptorStore;
	}

	public Queue<CoreInterceptor> getInterceptorQueue(CoreAction action, Method method) {
		Queue<CoreInterceptor> queue = new LinkedList<CoreInterceptor>();
		String methodName = getMethodInterceptorMappingName(action.getClass(), method);
		if (!MethodIntceptStation.isCleared(methodName)) {
			queue.addAll(GlobalIntceptStation.getGlobalQueue());
			queue.addAll(ClassIntceptStation.getQueueByClass(action.getClass().getName()));
			queue.addAll(MethodIntceptStation.getQueueByMethod(methodName));
		}
		if(MethodIntceptStation.isTransactional(methodName)){
			queue.offer(new AutoTx());
		}
		return queue;
	}

	public void loadInterceptor() throws Exception {
		GlobalIntceptStation.loadInterceptor();
		ClassIntceptStation.loadInterceptor();
		MethodIntceptStation.loadInterceptor();
	}

	private String getMethodInterceptorMappingName(Class<? extends CoreAction> clazz, Method method) {
		return clazz.getName() + "-" + method.getName();
	}

}
