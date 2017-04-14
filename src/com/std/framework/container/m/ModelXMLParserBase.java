package com.std.framework.container.m;


import com.std.framework.container.BaseContainerXMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class ModelXMLParserBase extends BaseContainerXMLParser {

    public Node getDataSourceNode () {
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
