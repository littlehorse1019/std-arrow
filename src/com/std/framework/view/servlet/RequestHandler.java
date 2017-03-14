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
     * 获取request中的参数自动填充到action同名属性中
     */
    public static void prepareActionFiled(BaseAction action) throws Exception {
        prepareObjectField(action, null);
    }

    /**
     * 获取request中的参数自动填充到action对应的方法参数中
     */
    public static void prepareParamFiled(BaseAction action, Object object) throws Exception {
        prepareObjectField(action, object);
    }

    private static void prepareObjectField(BaseAction action, Object object) throws Exception {
        HttpServletRequest request = action.awareRequest();
        Enumeration<?> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String fieldName = (String) e.nextElement(); // 名
            String fieldValue = request.getParameter(fieldName); // 值
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

    //TODO 进入调用以前，需要确认路由信息是否合法。返回JSON Result数据。
    private void requestAnalyze() {
        try {
            String requestURI = request.getRequestURI();
            String reqUrl = requestURI.substring(requestURI.lastIndexOf("/") + 1, requestURI.lastIndexOf("."));
            invokedClassName = reqUrl.substring(0, reqUrl.lastIndexOf("."));
            invokedMethodName = reqUrl.substring(reqUrl.lastIndexOf(".") + 1);
        } catch (Exception e) {
            System.out.println("Url解析失败，请检查Url路由映射是否存在.");
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
                        /** param参数填充 */
                        prepareObjectField(action, paramInstance);
                        paramObj[i] = paramInstance;
                    }
                    action.setParamObj(paramObj);
                } else {
                    /** action属性填充 */
                    prepareObjectField(action, null);
                }
            }
        }
        return method;
    }

}
