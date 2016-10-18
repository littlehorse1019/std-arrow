package com.std.framework.core.util;

import java.io.File;

/**
 * @author Luox 
 *         new File("..\path\abc.txt") 中的三个方法获取路径的方
 * 		   1.getPath()
 *         获取相对路径，例 ..\path\abc.txt 
 *         2.getAbslutlyPath() 
 *         获取绝对路径，但可能包含 "..""." 字符，例D:\otherPath\..\path\abc.txt 
 *         3.getCanonicalPath()
 *         获取绝对路径，但不包".." "." 字符，例 D:\path\abc.txt
 */
public class PathUtil {

	private static String webRootPath;
	private static String rootClassPath;

	@SuppressWarnings("rawtypes")
	public static String getPath(Class clazz) {
		String path = clazz.getResource("").getPath();
		return new File(path).getAbsolutePath();
	}

	public static String getPath(Object object) {
		String path = object.getClass().getResource("").getPath();
		return new File(path).getAbsolutePath();
	}

	public static String getRootClassPath() {
		if (rootClassPath == null) {
			try {
				String path = PathUtil.class.getClassLoader().getResource("/")
						.toURI().getPath();
				rootClassPath = new File(path).getAbsolutePath();
			} catch (Exception e) {
				String path = PathUtil.class.getClassLoader().getResource("/")
						.getPath();
				rootClassPath = new File(path).getAbsolutePath();
			}
		}
		return rootClassPath + "/";
	}

	public void setRootClassPath(String rootClassPath) {
		PathUtil.rootClassPath = rootClassPath;
	}

	public static String getPackagePath(Object object) {
		Package p = object.getClass().getPackage();
		return p != null ? p.getName().replaceAll("\\.", "/") : "";
	}

	public static File getFileFromJar(String file) {
		throw new RuntimeException("Not finish. Do not use this method.");
	}

	public static String getWebRootPath() {
		if (webRootPath == null)
			webRootPath = detectWebRootPath();
		return webRootPath;
	}

	public static void setWebRootPath(String webRootPath) {
		if (webRootPath == null)
			return;

		if (webRootPath.endsWith(File.separator))
			webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
		PathUtil.webRootPath = webRootPath;
	}

	private static String detectWebRootPath() {
		try {
			String path = PathUtil.class.getResource("/").toURI().getPath();
			return new File(path).getParentFile().getParentFile()
					.getCanonicalPath();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getProjectPath() {
		java.net.URL url = PathUtil.class.getProtectionDomain().getCodeSource()
				.getLocation();
		String filePath = null;
		try {
			filePath = java.net.URLDecoder.decode(url.getPath(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (filePath.endsWith(".jar"))
			filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
		java.io.File file = new java.io.File(filePath);
		filePath = file.getAbsolutePath();
		return filePath;
	}

	public static String getRealPath() {
		String realPath = PathUtil.class.getClassLoader().getResource("")
				.getFile();
		java.io.File file = new java.io.File(realPath);
		realPath = file.getAbsolutePath();
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return realPath;
	}

	public static String getAppPath(Class<?> cls) {
		// �?��用户传入的参数是否为�?
		if (cls == null)
			throw new java.lang.IllegalArgumentException("参数不能为空");

		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包
		String clsName = cls.getName();
		// 此处判定是否是Java基础类库，防止用户传入JDK内置的类
		if (clsName.startsWith("java.") || clsName.startsWith("javax.")) {
			throw new java.lang.IllegalArgumentException("不要传入系统类！");
		}
		// 将类的class文件全名改为路径形式
		String clsPath = clsName.replace(".", "/") + ".class";

		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(clsPath);
		// 从URL对象中获取路径信
		String realPath = url.getPath();
		// 去掉路径信息中的协议file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1) {
			realPath = realPath.substring(pos + 5);
		}
		// 去掉路径信息包含类文件信息的部分，得到类的路
		pos = realPath.indexOf(clsPath);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!")) {
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		}
		java.io.File file = new java.io.File(realPath);
		realPath = file.getAbsolutePath();

		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return realPath;
	}// getAppPath定义结束

}
