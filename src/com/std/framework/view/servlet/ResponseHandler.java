package com.std.framework.view.servlet;

import com.std.framework.core.log.Log;
import com.std.framework.core.log.LogFactory;
import com.std.framework.core.util.ReflectionUtil;
import com.std.framework.view.handle.CoreAction;
import com.std.framework.view.handle.CoreInvocation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ResponseHandler {

    private static Log log = LogFactory.getLogger();

    public static String getResponseJsp(String fwJsp, String className) {
        String forwardJsp = "";
        forwardJsp = fwJsp.concat(".jsp");
        if (!fwJsp.startsWith("/") && className.indexOf(".") != -1) {
            String tempStr = className.substring(0, className.lastIndexOf(".") + 1);
            forwardJsp = tempStr.replaceAll("\\.", "/").concat(fwJsp).concat(".jsp");
        }
        log.debug("ForwardJsp : " + forwardJsp);
        return forwardJsp;
    }

    /**
     * @param action 回写action中显示声明的属性到request中
     */
    public static void prepareFormAttribute(CoreAction action) throws Exception {
        boolean needFormed = action.isAttriToForm();
        if (needFormed) {
            Field[] declaredFields = action.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                Object attrValue = ReflectionUtil.getFieldValue(action, field);
                action.setAttr(field.getName(), attrValue);
            }
        }
    }

    public void sendResponse(CoreAction action, Method method) throws Exception {
        new CoreInvocation(method, action).invoke();
    }

}
