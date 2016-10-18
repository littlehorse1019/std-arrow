package com.std.framework.container;

import java.util.ArrayList;
import java.util.List;








import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.std.framework.container.c.ControllerXMLConstants;
import com.std.framework.container.m.ModelXMLConstants;
import com.std.framework.container.v.ViewXMLConstants;
import com.std.framework.core.util.PathUtil;
import com.std.framework.core.util.StringUtil;
import com.std.framework.core.xml.XMLValidator;

public abstract class ContainerXMLParser {

	protected static String configFilePath ;

	protected ContainerXMLParser() {}
	
	public static String getConfigResource() {
		return configFilePath;
	}
	
	public static void setConfigResource(String configResource) {
		if (StringUtil.isBlank(configResource)) {
			String resourePath = PathUtil.getRootClassPath();
			ContainerXMLParser.configFilePath = resourePath + XMLValidator.DEFAULT_MVC_FILE_NAME;
		}else{
			ContainerXMLParser.configFilePath = configResource;
		}
	}

	protected static Document getConfigDOM(String configResource) {
		Document document = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			document = db.parse(configResource);
			return document;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	protected static Node getViewNode() {
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

	protected static Node getModelNode() {
		Node modelNode = null;
		try {
			Document configDOM = getConfigDOM(configFilePath);
			modelNode = configDOM.getElementsByTagName(ModelXMLConstants.MODEL_NODE).item(0);
			return modelNode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelNode;
	}

	protected static Node getControllerNode() {
		Node ctrlNode = null;
		try {
			Document configDOM = getConfigDOM(configFilePath);
			ctrlNode = configDOM.getElementsByTagName(ControllerXMLConstants.CONTROLLER_NODE).item(0);
			return ctrlNode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctrlNode;
	}
	
	
	protected static List<Node> getNodeListByChildName(Node fatherNode, String nodeName) {
		NodeList ndList = fatherNode.getChildNodes();
		List<Node> list = new ArrayList<Node>();
		for (int i = 0; i < ndList.getLength(); i++) {
			if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE
					&& nodeName.equals(ndList.item(i).getNodeName())) {
				list.add(ndList.item(i)); 
			}
		}
		return list;
	}
	
	protected static Node getNodeByChildName(Node fatherNode, String nodeName) {
		NodeList ndList = fatherNode.getChildNodes();
		for (int i = 0; i < ndList.getLength(); i++) {
			if (ndList.item(i).getNodeType() == Node.ELEMENT_NODE
				&& ( StringUtil.isBlank(nodeName) || 
						nodeName.equals(ndList.item(i).getNodeName()) )){
					return ndList.item(i);
				}
			}
		return null;
	}
	
	protected static String getElementValue(Node node, String nodeName) {
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
