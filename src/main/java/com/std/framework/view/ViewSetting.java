package com.std.framework.view;

import static com.std.framework.container.v.ViewXMLParserBase.getRequsetEncoding;
import static com.std.framework.container.v.ViewXMLParserBase.getResponseCacheControl;
import static com.std.framework.container.v.ViewXMLParserBase.getResponseEncoding;
import static com.std.framework.container.v.ViewXMLParserBase.getResponseExpires;
import static com.std.framework.container.v.ViewXMLParserBase.getResponsePragma;

public class ViewSetting {

    private static String reqEncoding      = "";
    private static String respEncoding     = "";
    private static String respPragma       = "";
    private static String respCacheControl = "";
    private static String respExpires      = "";

    public static String getReqEncoding () {
        return reqEncoding;
    }

    public static String getRespEncoding () {
        return respEncoding;
    }

    public static String getRespPragma () {
        return respPragma;
    }

    public static String getRespCacheControl () {
        return respCacheControl;
    }

    public static int getRespExpires () {
        return Integer.parseInt(respExpires);
    }

    public static void init () throws Exception {
        reqEncoding = getRequsetEncoding();
        respEncoding = getResponseEncoding();
        respPragma = getResponsePragma();
        respCacheControl = getResponseCacheControl();
        respExpires = getResponseExpires();
    }

}
