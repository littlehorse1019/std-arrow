package com.std.server.servlet;

import com.std.server.routes.URL;

import java.util.Map;

public class HttpServletRequestWrapper implements HttpServletRequest {

    private String method;
    private String action;
    private Map<String, String[]> headers;
    private Map<String, String[]> paramters;

    private HttpServletRequestWrapper() {
    }

    /**
     * @param action
     * @param url
     * @param version
     * @param headers
     * @param body
     * @return
     */
    public static HttpServletRequestWrapper wrap(String action, String url,
                                                 String version, Map<String, String[]> headers, StringBuilder body) {
        HttpServletRequestWrapper request = new HttpServletRequestWrapper();
        return request;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getRequestURL()
     */
    @Override
    public URL getRequestURL() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getRequestURI()
     */
    @Override
    public String getRequestURI() {
        // TODO Auto-generated method stub
        return this.action;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getQueryString()
     */
    @Override
    public String getQueryString() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getRemoteAddr()
     */
    @Override
    public String getRemoteAddr() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getRemoteHost()
     */
    @Override
    public String getRemoteHost() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getRemotePort()
     */
    @Override
    public String getRemotePort() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getLocalAddr()
     */
    @Override
    public String getLocalAddr() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getLocalName()
     */
    @Override
    public String getLocalName() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getMethod()
     */
    @Override
    public String getMethod() {
        // TODO Auto-generated method stub
        return this.method;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getHeader(java.lang.String)
     */
    @Override
    public String[] getHeader(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getHeaders(java.lang.String)
     */
    @Override
    public Map<String, String[]> getHeaders(String name) {
        // TODO Auto-generated method stub
        return this.headers;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getHeaderNames()
     */
    @Override
    public String[] getHeaderNames() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getParameter(java.lang.String)
     */
    @Override
    public String getParameter(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getParameterValues(java.lang.String)
     */
    @Override
    public String[] getParameterValues(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getParameterNames()
     */
    @Override
    public String[] getParameterNames() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.std.server.servlet.HttpServletRequest#getParameterMap()
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        // TODO Auto-generated method stub
        return this.paramters;
    }

}
