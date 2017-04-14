package com.std.framework.controller;


import com.std.framework.container.c.ControllerXMLConstants;
import com.std.framework.controller.ioc.IOCCache;
import com.std.framework.controller.ioc.IOCDefinition;
import com.std.framework.core.util.StringUtil;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean����������ThreadLocal�̰߳�ȫ
 */
public class BeansHolder {

    private static Map<String, Object> resources = new HashMap<String, Object>();

    public static void addBeanResource (String key, Object value) {
        resources.put(key, value);
    }

    public static Object getBeanResource (String key) throws Exception {
        IOCCache      iocCache  = IOCCache.instance();
        IOCDefinition iocDefine = iocCache.getIocDefine(key);
        // �������Ϊ�������ͣ�ֱ�Ӵ�IOC�����л�ȡ
        if (iocDefine != null) {
            if (StringUtil.isBlank(iocDefine.getDefBeanType()) || iocDefine.getDefBeanType().equals(
                ControllerXMLConstants.BEAN_TYPE_SINGLETON)) {
                return resources.get(key);
            }
            // ������������
            if (iocDefine.getDefBeanType().equals(ControllerXMLConstants.BEAN_TYPE_PROTOTYPE)) {
                return iocCache.generatorIOCBean(iocDefine);
            }
        }
        return null;
    }

    public static void clear () {
        resources.clear();
    }
}
