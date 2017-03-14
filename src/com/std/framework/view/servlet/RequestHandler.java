package com.std.framework.view.servlet;

import com.std.framework.controller.BeanFactory;
import com.std.framework.core.util.ReflectionUtil;
import com.std.framework.view.ViewSetting;
import com.std.framework.view.handle.BaseAction;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.Enumeration;

@SuppressWarnings("unused")
public class RequestHandler {

    private HttpServletRequest request;
    private String invokedClassName;
    private String invokedMethodName;

    public RequestHandler(HttpServletRequest request) {
        this.request = request;
        requestSetting();
        requestAnalyze();
    }

    /**
     * ��ȡrequest�еĲ����Զ���䵽actionͬ��������
     */
    public static void prepareActionFiled(BaseAction action) throws Exception {
        prepareObjectField(action, null);
    }

    /**
     * ��ȡrequest�еĲ����Զ���䵽action��Ӧ�ķ���������
     */
    public static void prepareParamFiled(BaseAction action, Object object) throws Exception {
        prepareObjectField(action, object);
    }

    private static void prepareObjectField(BaseAction action, Object object) throws Exception {
        HttpServletRequest request = action.awareRequest();
        Enumeration<?> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String fieldName = (String) e.nextElement(); // ��
            String fieldValue = request.getParameter(fieldName); // ֵ
            if (object != null) {
                ReflectionUtil.setFieldForObject(object, fieldName, fieldValue);
            } else {
                ReflectionUtil.setFieldForObject(action, fieldName, fieldValue);
            }
        }
    }

    private void requestSetting() {
        try {
            request.setCharacterEncoding(ViewSetting.getReqEncoding());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //TODO ���������ǰ����Ҫȷ��·����Ϣ�Ƿ�Ϸ�������JSON Result���ݡ�
    private void requestAnalyze() {
        try {
            String requestURI = request.getRequestURI();
            String reqUrl = requestURI.substring(requestURI.lastIndexOf("/") + 1, requestURI.lastIndexOf("."));
            invokedClassName = reqUrl.substring(0, reqUrl.lastIndexOf("."));
            invokedMethodName = reqUrl.substring(reqUrl.lastIndexOf(".") + 1);
        } catch (Exception e) {
            System.out.println("Url����ʧ�ܣ�����Url·��ӳ���Ƿ����.");
        }
    }

    public BaseAction getServiceAction() throws Exception {
        Object action = BeanFactory.getMVCBean(invokedClassName);
        if (action == null) {
            action = Class.forName(invokedClassName).newInstance();
        }
        return (BaseAction) action;
    }

    public Method getServiceMethod(BaseAction action) throws Exception {
        Method method = null;
        Method[] serviceMethods = action.getClass().getDeclaredMethods();
        for (Method servMethod : serviceMethods) {
            if (servMethod.getName().equals(invokedMethodName)) {
                method = servMethod;
                Class<?>[] parameterTypes = servMethod.getParameterTypes();
                if (parameterTypes.length > 0) {
                    Object[] paramObj = new Object[parameterTypes.length];
                    for (int i = 0; i < parameterTypes.length; i++) {
                        Object paramInstance = parameterTypes[i].newInstance();
                        /** param������� */
                        prepareObjectField(action, paramInstance);
                        paramObj[i] = paramInstance;
                    }
                    action.setParamObj(paramObj);
                } else {
                    /** action������� */
                    prepareObjectField(action, null);
                }
            }
        }
        return method;
    }

}
