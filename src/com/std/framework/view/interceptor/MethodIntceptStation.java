package com.std.framework.view.interceptor;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.std.framework.annotation.Clear;
import com.std.framework.annotation.Interceptor;
import com.std.framework.annotation.Transactional;
import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.ViewExtraction;

/**
 * @author Luox Method¼¶±ðÀ¹½ØÆ÷ÈÝÆ÷
 */
public class MethodIntceptStation {

	private static Map<String, Queue<CoreInterceptor>> methodIntecptMap = new HashMap<String, Queue<CoreInterceptor>>();
	private static List<String> clearIntecptList = new ArrayList<String>();
	private static List<String> transactionIntecptList = new ArrayList<String>();

	public static Collection<? extends CoreInterceptor> getQueueByMethod(String methodName) {
		Queue<CoreInterceptor> queue = methodIntecptMap.get(methodName);
		if (queue != null) {
			return queue;
		}
		return new LinkedList<CoreInterceptor>();
	}
	
	public static boolean isCleared(String methodName) {
		return clearIntecptList.contains(methodName);
	}
	
	public static boolean isTransactional(String methodName) {
		return transactionIntecptList.contains(methodName);
	}

	public static void loadInterceptor() throws Exception {
		ClassScanner cs = ClassScanner.instance();
		cs.shiftViewJars();
		List<Class<?>> classes = cs.findMacthedClass(new ViewExtraction());
		for (Class<?> c : classes) {
			for (Method m : c.getDeclaredMethods()) {
				Interceptor ic = (Interceptor) m.getAnnotation(Interceptor.class);
				if (ic != null) {
					Class<? extends CoreInterceptor>[] interceptors = ic.value();
					cacheIntoMap(c.getName() + "-" + m.getName(), interceptors);
				}
				Clear cc = (Clear) m.getAnnotation(Clear.class);
				if (cc != null) {
					clearIntecptList.add(c.getName() + "-" + m.getName());
				}
				Transactional trans = (Transactional) m.getAnnotation(Transactional.class);
				if (trans != null) {
					transactionIntecptList.add(c.getName() + "-" + m.getName());
				}
			}
		}
	}

	private static void cacheIntoMap(String name, Class<? extends CoreInterceptor>[] interceptors) throws Exception {
		Queue<CoreInterceptor> queue = new LinkedList<CoreInterceptor>();
		for (Class<? extends CoreInterceptor> intceptClazz : interceptors) {
			queue.offer(intceptClazz.newInstance());
		}
		methodIntecptMap.put(name, queue);
	}

}
