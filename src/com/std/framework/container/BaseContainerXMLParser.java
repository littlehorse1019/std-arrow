package com.std.framework.container;

import com.std.framework.container.c.ControllerXMLConstants;
import com.std.framework.container.m.ModelXMLConstants;
import com.std.framework.container.v.ViewXMLConstants;
import com.std.framework.core.util.PathUtil;
import com.std.framework.core.util.StringUtil;
import com.std.framework.core.xml.XMLValidator;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class BaseContainerXMLParser {

    protected static String configFilePath;

    protected BaseContainerXMLParser () {
    }

    public static String getConfigResource () {
        return configFilePath;
    }

    public static void setConfigResource (String configResource) {
        if (StringUtil.isBlank(configResource)) {
            String resourePath = PathUtil.getRootClassPath();
            BaseContainerXMLParser.configFilePath = resourePath + XMLValidator.DEFAULT_MVC_FILE_NAME;
        } else {
            BaseContainerXMLParser.configFilePath = configResource;
        }
    }

    protected static Document getConfigDOM (String configResource) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder        db  = dbf.newDocumentBuilder();
            return db.parse(configResource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Node getViewNode () {
        Node viewNode = null;
        try {
            Document configDOM = getConfigDOM(configFilePath);
            viewNode = configDOM.getElementsByTagName(ViewXMLConstants.VIEW_NODE).item(0);
            return viewNode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewNode;
    }

    protected static Node getModelNode () {
        try {
            Document configDOM = getConfigDOM(configFilePath);
            assert configDOM != null;
            return configDOM.getElementsByTagName(ModelXMLConstants.MODEL_NODE).item(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Node getControllerNode () {
        try {
            Document configDOM = getConfigDOM(configFilePath);
            assert configDOM != null;
            return configDOM.getElementsByTagName(ControllerXMLConstants.CONTROLLER_NODE).item(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    protected static List<Node> getNodeListByChildName (Node fatherNode, String nodeName) {
        NodeList   ndList = fatherNode.getChildNodes();
        List<Node> list   = new ArrayList<>();
        for (int i = 0; i < ndList.getLength(); i++) {
            if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE
                && nodeName.equals(ndList.item(i).getNodeName())) {
                list.add(ndList.item(i));
            }
        }
        return list;
    }

    protected static Node getNodeByChildName (Node fatherNode, String nodeName) {
        NodeList ndList = fatherNode.getChildNodes();
        for (int i = 0; i < ndList.getLength(); i++) {
            if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE
                && (StringUtil.isBlank(nodeName) ||
                nodeName.equals(ndList.item(i).getNodeName()))) {
                return ndList.item(i);
            }
        }
        return null;
    }

    protected static String getElementValue (Node node, String nodeName) {
        NodeList ndList = node.getChildNodes();
        for (int i = 0; i < ndList.getLength(); i++) {
            if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE
                && nodeName.equals(ndList.item(i).getNodeName())) {
                return ndList.item(i).getFirstChild().getNodeValue();
            }
        }
        return null;
    }

}
