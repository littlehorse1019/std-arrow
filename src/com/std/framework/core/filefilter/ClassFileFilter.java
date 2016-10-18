package com.std.framework.core.filefilter;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Luox Class类型文件过滤器
 */
public class ClassFileFilter implements FileFilter {

	private static final String CLAZZ_SUFFIX = ".class";

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return true;
		} else {
			String name = file.getName();
			if (name.endsWith(CLAZZ_SUFFIX)) {
				return true;
			} else {
				return false;
			}
		}
	}
}
