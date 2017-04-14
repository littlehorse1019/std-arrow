package com.std.framework.core.extraction;


import com.std.framework.annotation.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox Entity ע�����ȡ�� �ڹ������������ļ���������ָ����jar���е��ࣩ�����У���ȡ���и���Entityע����ࡣ
 */
public class EntityExtraction implements Extraction {

    public List<Class<?>> extract (List<String> classFileList) throws Exception {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (String classFile : classFileList) {
            try {
                Class<?> classInFile = Class.forName(classFile);
                if (classInFile.isAnnotationPresent(Entity.class)) {
                    classList.add(classInFile);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

}
