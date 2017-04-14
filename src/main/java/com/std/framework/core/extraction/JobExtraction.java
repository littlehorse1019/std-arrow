package com.std.framework.core.extraction;

import com.std.framework.annotation.Job;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox Job ע�����ȡ�� �ڹ������������ļ���������ָ����jar���е��ࣩ�����С�
 */
public class JobExtraction implements Extraction {

    public List<Class<?>> extract (List<String> classFileList) throws Exception {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (String classFile : classFileList) {
            try {
                Class<?> classInFile = Class.forName(classFile);
                if (classInFile.isAnnotationPresent(Job.class)) {
                    classList.add(classInFile);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }
}
