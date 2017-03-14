package com.std.framework.core.extraction;


import com.std.framework.view.handle.BaseAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox BaseAction �̳����ȡ�� �ڹ������������ļ���������ָ����jar���е��ࣩ�����У���ȡ���м̳���CoreAction���ࡣ
 */
public class ViewExtraction implements Extraction {

    private Class<BaseAction> clazz = BaseAction.class;

    public List<Class<?>> extract(List<String> classFileList) throws Exception {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (String classFile : classFileList) {
            try {
                Class<?> classInFile = Class.forName(classFile);
                // �Ƿ�̳���CoreAction������  parent.isAssignableFrom(child)
                if (clazz.isAssignableFrom(classInFile)
                        // �ų�CoreAction��������
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
