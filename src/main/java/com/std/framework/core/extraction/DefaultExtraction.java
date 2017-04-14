package com.std.framework.core.extraction;


import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox Ĭ�ϳ�ȡ�����޹��򣬽��������ļ�������ת��Ϊ�༯��
 */
public class DefaultExtraction implements Extraction {

    public List<Class<?>> extract (List<String> classFileList) throws Exception {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (String classFile : classFileList) {
            try {
                Class<?> classInFile = Class.forName(classFile);
                classList.add(classInFile);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

}
