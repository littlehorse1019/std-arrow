package com.std.framework.core.extraction;


import com.std.framework.annotation.Autowired;
import com.std.framework.annotation.Controller;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Luox Controller ע�����ȡ�� �ڹ������������ļ���������ָ����jar���е��ࣩ�����У�����Autowired��Controllerע����ࡣ
 */
public class IOCExtraction implements Extraction {

    public List<Class<?>> extract (List<String> classFileList) throws Exception {
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
