package com.std.framework.container.c;

import com.std.framework.container.ContainerXMLParser;
import com.std.framework.controller.ioc.PropertyBean;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.std.framework.container.c.ControllerXMLConstants.*;

public class ControllerXMLParser extends ContainerXMLParser {

    public static Node getIOCNode() throws Exception {
        Node controllerNode = getControllerNode();
        return getNodeByChildName(controllerNode, IOC_NODE);
    }

    public static Node getAOPNode() throws Exception {
        Node controllerNode = getControllerNode();
        return getNodeByChildName(controllerNode, AOP_NODE);
    }

    public static List<Node> getBeanNodeList(Node iocNode) throws Exception {
        return getNodeListByChildName(iocNode, BEAN_NODE);
    }

    public static List<Node> getAopNodeList(Node aopNode) throws Exception {
        return getNodeListByChildName(aopNode, ADVISOR);
    }

    public static NodeList getReferenceNodeList() throws Exception {
        Document configDOM = getConfigDOM(configFilePath);
        NodeList refNodeList = configDOM.getElementsByTagName(BEAN_REF);
        return refNodeList;
    }

    public static List<String> getBeanIdList(List<Node> beanNodeList) throws Exception {
        List<String> idList = new ArrayList<String>();
        for (Node bean : beanNodeList) {
            Element eleBean = (Element) bean;
            String id = eleBean.getAttribute(BEAN_ID);
            idList.add(id);
        }
        return idList;
    }

    public static List<String> getBeanClassList(List<Node> beanNodeList) throws Exception {
        List<String> idList = new ArrayList<String>();
        for (Node bean : beanNodeList) {
            Element eleBean = (Element) bean;
            String id = eleBean.getAttribute(BEAN_CLASS);
            idList.add(id);
        }
        return idList;
    }

    public static List<String> getrefIdList(NodeList referenceNodeList) throws Exception {
        List<String> idList = new ArrayList<String>();
        for (int i = 0; i < referenceNodeList.getLength(); i++) {
            Node refNode = referenceNodeList.item(i);
            Element eleRef = (Element) refNode;
            String id = eleRef.getAttribute(BEAN_REF_REFERENCE);
            idList.add(id);
        }
        return idList;
    }

    public static String getBeanId(Node beanNode) throws Exception {
        Element eleBean = (Element) beanNode;
        return eleBean.getAttribute(BEAN_ID);
    }

    public static String getBeanClass(Node beanNode) throws Exception {
        Element eleBean = (Element) beanNode;
        return eleBean.getAttribute(BEAN_CLASS);
    }

    public static String getBeanType(Node beanNode) throws Exception {
        Element eleBean = (Element) beanNode;
        return eleBean.getAttribute(BEAN_TYPE);
    }

    public static List<PropertyBean> getBeanProp(Node beanNode) throws Exception {
        List<Node> propNodeList = getBeanPropNodeList(beanNode);
        return transPropNodeList2PropList(propNodeList);
    }

    public static List<Node> getBeanPropNodeList(Node beanNode) throws Exception {
        return getNodeListByChildName(beanNode, BEAN_PROP);
    }

    private static List<PropertyBean> transPropNodeList2PropList(List<Node> propNodeList) {
        List<PropertyBean> propList = new ArrayList<PropertyBean>();
        for (Node propNode : propNodeList) {
            PropertyBean propBean = new PropertyBean();
            propBean.setPropName(getPropName(propNode));
            Node propChildNode = getPropChildNode(propBean, propNode);
            transPropNode2PropBean(propBean, propChildNode);
            propList.add(propBean);
        }
        return propList;
    }

    private static String getPropName(Node propNode) {
        Element eleBean = (Element) propNode;
        return eleBean.getAttribute(BEAN_PROP_NAME);
    }

    private static Node getPropChildNode(PropertyBean propBean, Node propNode) {
        return getNodeByChildName(propNode, null);
    }

    private static void transPropNode2PropBean(PropertyBean propBean, Node propNode) {
        String propType = propNode.getNodeName();
        if (propType.equals(BEAN_VALUE)) {
            setValue2PropBean(propBean, propNode);
        }
        if (propType.equals(BEAN_REF)) {
            setRefer2PropBean(propBean, propNode);
        }
        if (propType.equals(BEAN_PROP_LIST)) {
            setList2PropBean(propBean, propNode);
        }
        if (propType.equals(BEAN_PROP_MAP)) {
            setMap2PropBean(propBean, propNode);
        }
    }

    private static void setValue2PropBean(PropertyBean propBean, Node propNode) {
        String value = propNode.getFirstChild().getNodeValue();
        propBean.setValueProp(value);
    }

    private static void setRefer2PropBean(PropertyBean propBean, Node propNode) {
        Element propEle = (Element) propNode;
        String reference = propEle.getAttribute(BEAN_REF_REFERENCE);
        propBean.setRefProp(reference);
    }

    private static void setList2PropBean(PropertyBean propBean, Node propNode) {
        NodeList ndList = propNode.getChildNodes();
        for (int i = 0; i < ndList.getLength(); i++) {
            if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Node interNode = ndList.item(i);
                if (interNode.getNodeName().equals(BEAN_VALUE)) {
                    propBean.getListProp().add(getValueMap(interNode));
                }
                if (interNode.getNodeName().equals(BEAN_REF)) {
                    propBean.getListProp().add(getRefMap(interNode));
                }
            }
        }
    }

    private static void setMap2PropBean(PropertyBean propBean, Node propNode) {
        List<Node> entryNodeList = getEntryNodeList(propNode);
        for (Node entryNode : entryNodeList) {
            setEntryMap2PropBean(propBean, entryNode);
        }
    }

    private static void setEntryMap2PropBean(PropertyBean propBean, Node entryNode) {
        String keyName = ((Element) entryNode).getAttribute(BEAN_PROP_MAP_ENTRY_KEY);
        Map<String, Map<String, String>> mapProp = propBean.getMapProp();
        NodeList ndList = entryNode.getChildNodes();
        for (int i = 0; i < ndList.getLength(); i++) {
            if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Node interNode = ndList.item(i);
                if (interNode.getNodeName().equals(BEAN_VALUE)) {
                    mapProp.put(keyName, getValueMap(interNode));
                }
                if (interNode.getNodeName().equals(BEAN_REF)) {
                    mapProp.put(keyName, getRefMap(interNode));
                }
            }
        }
    }

    private static List<Node> getEntryNodeList(Node propNode) {
        List<Node> entryNodeList = new ArrayList<Node>();
        NodeList ndList = propNode.getChildNodes();
        for (int i = 0; i < ndList.getLength(); i++) {
            if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE
                    && ndList.item(i).getNodeName().equals(BEAN_PROP_MAP_ENTRY)) {
                entryNodeList.add(ndList.item(i));
            }
        }
        return entryNodeList;
    }

    private static Map<String, String> getValueMap(Node interNode) {
        Map<String, String> interMap = new HashMap<String, String>();
        String nodeName = interNode.getNodeName();
        String value = interNode.getFirstChild().getNodeValue();
        interMap.put(nodeName, value);
        return interMap;
    }

    private static Map<String, String> getRefMap(Node interNode) {
        Map<String, String> interMap = new HashMap<String, String>();
        String nodeName = interNode.getNodeName();
        Element interEle = (Element) interNode;
        String defaultValue = interEle.getAttribute(BEAN_REF_REFERENCE);
        interMap.put(nodeName, defaultValue);
        return interMap;
    }

}
