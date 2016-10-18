package com.std.framework.core.proxy;

import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;

@SuppressWarnings( { "unchecked","rawtypes"} )
public final class MemoryFileManager extends ForwardingJavaFileManager {
	/**
	 * Maps binary class names to class files stored as byte arrays.
	 */
	private Map<String, byte[]> classes;

	public JavaFileObject makeSource(String name, String code) {
		return new JavaSourceFromString(name, code);
	}

	/**
	 * Construct a memory file manager which delegates to the specified file manager for unknown sources.
	 * 
	 * @param fileManager
	 *            a file manager used to look up class files on class path, etc.
	 */
	public MemoryFileManager(JavaFileManager fileManager) {
		super(fileManager);
		classes = new HashMap<String, byte[]>();
	}

	/**
	 * Get a class loader which first search the classes stored by this file manager.
	 * 
	 * @return a class loader for compiled files
	 */
	@Override
	public ClassLoader getClassLoader(Location location) {
		return new ByteArrayClassLoader(classes);
	}

	@Override
	public JavaFileObject getJavaFileForOutput(Location location, String name, Kind kind, FileObject originatingSource)
			throws UnsupportedOperationException {
		if (originatingSource instanceof JavaSourceFromString) {
			return new JavaClassInArray(name, classes);
		} else {
			throw new UnsupportedOperationException();
		}
	}
}
