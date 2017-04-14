package com.std.framework.core.proxy;


import com.std.framework.annotation.PointCut;
import com.std.framework.container.c.ControllerException;
import com.std.framework.controller.aop.AdvisorBean;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox ��̬���������������ݴ����������ã����ɴ�����(�ַ���������������AOP��) �������������ڸ���֮�У����Բ���ʹ�þ�̬������ÿ�����ɴ���Ҫnewһ���µ�ProxyGenerator.
 */
public class ProxyGenerator {

    private static int proxyCount;
    private ProxyCfgBean proxyCfg = null;

    // �󶨱���������ص�������������
    public void bind (ProxyCfgBean proxyCfg) {
        this.proxyCfg = proxyCfg;
    }

    public Object create () throws Exception {
        final Class<?> targetClass = Class.forName(proxyCfg.getClassName());
        Object newProxyInstance = newProxyInstance(new ProxyHandler() {
            @Override
            public Object invoke (Method method, Object... args) {
                Object returnValue = null;
                try {
                    Object target     = targetClass.newInstance();
                    String methodName = method.getName();
                    invokeBefore(methodName, args);
                    returnValue = method.invoke(target, args);
                    invokeAfter(methodName, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return returnValue;
            }

            private void invokeBefore (String methodName, Object[] args) throws Exception {
                for (AdvisorBean advisorBean : proxyCfg.getAdvisorBeanList()) {
                    Class<?> beforeClass = advisorBean.getBeforeClass();
                    if (isMatchingAdvisor(beforeClass, advisorBean, methodName, args, PointCut.Before)) {
                        Object beforeAdvisor = beforeClass.newInstance();
                        Method beforeMethods = getBeforeMethods(beforeClass, advisorBean);
                        beforeMethods.invoke(beforeAdvisor, args);
                    }
                }
            }

            private void invokeAfter (String methodName, Object[] args) throws Exception {
                for (AdvisorBean advisorBean : proxyCfg.getAdvisorBeanList()) {
                    Class<?> afterClass = advisorBean.getAfterClass();
                    if (isMatchingAdvisor(afterClass, advisorBean, methodName, args, PointCut.After)) {
                        Object afterAdvisor = afterClass.newInstance();
                        Method afterMethods = getAfterMethods(afterClass, advisorBean);
                        afterMethods.invoke(afterAdvisor, args);
                    }
                }
            }

            //�ж������¼��Ƿ���ȫ�Ǻ�
            private boolean isMatchingAdvisor (Class<?> advisotClass, AdvisorBean advisorBean, String methodName,
                Object[] args, PointCut cutPosition) {
                if (advisotClass == null) {
                    return false;
                }
                if (!advisorBean.getMethodName().equals(methodName)) {
                    return false;
                }
                Class<?>[] advisorArguments = new Class<?>[]{};
                if (cutPosition == PointCut.Before) {
                    advisorArguments = advisorBean.getBeforeArguments();
                }
                if (cutPosition == PointCut.After) {
                    advisorArguments = advisorBean.getAfterArguments();
                }
                if (args.length != advisorArguments.length) {
                    return false;
                }
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
     * ���������ɣ����붯̬����ص����ָ������������
     */
    private Object newProxyInstance (ProxyHandler handler, Class<?> clazz) throws Exception {
        List<Method>   proxyMethods = getProxyMethods(clazz);
        MemoryCompiler compiler     = new MemoryCompiler(proxyCount);
        Object         proxy        = compiler.generatorProxyInMemory(clazz, proxyMethods);
        proxy.getClass().getField("proxyHandler").set(proxy, handler);
        ProxyGenerator.proxyCount++;
        return proxy;
    }

    // ��ȡ������������
    private List<Method> getProxyMethods (Class<?> targetClazz) throws SecurityException, NoSuchMethodException {
        List<Method> methodList = new ArrayList<>();
        for (AdvisorBean advisorBean : proxyCfg.getAdvisorBeanList()) {
            Method matchedMethod = targetClazz
                .getDeclaredMethod(advisorBean.getMethodName(), advisorBean.getMethodArguments());
            methodList.add(matchedMethod);
        }
        return methodList;
    }

    // ��ȡBefore�Ľӿ�ʵ�ּ���
    private Method getBeforeMethods (Class<?> interceptorClazz, AdvisorBean advisorBean) {
        String     beforeMethod    = advisorBean.getBeforeMethod();
        Class<?>[] beforeArguments = advisorBean.getBeforeArguments();
        try {
            return interceptorClazz.getDeclaredMethod(beforeMethod, beforeArguments);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throwAdvisorException();
        }
        return null;
    }

    // ��ȡAfter�Ľӿ�ʵ�ּ���
    private Method getAfterMethods (Class<?> interceptorClazz, AdvisorBean advisorBean) {
        String     afterMethod    = advisorBean.getAfterMethod();
        Class<?>[] afterArguments = advisorBean.getAfterArguments();
        try {
            return interceptorClazz.getDeclaredMethod(afterMethod, afterArguments);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throwAdvisorException();
        }
        return null;
    }

    private void throwAdvisorException () {
        throw new ControllerException("δ�ҵ�ָ�������淽������ȷ��advisor������ȷ���Ҳ����ͱ���ǿ����һ��!");
    }
}
