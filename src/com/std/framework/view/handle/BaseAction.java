package com.std.framework.view.handle;

import com.std.framework.view.servlet.Render;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Luox Action处理基础类
 */
public abstract class BaseAction {

    protected Render render;
    ;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Object[] paramObj = null;

    public Object[] getParamObj() {
        return paramObj;
    }

    public void setParamObj(Object[] paramObj) {
        this.paramObj = paramObj;
    }

    public void awareServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.request = request;
        this.response = response;
        render = new Render(request, response, this);
    }

    public HttpServletRequest awareRequest() {
        return request;
    }

    public HttpServletResponse awareResponse() {
        return response;
    }

    public Object getPara(String paramName) {
        return request.getParameter(paramName);
    }

    public void setAttr(String attrName, Object attrValue) {
        request.setAttribute(attrName, attrValue);
    }

    public boolean isAttriToForm() {
        return false;
    }

    ;

}
