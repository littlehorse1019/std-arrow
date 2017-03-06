package com.std.framework.core.proxy;


import com.std.framework.annotation.PointCut;
import com.std.framework.container.c.ControllerException;
import com.std.framework.controller.aop.AdvisorBean;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox 动态代理生成器，根据代理配置设置，生成代理类(字符编码生成真正的AOP类) 代理属性设置在该类之中，所以不能使用静态方法，每次生成代理要new一个新的ProxyGenerator.
 */
public class ProxyGenerator {
    private static int proxyCount;
    private ProxyCfgBean proxyCfg = null;

    // 绑定被代理类相关的所有属性配置
    public void bind(ProxyCfgBean proxyCfg) {
        this.proxyCfg = proxyCfg;
    }

    public Object create() throws Exception {
        final Class<?> targetClass = Class.forName(proxyCfg.getClassName());
        Object newProxyInstance = newProxyInstance(new ProxyHandler() {
            @Override
            public Object invoke(Method method, Object... args) {
                Object returnValue = null;
                try {
                    Object target = targetClass.newInstance();
                    String methodName = method.getName();
                    invokeBefore(methodName, args);
                    returnValue = method.invoke(target, args);
                    invokeAfter(methodName, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return returnValue;
            }

            private void invokeBefore(String methodName, Object[] args) throws Exception {
                for (AdvisorBean advisorBean : proxyCfg.getAdvisorBeanList()) {
                    Class<?> beforeClass = advisorBean.getBeforeClass();
                    if (isMatchingAdvisor(beforeClass, advisorBean, methodName, args, PointCut.Before)) {
                        Object beforeAdvisor = beforeClass.newInstance();
                        Method beforeMethods = getBeforeMethods(beforeClass, advisorBean);
                        beforeMethods.invoke(beforeAdvisor, args);
                    }
                }
            }

            private void invokeAfter(String methodName, Object[] args) throws Exception {
                for (AdvisorBean advisorBean : proxyCfg.getAdvisorBeanList()) {
                    Class<?> afterClass = advisorBean.getAfterClass();
                    if (isMatchingAdvisor(afterClass, advisorBean, methodName, args, PointCut.After)) {
                        Object afterAdvisor = afterClass.newInstance();
                        Method afterMethods = getAfterMethods(afterClass, advisorBean);
                        afterMethods.invoke(afterAdvisor, args);
                    }
                }
            }

            //判断切入事件是否完全吻合
            private boolean isMatchingAdvisor(Class<?> advisotClass, AdvisorBean advisorBean, String methodName,
                                              Object[] args, PointCut cutPosition) {
                if (advisotClass == null)
                    return false;
                if (!advisorBean.getMethodName().equals(methodName))
                    return false;
                Class<?>[] advisorArguments = new Class<?>[]{};
                if (cutPosition == PointCut.Before)
                    advisorArguments = advisorBean.getBeforeArguments();
                if (cutPosition == PointCut.After)
                    advisorArguments = advisorBean.getAfterArguments();
                if (args.length != advisorArguments.length)
                    return false;
                for (int i = 0; i < args.length; i++) {
                    if (!advisorArguments[i].isInstance(args[i])) {
                        return false;
                    }
                }
                return true;
            }

        }, targetClass);
        return newProxyInstance;
    }

    /**
     * 代理类生成，传入动态代理回调类和指定代理方法集合
     */
    private Object newProxyInstance(ProxyHandler handler, Class<?> clazz) throws Exception {
        List<Method> proxyMethods = getProxyMethods(clazz);
        MemoryCompiler compiler = new MemoryCompiler(proxyCount);
        Object proxy = compiler.generatorProxyInMemory(clazz, proxyMethods);
        proxy.getClass().getField("proxyHandler").set(proxy, handler);
        ProxyGenerator.proxyCount++;
        return proxy;
    }

    // 抽取被代理方法集合
    private List<Method> getProxyMethods(Class<?> targetClazz) throws SecurityException, NoSuchMethodException {
        List<Method> methodList = new ArrayList<Method>();
        for (AdvisorBean advisorBean : proxyCfg.getAdvisorBeanList()) {
            Method matchedMethod = targetClazz.getDeclaredMethod(advisorBean.getMethodName(), advisorBean.getMethodArguments());
            methodList.add(matchedMethod);
        }
        return methodList;
    }

    // 抽取Before的接口实现集合
    private Method getBeforeMethods(Class<?> interceptorClazz, AdvisorBean advisorBean) {
        String beforeMethod = advisorBean.getBeforeMethod();
        Class<?>[] beforeArguments = advisorBean.getBeforeArguments();
        try {
            return interceptorClazz.getDeclaredMethod(beforeMethod, beforeArguments);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throwAdvisorException();
        }
        return null;
    }

    // 抽取After的接口实现集合
    private Method getAfterMethods(Class<?> interceptorClazz, AdvisorBean advisorBean) {
        String afterMethod = advisorBean.getAfterMethod();
        Class<?>[] afterArguments = advisorBean.getAfterArguments();
        try {
            return interceptorClazz.getDeclaredMethod(afterMethod, afterArguments);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throwAdvisorException();
        }
        return null;
    }

    private void throwAdvisorException() {
        throw new ControllerException("未找到指定的切面方法，请确保advisor名称正确并且参数和被增强方法一致!");
    }
}
