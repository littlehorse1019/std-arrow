package com.std.framework.view.servlet;

import com.std.framework.core.util.JsonUtil;
import com.std.framework.view.ViewSetting;
import com.std.framework.view.handle.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Luox response ������ʽ������
 */
public class Render {

    private static int convertDepth = 15;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private BaseAction action;

    public Render(HttpServletRequest request, HttpServletResponse response, BaseAction action) {
        this.request = request;
        this.response = response;
        this.action = action;
        settingResponse();
    }

    public void setReqAttr(String name, Object obj) {
        request.setAttribute(name, obj);
    }

    /**
     * �ض���ָ����ҳ��
     */
    public void redirectJsp(String fwJsp) {
        String forwardPath = ResponseHandler.getResponseJsp(fwJsp, action.getClass().getName());
        try {
            /** �ش�action����ֵ�Զ���䵽�� */
            ResponseHandler.prepareFormAttribute(action);
            response.sendRedirect(forwardPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ת����ָ����ҳ��
     */
    public void forwardJsp(String fwJsp) {
        String forwardPath = ResponseHandler.getResponseJsp(fwJsp, action.getClass().getName());
        try {
            /** �ش�action����ֵ�Զ���䵽�� */
            ResponseHandler.prepareFormAttribute(action);
            request.getRequestDispatcher(forwardPath).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ����Json�ַ���
     */
    public void toJson(Object obj) {
        setEncoding(ViewSetting.getRespEncoding());
        writeString(JsonUtil.toJson(obj, convertDepth));
    }

    /**
     * ����Json�ַ�����ָ������
     */
    public void toJson(Object obj, String encoding) {
        setEncoding(encoding);
        writeString(JsonUtil.toJson(obj, convertDepth));
    }

    /**
     * ������ͨtext�ַ���
     */
    public void toText(String text) {
        setEncoding(ViewSetting.getRespEncoding());
        writeString(text);
    }

    /**
     * ������ͨtext�ַ��� ��ָ������
     */
    public void toText(String text, String encoding) {
        setEncoding(encoding);
        writeString(text);
    }

    private void writeString(String returnString) {
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.write(returnString);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }

    private void setEncoding(String encoding) {
        response.setCharacterEncoding(encoding);
    }

    private void settingResponse() {
        response.setHeader("Pragma", ViewSetting.getRespPragma());
        response.setHeader("Cache-Control", ViewSetting.getRespCacheControl());
        response.setDateHeader("Expires", ViewSetting.getRespExpires());
    }

}
