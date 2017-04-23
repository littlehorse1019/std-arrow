package com.std.server.routes;

import com.std.server.http.HttpHandler;
import com.std.server.servlet.HttpServletRequest;
import com.std.server.servlet.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Route
 * <p>
 * This class represent one route way to accept request, it includes URL pattern and parameter mapping
 * @author luoxiao
 * @since 1.0
 */
public class Route {

    private String     requestType;
    private String     path;
    private Method     method;
    private Object     parent;
    private URLPattern pattern;

    public Route (String requestType, String path) {
        if (requestType == null || path == null) {
            throw new IllegalArgumentException();
        }
        this.requestType = requestType;
        this.path = path;
        this.pattern = new URLPattern(path);
    }

    public String getRequestType () {
        return requestType;
    }

    public String getPath () {
        return path;
    }


    /**
     * Use the method and parent this route
     * <p>
     * This is called when httpServer accept an controller, parent must extends from {@code HttpHandler}
     */
    public Route use (Method method, Object parent) {
        this.method = method;
        this.parent = parent;
        if (method != null && !HttpHandler.class.isAssignableFrom(method.getReturnType())) {
            throw new RoutingException("Routes must return a HttpHandler. Actual: " + method.getReturnType());
        }
        return this;
    }

    /**
     * matches annotation path and request url
     * @return boolean
     */
    public boolean matches (String url) {
        if (url == null) {
            throw new IllegalArgumentException();
        }
        return pattern.matches(url);
    }

    /**
     * invoke controller function which matches the url
     * <p>
     * function must return a implementation of {@code HttpHandler}
     */
    public Object invoke (HttpServletRequest request, HttpServletResponse response)
        throws InvocationTargetException, IllegalAccessException {
        if (method == null) {
            throw new RoutingException(
                "No method configured for route. Route#use must be called to assign the method to invoke.");
        }
        method.setAccessible(true);
        return method.invoke(parent, request, response);
    }


}
