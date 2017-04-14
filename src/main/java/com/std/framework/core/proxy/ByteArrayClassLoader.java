package com.std.framework.core.proxy;

import java.util.Map;

public class ByteArrayClassLoader extends ClassLoader {

    /**
     * Maps binary class names to class files stored as byte arrays.
     */
    private Map<String, byte[]> classes;

    /**
     * Creates a new instance of ByteArrayClassLoader
     *
     * @param a map from binary class names to class files stored as byte arrays
     */
    public ByteArrayClassLoader (Map<String, byte[]> classes) {
//		super(new URL[]{}); // π”√URLClassLoader
        this.classes = classes;
//		File file = new File(PathPub.getRootClassPath());
//		URL url = file.toURI().toURL();
//		this.addURL(url);
    }

    @Override
    public Class<?> loadClass (String className) throws ClassNotFoundException {
        try {
//			return super.loadClass(className);
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            byte[] classData = classes.get(className);
            return defineClass(className, classData, 0, classData.length);
        }
    }
}
