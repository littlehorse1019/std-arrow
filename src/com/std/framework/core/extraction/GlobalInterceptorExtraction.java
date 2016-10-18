package com.std.framework.core.extraction;


import java.util.ArrayList;
import java.util.List;

import com.std.framework.annotation.Global;
import com.std.framework.view.interceptor.CoreInterceptor;

/**
 * @author Luox Interceptor 注解类抽取器 在工程下所有类文件名（包括指定的jar包中的类）集合中，抽取所有继承自CoreInterceptor并且附带Interceptor注解的类。
 */
public class GlobalInterceptorExtraction implements Extraction {

	private Class<CoreInterceptor> clazz = CoreInterceptor.class;

	public List<Class<?>> extract(List<String> classFileList) throws Exception {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		for (String classFile : classFileList) {
			try {
				Class<?> classInFile = Class.forName(classFile);
				// 是否继承自CoreInterceptor实现类  parent.isAssignableFrom(child)
				if (clazz.isAssignableFrom(classInFile)
				// 排除CoreInterceptor该类自身
						&& !classInFile.getSimpleName().equals(clazz.getSimpleName())
						// 是否存在@Global注解
						&& classInFile.isAnnotationPresent(Global.class)) {
					classList.add(classInFile);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classList;
	}
}
