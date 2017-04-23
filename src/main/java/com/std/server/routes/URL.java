package com.std.server.routes;

import java.util.Map;

/**
 * URL, class represent to Http url action
 * @author Administrator
 * @since 1.0
 */
public class URL {

    private String                path;
    private String                params;
    private String                query;
    private String                frag;
    private Map<String, String[]> parameters;

    public static URL parseUrl (String urlString) {
        URL url = new URL();
        return url;
    }

    public String getPath () {
        return path;
    }

    public String getParams () {
        return params;
    }

    public String getQuery () {
        return query;
    }

    public String getFrag () {
        return frag;
    }

    public Map<String, String[]> getParameters () {
        return parameters;
    }
}
