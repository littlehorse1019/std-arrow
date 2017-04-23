package com.std.framework.core.proxy;


import com.std.framework.core.util.ConvertUtil;
import com.std.framework.core.util.PathUtil;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

/**
 * @author Luox
 *         自定义动态代理编译器，通过ClassLoader加载被代理文件，载入{@link ProxyGenerator}中得到的动态类
 *         进行动态代理实现，但目前ProxyGenerator还是拦截器类的代理步骤，需要更改，目标是不改变类的情况下
 *         改变类的行为，现在将注解加载的被代理类上是个错误。
 */
public class MemoryCompiler {

    private String instanceName;

    public MemoryCompiler (int proxyCount) {
        instanceName = "Proxy$" + proxyCount;
    }

    /**
     * 内存中生成代理实例对象
     */
    public Object generatorProxyInMemory (Class<?> clazz, List<Method> proxyMethods) throws ClassNotFoundException,
        InstantiationException, IllegalAccessException {
        // 写代理类字符编码主体
        String source = writeJavaSourceFileForMemory(clazz, proxyMethods);
        // 获取FileManager
        JavaCompiler      compiler    = ToolProvider.getSystemJavaCompiler();
        MemoryFileManager fileManager = new MemoryFileManager(compiler.getStandardFileManager(null, null, null));
        // 字符流编译类成实例对象
        JavaFileObject                     file             = fileManager.makeSource(instanceName, source);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        String                             classpath        = PathUtil.getRootClassPath();
        Iterable<String> options = Arrays
            .asList("-d", classpath, "-cp", classpath); // ?????????????????????????????????
        JavaCompiler.CompilationTask task = compiler
            .getTask(null, fileManager, null, options, null, compilationUnits);
        task.call();
        // 将编译的class对象实例载入classloader
        Class<?> proxyClazz = fileManager.getClassLoader(null).loadClass(instanceName);
        return proxyClazz.newInstance();
    }

    /**
     * 字符流创建代理类编码
     */
    private String writeJavaSourceFileForMemory (Class<?> clazz, List<Method> proxyMethods) {
        StringBuilder sb = new StringBuilder();
        writeClassHead(sb, clazz);
        sb.append("{\n");
        sb.append("\tpublic com.std.core.proxy.ProxyHandler proxyHandler;\n");
        writeMethods(sb, clazz, proxyMethods);
        sb.append("}");
        return sb.toString();
    }

    private void writeClassHead (StringBuilder sb, Class<?> clazz) {
        sb.append("import " + clazz.getName() + ";\n");
        sb.append("public class " + instanceName + " extends ");
        sb.append(clazz.getSimpleName());
        sb.append("\n");
    }

    private void writeMethods (StringBuilder sb, Class<?> clazz, List<Method> proxyMethods) {
        for (Method method : proxyMethods) {
            sb.append("\tpublic ");
            Class<?> returnType = method.getReturnType();
            // ????????????????????
            if (returnType.isArray()) {
                sb.append(returnType.getCanonicalName());
            } else {
                sb.append(returnType.getName());
            }
            sb.append(" ").append(method.getName());
            sb.append("(");

            Class<?>[] parameters = method.getParameterTypes();
            int        i          = 0;
            String     args       = "";
            String     pClazz     = "";
            for (Class<?> parameter : parameters) {
                sb.append(parameter.getCanonicalName()).append(" arg").append(i);
                args += "arg" + i;
                pClazz += parameter.getCanonicalName() + ".class";
                if (i != parameters.length - 1) {
                    sb.append(",");
                    args += ",";
                    pClazz += ",";
                }
                i++;
            }

            sb.append(")\n\t{\n");
            sb.append("\t\tObject obj = null;\n");
            sb.append("\t\ttry\n\t\t{\n");
            sb.append("\t\t\tobj = proxyHandler.invoke(");
            sb.append(clazz.getCanonicalName() + ".class.getMethod(\"" + method.getName() + "\","
                          + (parameters.length == 0 ? "new Class<?>[]{}" : pClazz) + "),");
            sb.append((parameters.length == 0 ? "new Object[]{}" : args));
            sb.append(")");
            sb.append(";\n");
            sb.append("\t\t}\n\t\tcatch (Exception e)\n\t\t{\n\t\t\te.printStackTrace();\n\t\t}\n");
            sb.append("\t\treturn");

            // 原型类型转换Object Otherwise --> ClassCastException: Object cannot be cast to primitive type
            String castString = ConvertUtil.primitiveToObjCastString(returnType);
            sb.append(castString.equals("") ? castString : castString + " obj ");
            sb.append(";");
            sb.append("\n\t}\n");
        }
    }
}
