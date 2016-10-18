package com.std.framework.core.proxy;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import com.std.framework.core.util.ConvertUtil;
import com.std.framework.core.util.PathUtil;

/**
 * @author Luox 本类为内存编译器 实现了根据被代理方法动态生成源文件，并在内存中进行编译
 *  需要为编译过程指定编译的类路径：如下->
 * 	C:\myproject>javac -help
	用法：javac <选项> <源文件>
	其中，可能的选项包括：
	-g 生成所有调试信息
	-g:none 不生成任何调试信息
	-g:{lines,vars,source} 只生成某些调试信息
	-nowarn 不生成任何警告
	-verbose 输出有关编译器正在执行的操作的消息
	-deprecation 输出使用已过时的 API 的源位置
	-classpath <路径> 指定查找用户类文件的位置
	-cp <路径> 指定查找用户类文件的位置
	-sourcepath <路径> 指定查找输入源文件的位置
	-bootclasspath <路径> 覆盖引导类文件的位置
	-extdirs <目录> 覆盖安装的扩展目录的位置
	-endorseddirs <目录> 覆盖签名的标准路径的位置
	-d <目录> 指定存放生成的类文件的位置
	-encoding <编码> 指定源文件使用的字符编码
	-source <版本> 提供与指定版本的源兼容性
	-target <版本> 生成特定 VM 版本的类文件
	-version 版本信息
	-help 输出标准选项的提要
	-X 输出非标准选项的提要
	-J<标志> 直接将 <标志> 传递给运行时系统
 */
public class MemoryCompiler {

	private String instanceName;

	public MemoryCompiler(int proxyCount) {
		instanceName = "Proxy$" + proxyCount;
	}

	/**
	 * 根据指定的接口生成代理类的源文件并进行编译，最后返回代理类实例对象
	 */
	public Object generatorProxyInMemory(Class<?> clazz, List<Method> proxyMethods) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		// 生成动态代理类代码
		String source = writeJavaSourceFileForMemory(clazz, proxyMethods);
		// 创建内存文件管理器
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		MemoryFileManager filemanager = new MemoryFileManager(compiler.getStandardFileManager(null, null, null));
		// 创建代理类的字节码
		JavaFileObject file = filemanager.makeSource(instanceName, source);
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
		String classpath = PathUtil.getRootClassPath();
		Iterable<String> options = Arrays.asList("-d", classpath, "-cp", classpath); // 编译选项，将编译产生的类文件放在当前目录下
		JavaCompiler.CompilationTask task = compiler.getTask(null, filemanager, null, options, null, compilationUnits);
		task.call();
		// 加载代理类，返回实例
		Class<?> proxyClazz = filemanager.getClassLoader(null).loadClass(instanceName);
		return proxyClazz.newInstance();
	}

	/**
	 * 将代理类的源代码写入内存
	 */
	private String writeJavaSourceFileForMemory(Class<?> clazz, List<Method> proxyMethods) {
		StringBuilder sb = new StringBuilder();
		writeClassHead(sb, clazz);
		sb.append("{\n");
		sb.append("\tpublic com.std.core.proxy.ProxyHandler proxyHandler;\n");
		writeMethods(sb, clazz, proxyMethods);
		sb.append("}");
		return sb.toString();
	}

	private void writeClassHead(StringBuilder sb, Class<?> clazz) {
		sb.append("import " + clazz.getName() + ";\n");
		sb.append("public class " + instanceName + " extends ");
		sb.append(clazz.getSimpleName());
		sb.append("\n");
	}

	private void writeMethods(StringBuilder sb, Class<?> clazz, List<Method> proxyMethods) {
		for (Method method : proxyMethods) {
			sb.append("\tpublic ");
			Class<?> returnType = method.getReturnType();
			// 数组类型返回字串特殊处理
			if (returnType.isArray()) {
				sb.append(returnType.getCanonicalName());
			} else {
				sb.append(returnType.getName());
			}
			sb.append(" ").append(method.getName());
			sb.append("(");

			Class<?>[] parameters = method.getParameterTypes();
			// 该变量用来附加在形参参数名称后，用来区分参数列表中的对象，例如 String arg0,String arg1...
			int i = 0;
			// 该字符串用来保存形参的参数名称， 调用invoke方法的时候会用到这些 名称的列表
			String args = "";
			// 该字符串保存了形参的字节码文件，就像Object.class这样的，用来 转发调用请求时的参数类型
			String pclazz = "";
			// 写入形参列表
			for (Class<?> parameter : parameters) {
				sb.append(parameter.getCanonicalName()).append(" arg").append(i);
				args += "arg" + i;
				pclazz += parameter.getCanonicalName() + ".class";
				if (i != parameters.length - 1) {
					sb.append(",");
					args += ",";
					pclazz += ",";
				}
				i++;
			}

			sb.append(")\n\t{\n");
			sb.append("\t\tObject obj = null;\n");
			sb.append("\t\ttry\n\t\t{\n");
			sb.append("\t\t\tobj = proxyHandler.invoke(");
			sb.append(clazz.getCanonicalName() + ".class.getMethod(\"" + method.getName() + "\","
					+ (parameters.length == 0 ? "new Class<?>[]{}" : pclazz) + "),");
			sb.append((parameters.length == 0 ? "new Object[]{}" : args));
			sb.append(")");
			sb.append(";\n");
			sb.append("\t\t}\n\t\tcatch (Exception e)\n\t\t{\n\t\t\te.printStackTrace();\n\t\t}\n");
			sb.append("\t\treturn");

			// 基本数据类型转换Object Otherwise --> ClassCastException: Object cannot be cast to primitive type
			String castString = ConvertUtil.primitiveToObjCastString(returnType);
			sb.append(castString.equals("") ? castString : castString + " obj ");
			sb.append(";");
			sb.append("\n\t}\n");
		}
	}
}
