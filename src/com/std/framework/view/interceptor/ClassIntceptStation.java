package com.std.framework.view.interceptor;


import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.std.framework.annotation.Interceptor;
import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.ClassInterceptorExtraction;

/**
 * @author Luox Class¼¶±ðÀ¹½ØÆ÷ÈÝÆ÷
 */
public class ClassIntceptStation {

	private static Map<String, Queue<CoreInterceptor>> classIntecptMap = new HashMap<String, Queue<CoreInterceptor>>();

	public static Collection<? extends CoreInterceptor> getQueueByClass(String className) {
		Queue<CoreInterceptor> queue = classIntecptMap.get(className);
		if (queue != null) {
			return queue;
		}
		return new LinkedList<CoreInterceptor>();
	}

	public static void loadInterceptor() throws Exception {
		ClassScanner cs = ClassScanner.instance();
		cs.shiftViewJars();
		List<Class<?>> classes = cs.findMacthedClass(new ClassInterceptorExtraction());
		for (Class<?> c : classes) {
			Interceptor ic = c.getAnnotation(Interceptor.class);
			Class<? extends CoreInterceptor>[] interceptors = ic.value();
			cacheIntoMap(c.getName(), interceptors);
		}
	}

	private static void cacheIntoMap(String name, Class<? extends CoreInterceptor>[] interceptors) throws Exception {
		Queue<CoreInterceptor> queue = new LinkedList<CoreInterceptor>();
		for (Class<? extends CoreInterceptor> intceptClazz : interceptors) {
			queue.offer(intceptClazz.newInstance());
		}
		classIntecptMap.put(name, queue);
	}

}
