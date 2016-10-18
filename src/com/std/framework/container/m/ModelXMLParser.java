package com.std.framework.container.m;


import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.std.framework.container.ContainerXMLParser;


public class ModelXMLParser extends ContainerXMLParser {

	public Node getDataSourceNode() {
		Node ctrlNode = null;
		try {
			Document configDOM = getConfigDOM(configFilePath);
			ctrlNode = configDOM.getElementsByTagName(ModelXMLConstants.DATASOURCE_NODE).item(0);
			return ctrlNode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ctrlNode;
	}
}
