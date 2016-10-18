package com.std.framework.view.interceptor;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import com.std.framework.annotation.Global;
import com.std.framework.context.ClassScanner;
import com.std.framework.core.extraction.GlobalInterceptorExtraction;
import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;

/**
 * @author Luox Global级别拦截器容器
 */
public class GlobalIntceptStation {

	private static Log log = LogFactory.getLogger();

	private static List<CoreInterceptor> globalIntecptList = new LinkedList<CoreInterceptor>();

	public static Collection<? extends CoreInterceptor> getGlobalQueue() {
		return globalIntecptList;
	}


	public static void loadInterceptor() throws Exception {
		ClassScanner cs = ClassScanner.instance();
		cs.shiftViewJars();
		List<Class<?>> classes = cs.findMacthedClass(new GlobalInterceptorExtraction());
		for (Class<?> c : classes) {
			globalIntecptList.add((CoreInterceptor) c.newInstance());
		}
		Collections.sort(globalIntecptList, new Comparator<Object>() {
			@Override
			public int compare(Object arg0, Object arg1) {
				Global global0 = arg0.getClass().getAnnotation(Global.class);
				Global global1 = arg1.getClass().getAnnotation(Global.class);
				return global0.order() - global1.order();
			}
		});
		log.debug("全局拦截器优先级->" + globalIntecptList);
	}

}
