package com.std.framework.core.extraction;


import com.std.framework.annotation.Proxy;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Luox AOP ע�����ȡ�� �ڹ������������ļ���������ָ����jar���е��ࣩ�����У���ȡ���и���Proxyע����ࡣ
 */
public class AOPExtraction implements Extraction {

    public List<Class<?>> extract (List<String> classFileList) throws Exception {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (String classFile : classFileList) {
            try {
                Class<?> classInFile = Class.forName(classFile);
                if (classInFile.isAnnotationPresent(Proxy.class)) {
                    classList.add(classInFile);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

}
