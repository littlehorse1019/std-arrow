package com.std.framework.container;

/**
 * @author Luox MVC���� ���������ĳ�ʼ�����
 */
public class ContainerManager {

    public static void loadSysParams () {
        StdSysContainer.loadSysParams();
    }

    public static Object getParam (String paramName) {
        return StdSysContainer.getParam(paramName);
    }

}
