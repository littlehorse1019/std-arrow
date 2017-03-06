package com.std.framework.core.extraction;


import com.std.framework.annotation.Autowired;
import com.std.framework.annotation.Controller;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Luox Controller 注解类抽取器 在工程下所有类文件名（包括指定的jar包中的类）集合中，附带Autowired和Controller注解的类。
 */
public class IOCExtraction implements Extraction {

    public List<Class<?>> extract(List<String> classFileList) throws Exception {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        for (String classFile : classFileList) {
            try {
                Class<?> classInFile = Class.forName(classFile);
                if (classInFile.isAnnotationPresent(Controller.class)
                        || classInFile.isAnnotationPresent(Autowired.class)) {
                    classList.add(classInFile);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return classList;
    }

}
