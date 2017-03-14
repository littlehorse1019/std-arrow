package com.std.framework.core.extraction;


import com.std.framework.annotation.Interceptor;
import com.std.framework.view.handle.BaseAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox Interceptor ע�����ȡ�� �ڹ������������ļ���������ָ����jar���е��ࣩ�����У���ȡ���м̳���CoreAction���Ҹ���Interceptorע����ࡣ
 */
public class ClassInterceptorExtraction implements Extraction {

    private Class<BaseAction> clazz = BaseAction.class;

    public List<Class<?>> extract(List<String> classFileList) throws Exception {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (String classFile : classFileList) {
            try {
                Class<?> classInFile = Class.forName(classFile);
                // �Ƿ�̳���CoreAction������  parent.isAssignableFrom(child)
                if (clazz.isAssignableFrom(classInFile)
                        // �ų�CoreAction��������
                        && !classInFile.getSimpleName().equals(clazz.getSimpleName())
                        // �Ƿ����@Interceptorע��
                        && classInFile.isAnnotationPresent(Interceptor.class)) {
                    classList.add(classInFile);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

}
