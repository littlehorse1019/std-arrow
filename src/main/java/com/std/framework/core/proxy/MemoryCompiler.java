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
 * @author Luox ����Ϊ�ڴ������ ʵ���˸��ݱ���������̬����Դ�ļ��������ڴ��н��б��� ��ҪΪ�������ָ���������·��������->
 *         C:\myproject>javac -help �÷���javac <ѡ��> <Դ�ļ�>
 *         ���У����ܵ�ѡ������� -g �������е�����Ϣ -g:none �������κε�����Ϣ -g:{lines,vars,source} ֻ����ĳЩ������Ϣ -nowarn
 *         �������κξ��� -verbose
 *         ����йر���������ִ�еĲ�������Ϣ -deprecation ���ʹ���ѹ�ʱ�� API ��Դλ�� -classpath <·��> ָ�������û����ļ���λ�� -cp
 *         <·��> ָ�������û����ļ���λ��
 *         -sourcepath <·��> ָ����������Դ�ļ���λ�� -bootclasspath <·��> �����������ļ���λ�� -extdirs <Ŀ¼>
 *         ���ǰ�װ����չĿ¼��λ�� -endorseddirs <Ŀ¼>
 *         ����ǩ���ı�׼·����λ�� -d <Ŀ¼> ָ��������ɵ����ļ���λ�� -encoding <����> ָ��Դ�ļ�ʹ�õ��ַ����� -source <�汾>
 *         �ṩ��ָ���汾��Դ������ -target <�汾> �����ض�
 *         VM
 *         �汾�����ļ� -version �汾��Ϣ -help �����׼ѡ�����Ҫ -X ����Ǳ�׼ѡ�����Ҫ -J<��־> ֱ�ӽ� <��־> ���ݸ�����ʱϵͳ
 */
public class MemoryCompiler {

    private String instanceName;

    public MemoryCompiler (int proxyCount) {
        instanceName = "Proxy$" + proxyCount;
    }

    /**
     * ����ָ���Ľӿ����ɴ������Դ�ļ������б��룬��󷵻ش�����ʵ������
     */
    public Object generatorProxyInMemory (Class<?> clazz, List<Method> proxyMethods) throws ClassNotFoundException,
        InstantiationException, IllegalAccessException {
        // ���ɶ�̬���������
        String source = writeJavaSourceFileForMemory(clazz, proxyMethods);
        // �����ڴ��ļ�������
        JavaCompiler      compiler    = ToolProvider.getSystemJavaCompiler();
        MemoryFileManager filemanager = new MemoryFileManager(compiler.getStandardFileManager(null, null, null));
        // ������������ֽ���
        JavaFileObject                     file             = filemanager.makeSource(instanceName, source);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        String                             classpath        = PathUtil.getRootClassPath();
        Iterable<String> options = Arrays
            .asList("-d", classpath, "-cp", classpath); // ����ѡ���������������ļ����ڵ�ǰĿ¼��
        JavaCompiler.CompilationTask task = compiler
            .getTask(null, filemanager, null, options, null, compilationUnits);
        task.call();
        // ���ش����࣬����ʵ��
        Class<?> proxyClazz = filemanager.getClassLoader(null).loadClass(instanceName);
        return proxyClazz.newInstance();
    }

    /**
     * ���������Դ����д���ڴ�
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
            // �������ͷ����ִ����⴦��
            if (returnType.isArray()) {
                sb.append(returnType.getCanonicalName());
            } else {
                sb.append(returnType.getName());
            }
            sb.append(" ").append(method.getName());
            sb.append("(");

            Class<?>[] parameters = method.getParameterTypes();
            // �ñ��������������ββ������ƺ��������ֲ����б��еĶ������� String arg0,String arg1...
            int i = 0;
            // ���ַ������������βεĲ������ƣ� ����invoke������ʱ����õ���Щ ���Ƶ��б�
            String args = "";
            // ���ַ����������βε��ֽ����ļ�������Object.class�����ģ����� ת����������ʱ�Ĳ�������
            String pclazz = "";
            // д���β��б�
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

            // ������������ת��Object Otherwise --> ClassCastException: Object cannot be cast to primitive type
            String castString = ConvertUtil.primitiveToObjCastString(returnType);
            sb.append(castString.equals("") ? castString : castString + " obj ");
            sb.append(";");
            sb.append("\n\t}\n");
        }
    }
}
