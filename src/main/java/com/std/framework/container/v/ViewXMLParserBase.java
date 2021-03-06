package com.std.framework.container.v;

import static com.std.framework.container.v.ViewXMLConstants.VIEW_ACCEPT;
import static com.std.framework.container.v.ViewXMLConstants.VIEW_CACHECONTROL;
import static com.std.framework.container.v.ViewXMLConstants.VIEW_ENCODING;
import static com.std.framework.container.v.ViewXMLConstants.VIEW_EXPIRES;
import static com.std.framework.container.v.ViewXMLConstants.VIEW_PRAGMA;
import static com.std.framework.container.v.ViewXMLConstants.VIEW_SEND;

import com.std.framework.container.BaseContainerXMLParser;
import org.w3c.dom.Node;


public class ViewXMLParserBase extends BaseContainerXMLParser {

    public static Node getAcceptNode () throws Exception {
        Node viewNode = getViewNode();
        return getNodeByChildName(viewNode, VIEW_ACCEPT);
    }

    public static Node getSendNode () throws Exception {
        Node viewNode = getViewNode();
        return getNodeByChildName(viewNode, VIEW_SEND);
    }

    public static String getRequsetEncoding () throws Exception {
        Node acceptNode = getAcceptNode();
        return getElementValue(acceptNode, VIEW_ENCODING);
    }

    public static String getResponseEncoding () throws Exception {
        Node sendNode = getSendNode();
        return getElementValue(sendNode, VIEW_ENCODING);
    }

    public static String getResponseCacheControl () throws Exception {
        Node sendNode = getSendNode();
        return getElementValue(sendNode, VIEW_CACHECONTROL);
    }

    public static String getResponsePragma () throws Exception {
        Node sendNode = getSendNode();
        return getElementValue(sendNode, VIEW_PRAGMA);
    }

    public static String getResponseExpires () throws Exception {
        Node sendNode = getSendNode();
        return getElementValue(sendNode, VIEW_EXPIRES);
    }

}
