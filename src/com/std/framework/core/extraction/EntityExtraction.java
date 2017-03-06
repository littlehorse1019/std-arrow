package com.std.framework.core.extraction;


import com.std.framework.annotation.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox Entity 注解类抽取器 在工程下所有类文件名（包括指定的jar包中的类）集合中，抽取所有附带Entity注解的类。
 */
public class EntityExtraction implements Extraction {

    public List<Class<?>> extract(List<String> classFileList) throws Exception {
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
