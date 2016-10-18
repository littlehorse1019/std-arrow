package com.std.framework.core.extraction;


import java.util.ArrayList;
import java.util.List;

import com.std.framework.view.handle.CoreAction;

/**
 * @author Luox CoreAction 继承类抽取器 在工程下所有类文件名（包括指定的jar包中的类）集合中，抽取所有继承自CoreAction的类。
 */
public class ViewExtraction implements Extraction {

	private Class<CoreAction> clazz = CoreAction.class;

	public List<Class<?>> extract(List<String> classFileList) throws Exception {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		for (String classFile : classFileList) {
			try {
				Class<?> classInFile = Class.forName(classFile);
				// 是否继承自CoreAction抽象类  parent.isAssignableFrom(child)
				if (clazz.isAssignableFrom(classInFile)
				// 排除CoreAction该类自身
						&& !classInFile.getSimpleName().equals(clazz.getSimpleName())) {
					classList.add(classInFile);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return classList;
	}

}
