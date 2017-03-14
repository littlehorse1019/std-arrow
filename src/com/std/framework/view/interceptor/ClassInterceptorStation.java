package com.std.framework.view.interceptor;


import com.std.framework.annotation.Interceptor;
import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.ClassInterceptorExtraction;

import java.util.*;

/**
 * @author Luox Class¼¶±ðÀ¹½ØÆ÷ÈÝÆ÷
 */
public class ClassInterceptorStation {

    private static Map<String, Queue<BaseInterceptor>> classIntecptMap = new HashMap<String, Queue<BaseInterceptor>>();

    public static Collection<? extends BaseInterceptor> getQueueByClass(String className) {
        Queue<BaseInterceptor> queue = classIntecptMap.get(className);
        if (queue != null) {
            return queue;
        }
        return new LinkedList<BaseInterceptor>();
    }

    public static void loadInterceptor() throws Exception {
        ClassScanner cs = ClassScanner.instance();
        cs.shiftViewJars();
        List<Class<?>> classes = cs.findMacthedClass(new ClassInterceptorExtraction());
        for (Class<?> c : classes) {
            Interceptor ic = c.getAnnotation(Interceptor.class);
            Class<? extends BaseInterceptor>[] interceptors = ic.value();
            cacheIntoMap(c.getName(), interceptors);
        }
    }

    private static void cacheIntoMap(String name, Class<? extends BaseInterceptor>[] interceptors) throws Exception {
        Queue<BaseInterceptor> queue = new LinkedList<BaseInterceptor>();
        for (Class<? extends BaseInterceptor> intceptClazz : interceptors) {
            queue.offer(intceptClazz.newInstance());
        }
        classIntecptMap.put(name, queue);
    }

}
