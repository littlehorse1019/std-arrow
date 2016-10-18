package com.std.framework.core.extraction;

import java.util.ArrayList;
import java.util.List;

import com.std.framework.annotation.Job;

/**
 * @author Luox Job 注解类抽取器 在工程下所有类文件名（包括指定的jar包中的类）集合中。
 */
public class JobExtraction implements Extraction {

	public List<Class<?>> extract(List<String> classFileList) throws Exception {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		for (String classFile : classFileList) {
			try {
				Class<?> classInFile = Class.forName(classFile);
				if (classInFile.isAnnotationPresent(Job.class)) {
					classList.add(classInFile);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classList;
	}
}
