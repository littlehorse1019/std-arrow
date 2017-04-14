package com.std.framework.controller.aop;

/**
 * @author Luox AOP增强方法属性承载类
 */
public class AdvisorBean {

    private String methodName = "";
    private Class<?>[] methodArguments;
    private Class<?>   beforeClass;
    private String beforeMethod = "";
    private Class<?>[] beforeArguments;
    private Class<?>   afterClass;
    private String afterMethod = "";
    private Class<?>[] afterArguments;

    public String getMethodName () {
        return methodName;
    }

    public void setMethodName (String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getMethodArguments () {
        return methodArguments;
    }

    public void setMethodArguments (Class<?>[] methodArguments) {
        this.methodArguments = methodArguments;
    }

    public Class<?> getBeforeClass () {
        return beforeClass;
    }

    public void setBeforeClass (Class<?> beforeClass) {
        this.beforeClass = beforeClass;
    }

    public String getBeforeMethod () {
        return beforeMethod;
    }

    public void setBeforeMethod (String beforeMethod) {
        this.beforeMethod = beforeMethod;
    }

    public Class<?>[] getBeforeArguments () {
        return beforeArguments;
    }

    public void setBeforeArguments (Class<?>[] beforeArguments) {
        this.beforeArguments = beforeArguments;
    }

    public Class<?> getAfterClass () {
        return afterClass;
    }

    public void setAfterClass (Class<?> afterClass) {
        this.afterClass = afterClass;
    }

    public String getAfterMethod () {
        return afterMethod;
    }

    public void setAfterMethod (String afterMethod) {
        this.afterMethod = afterMethod;
    }

    public Class<?>[] getAfterArguments () {
        return afterArguments;
    }

    public void setAfterArguments (Class<?>[] afterArguments) {
        this.afterArguments = afterArguments;
    }

}
