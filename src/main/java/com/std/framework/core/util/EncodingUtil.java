package com.std.framework.core.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

public class EncodingUtil {

    Hashtable<String, ArrayList<String>> pairs = new Hashtable<String, ArrayList<String>>();

    public EncodingUtil (HttpServletRequest request, String encoding) {
        super();
        try {
            parse(request.getQueryString(), encoding);
            parse(request.getReader().readLine(), encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String decode (String s, String encoding) throws Exception {
        return URLDecoder.decode(s, encoding);
    }

    public String getParameter (String name) {
        if (pairs == null || !pairs.containsKey(name)) {
            return null;
        }
        return (String) ((pairs.get(name)).get(0));
    }

    public Enumeration<String> getParameterNames () {
        if (pairs == null) {
            return null;
        }
        return pairs.keys();
    }

    public String[] getParameterValues (String name) {
        if (pairs == null || !pairs.containsKey(name)) {
            return null;
        }
        ArrayList<String> al     = pairs.get(name);
        String[]          values = new String[al.size()];
        for (int i = 0; i < values.length; i++) {
            values[i] = (String) al.get(i);
        }
        return values;
    }

    private void parse (String urlenc, String encoding)
        throws java.io.IOException {
        if (urlenc == null) {
            return;
        }
        StringTokenizer tok = new StringTokenizer(urlenc, "&");
        try {
            while (tok.hasMoreTokens()) {
                String aPair = tok.nextToken();
                int    pos   = aPair.indexOf("=");
                String name  = null;
                String value = null;
                if (pos != -1) {
                    name = decode(aPair.substring(0, pos), encoding);
                    value = decode(aPair.substring(pos + 1), encoding);
                } else {
                    name = aPair;
                    value = "";
                }
                if (pairs.containsKey(name)) {
                    ArrayList<String> values = pairs.get(name);
                    values.add(value);
                } else {
                    ArrayList<String> values = new ArrayList<String>();
                    values.add(value);
                    pairs.put(name, values);
                }
            }
        } catch (Exception e) {
            throw new java.io.IOException(e.getMessage());
        }
    }
}
