package com.std.framework.view;

import static com.std.framework.container.v.ViewXMLParser.*;

public class ViewSetting {

	private static String reqEncoding = "";
	private static String respEncoding = "";
	private static String respPragma = "";
	private static String respCacheControl = "";
	private static String respExpires = "";

	public static String getReqEncoding() {
		return reqEncoding;
	}

	public static String getRespEncoding() {
		return respEncoding;
	}

	public static String getRespPragma() {
		return respPragma;
	}

	public static String getRespCacheControl() {
		return respCacheControl;
	}

	public static int getRespExpires() {
		return Integer.parseInt(respExpires);
	}

	public static void init() throws Exception {
		reqEncoding = getRequsetEncoding();
		respEncoding = getResponseEncoding();
		respPragma = getResponsePragma();
		respCacheControl = getResponseCacheControl();
		respExpires = getResponseExpires();
	}

}
