package com.std.framework.view.interceptor;


import com.std.framework.annotation.Clear;
import com.std.framework.annotation.Interceptor;
import com.std.framework.annotation.Transactional;
import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.ViewExtraction;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Luox Method¼¶±ðÀ¹½ØÆ÷ÈÝÆ÷
 */
public class MethodInterceptorStation {

    private static Map<String, Queue<BaseInterceptor>> methodIntecptMap = new HashMap<String, Queue<BaseInterceptor>>();
    private static List<String> clearIntecptList = new ArrayList<String>();
    private static List<String> transactionIntecptList = new ArrayList<String>();

    public static Collection<? extends BaseInterceptor> getQueueByMethod(String methodName) {
        Queue<BaseInterceptor> queue = methodIntecptMap.get(methodName);
        if (queue != null) {
            return queue;
        }
        return new LinkedList<BaseInterceptor>();
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
                    Class<? extends BaseInterceptor>[] interceptors = ic.value();
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

    private static void cacheIntoMap(String name, Class<? extends BaseInterceptor>[] interceptors) throws Exception {
        Queue<BaseInterceptor> queue = new LinkedList<BaseInterceptor>();
        for (Class<? extends BaseInterceptor> intceptClazz : interceptors) {
            queue.offer(intceptClazz.newInstance());
        }
        methodIntecptMap.put(name, queue);
    }

}
