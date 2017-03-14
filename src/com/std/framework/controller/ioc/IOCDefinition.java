package com.std.framework.controller.ioc;


import org.w3c.dom.Node;

import java.util.List;

import static com.std.framework.container.c.ControllerXMLParser.*;

/**
 * @author Luox �������ڱ��������ļ����������Ԫ�ض����ϵ
 */
public class IOCDefinition {

    private String beanId = "";
    private String beanClassName = "";
    private String beanType = "";
    private List<PropertyBean> beanProp = null;

    public String getDefBeanId() {
        return beanId;
    }

    public String getDefBeanClassName() {
        return beanClassName;
    }

    public String getDefBeanType() {
        return beanType;
    }

    public List<PropertyBean> getDefBeanProp() {
        return beanProp;
    }

    public void loadIOCDefine2Cache(Node beanNode, IOCCache iocCache) throws Exception {
        beanId = getBeanId(beanNode);
        beanClassName = getBeanClass(beanNode);
        beanType = getBeanType(beanNode);
        beanProp = getBeanProp(beanNode);
        iocCache.cacheIOCDefine(this);
    }

}