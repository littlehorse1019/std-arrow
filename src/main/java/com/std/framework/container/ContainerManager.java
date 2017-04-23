package com.std.framework.container;

/**
 * @author Luox MVC容器 整体上下文初始化入口
 */
public class ContainerManager {

    public static void loadSysParams () {
        StdSysContainer.loadSysParams();
    }

    public static Object getParam (String paramName) {
        return StdSysContainer.getParam(paramName);
    }

}
