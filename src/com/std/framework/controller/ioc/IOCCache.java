package com.std.framework.controller.ioc;


import com.std.framework.container.c.ControllerXMLConstants;
import com.std.framework.container.c.ControllerXMLParser;
import com.std.framework.controller.BeansHolder;
import com.std.framework.controller.aop.AOPCache;
import com.std.framework.controller.aop.AOPDefinition;
import com.std.framework.core.proxy.ProxyCfgBean;
import com.std.framework.core.proxy.ProxyGenerator;
import com.std.framework.core.util.ConvertUtil;
import com.std.framework.core.util.ReflectionUtil;
import com.std.framework.core.util.StringUtil;
import org.w3c.dom.Node;

import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;


public class IOCCache {

    private final static Object syncLock = new Object();
    private static IOCCache iocCache = null;

    ;
    private static Map<String, IOCDefinition> iocDefineMap = new HashMap<String, IOCDefinition>();

    private IOCCache() {
    }

    public static IOCCache instance() {
        if (iocCache == null) {
            synchronized (syncLock) {
                iocCache = new IOCCache();
            }
        }
        return iocCache;
    }

    public IOCDefinition getIocDefine(String beanId) {
        IOCDefinition iocDefine = iocDefineMap.get(beanId);
        return iocDefine;
    }

    public void cacheIOCDefine(IOCDefinition iocDefine) {
        String beanId = iocDefine.getDefBeanId();
        iocDefineMap.put(beanId, iocDefine);
    }

    public void generateAndCacheIOCBeans() throws Exception {
        for (Entry<String, IOCDefinition> entry : iocDefineMap.entrySet()) {
            do {
                Object beanInstance = generatorIOCBean(entry.getValue());
                BeansHolder.addBeanResource(entry.getKey(), beanInstance);
            }
            // ֮ǰִ�е�IOC����������Ѿ����ɹ�����ô�Ѿ�����BeansHolder�У�����Ҫ�ٴμ���
            while (BeansHolder.getBeanResource(entry.getKey()) == null);
        }
    }

    public Object generatorIOCBean(IOCDefinition iocDefine) throws Exception {
        // �������Ϊ�������ͣ������Ѿ����ɵ�IOC�࣬��ֱ�ӻ�ȡ���������iocDefine����
        if ((StringUtil.isBlank(iocDefine.getDefBeanType()) || iocDefine.getDefBeanType().equals(ControllerXMLConstants.BEAN_TYPE_SINGLETON))
                && BeansHolder.getBeanResource(iocDefine.getDefBeanId()) != null) {
            return BeansHolder.getBeanResource(iocDefine.getDefBeanId());
        }
        Object newInstance = createMayBeAOPInstance(iocDefine.getDefBeanClassName());
        invokeIOCBean(newInstance, iocDefine);
        return newInstance;
    }

    /**
     * �ж��Ƿ���AOP������࣬����ǵĻ���������Ӧ�Ķ�̬�������򴴽���ͨ����
     */
    private Object createMayBeAOPInstance(String className) throws Exception {
        AOPDefinition aopDefine = AOPCache.instance().getAopDefine(className);
        if (aopDefine != null) {
            return createProxyInstance(className, aopDefine);
        }
        return Class.forName(className).newInstance();
    }

    /**
     * ����Aop���ö��崴��������
     */
    private Object createProxyInstance(String className, AOPDefinition aopDefine) throws Exception {
        ProxyCfgBean proxyCfg = new ProxyCfgBean();
        proxyCfg.setClassName(className);
        proxyCfg.setAdvisorBeanList(aopDefine.getAdvisorBeanList());
        ProxyGenerator pg = new ProxyGenerator();
        pg.bind(proxyCfg);
        try {
            return pg.create();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Class.forName(className).newInstance();
    }

    //IOCʵ��������ע�����
    private void invokeIOCBean(Object instance, IOCDefinition iocDefinition) throws Exception {
        List<Method> setterMethods = ReflectionUtil.getSetterMethods(instance);
        for (Method setMethod : setterMethods) {
            String settingName = setMethod.getName().substring(3).substring(0, 1).toLowerCase() + setMethod.getName().substring(4);
            Class<?> paramTypeClass = setMethod.getParameterTypes()[0];
            List<PropertyBean> beanProps = iocDefinition.getDefBeanProp();
            for (PropertyBean propBean : beanProps) {
                if (propBean.getPropName().equals(settingName)) {
                    invokeIOCProp(setMethod, paramTypeClass, propBean, instance);
                }
            }
        }
    }

    //ΪIOCʵ��ע���������
    private void invokeIOCProp(Method setMethod, Class<?> paramTypeClass, PropertyBean propBean, Object instance)
            throws Exception {
        if (ConvertUtil.isBaseDataType(paramTypeClass)) {
            handleBaseValue(propBean, setMethod, paramTypeClass, instance);
        } else if (paramTypeClass.isAssignableFrom(List.class)) {
            handleListValue(propBean, setMethod, instance);
        } else if (paramTypeClass.isAssignableFrom(Map.class)) {
            handleMapValue(propBean, setMethod, instance);
        } else {
            handleRefValue(propBean, setMethod, instance);
        }
    }

    // Ϊ��IOC��ע����ͨ���Ͷ���
    private void handleBaseValue(PropertyBean propBean, Method setMethod, Class<?> paramTypeClass, Object instance)
            throws Exception {
        Object value = ConvertUtil.castToFieldType(propBean.getValueProp(), paramTypeClass);
        setMethod.invoke(instance, value);
    }

    // Ϊ��IOC��ע��List����
    private void handleListValue(PropertyBean propBean, Method setMethod, Object instance) throws Exception {
        List<Object> list = new ArrayList<Object>();
        List<Map<String, String>> listProp = propBean.getListProp();
        Iterator<Map<String, String>> iterator = listProp.iterator();
        while (iterator.hasNext()) {
            Map<String, String> next = iterator.next();
            if (next.containsKey(ControllerXMLConstants.BEAN_VALUE)) {
                String value = next.get(ControllerXMLConstants.BEAN_VALUE);
                list.add(value);
            } else if (next.containsKey(ControllerXMLConstants.BEAN_REF)) {
                String reference = next.get(ControllerXMLConstants.BEAN_REF);
                Object generatorIOCBean = generatorIOCBean(iocDefineMap.get(reference));
                list.add(generatorIOCBean);
                BeansHolder.addBeanResource(reference, generatorIOCBean);
            }
        }
        setMethod.invoke(instance, list);
    }

    @SuppressWarnings("unchecked")
    // Ϊ��IOC��ע��Map����
    private void handleMapValue(PropertyBean propBean, Method setMethod, Object instance) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Map<String, String>> mapProp = propBean.getMapProp();
        for (Entry<?, ?> entry : mapProp.entrySet()) {
            Map<String, String> mapValue = (Map<String, String>) entry.getValue();
            if (mapValue.containsKey(ControllerXMLConstants.BEAN_VALUE)) {
                String value = mapValue.get(ControllerXMLConstants.BEAN_VALUE);
                map.put((String) entry.getKey(), value);
            } else if (mapValue.containsKey(ControllerXMLConstants.BEAN_REF)) {
                String reference = mapValue.get(ControllerXMLConstants.BEAN_REF);
                Object generatorIOCBean = generatorIOCBean(iocDefineMap.get(reference));
                map.put((String) entry.getKey(), generatorIOCBean);
                BeansHolder.addBeanResource(reference, generatorIOCBean);
            }
        }
        setMethod.invoke(instance, map);
    }

    // Ϊ��IOC��ע�������������Ͷ���
    private void handleRefValue(PropertyBean propBean, Method setMethod, Object instance) throws Exception {
        String reference = propBean.getRefProp();
        Object generatorIOCBean = generatorIOCBean(iocDefineMap.get(reference));
        BeansHolder.addBeanResource(reference, generatorIOCBean);
        setMethod.invoke(instance, generatorIOCBean);
    }

    //����IOC������ã���������������ʵ��bean���������С�
    public void loadIOC() throws Exception {
        Node iocNode = ControllerXMLParser.getIOCNode();
        List<Node> beanNodeList = ControllerXMLParser.getBeanNodeList(iocNode);

        for (Node beanNode : beanNodeList) {
            IOCDefinition iocDefine = new IOCDefinition();
            iocDefine.loadIOCDefine2Cache(beanNode, iocCache);
        }
        iocCache.generateAndCacheIOCBeans();
    }

}
