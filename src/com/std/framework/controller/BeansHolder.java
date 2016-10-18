package com.std.framework.controller;


import java.util.HashMap;
import java.util.Map;

import com.std.framework.container.c.ControllerXMLConstants;
import com.std.framework.controller.ioc.IOCCache;
import com.std.framework.controller.ioc.IOCDefinition;
import com.std.framework.core.util.StringUtil;

/**
 * Bean持有容器，ThreadLocal线程安全
 */
public class BeansHolder {

	private static Map<String, Object> resources = new HashMap<String, Object>();

	public static void addBeanResource(String key, Object value) {
		resources.put(key, value);
	}

	public static Object getBeanResource(String key) throws Exception {
		IOCCache iocCache = IOCCache.instance();
		IOCDefinition iocDefine = iocCache.getIocDefine(key);
		// 如果声明为单例类型，直接从IOC缓存中获取
		if (iocDefine != null) {
			if (StringUtil.isBlank(iocDefine.getDefBeanType()) || iocDefine.getDefBeanType().equals(ControllerXMLConstants.BEAN_TYPE_SINGLETON)) {
				return resources.get(key);
			}
			// 否则重新生成
			if (iocDefine.getDefBeanType().equals(ControllerXMLConstants.BEAN_TYPE_PROTOTYPE)) {
				return iocCache.generatorIOCBean(iocDefine);
			}
		}
		return null;
	}

	public static void clear() {
		resources.clear();
	}
}