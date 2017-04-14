package com.std.framework.view.interceptor;


import com.std.framework.annotation.Global;
import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.GlobalInterceptorExtraction;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Luox Global级别拦截器容器
 */
public class GlobalInterceptorStation {

    private static Log log = LogFactory.getLogger();

    private static List<BaseInterceptor> globalInterceptorList = new LinkedList<BaseInterceptor>();

    public static Collection<? extends BaseInterceptor> getGlobalQueue () {
        return globalInterceptorList;
    }


    public static void loadInterceptor () throws Exception {
        ClassScanner cs = ClassScanner.instance();
        cs.shiftViewJars();
        List<Class<?>> classes = cs.findMacthedClass(new GlobalInterceptorExtraction());
        for (Class<?> c : classes) {
            globalInterceptorList.add((BaseInterceptor) c.newInstance());
        }
        Collections.sort(globalInterceptorList, (arg0, arg1) -> {
            Global global0 = arg0.getClass().getAnnotation(Global.class);
            Global global1 = arg1.getClass().getAnnotation(Global.class);
            return global0.order() - global1.order();
        });
        log.debug("全局拦截器优先级->" + globalInterceptorList);
    }

}
