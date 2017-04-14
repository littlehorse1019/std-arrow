package com.std.framework.controller.aop;


import com.std.framework.annotation.Advisor;
import com.std.framework.annotation.PointCut;
import com.std.framework.container.c.ControllerException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;

/**
 * @author Luox 该类用于保存配置文件中类的配置元素对象关系
 */
public class AOPDefinition {

    private String            className = "";
    private List<AdvisorBean> list      = new ArrayList<>();

    public String getClassName () {
        return className;
    }

    public List<AdvisorBean> getAdvisorBeanList () {
        return list;
    }

    public void loadAOPDefine2Cache (Node advisorNode, AOPCache aopCache) {
        //TODO 解析AOP XML类型配置
    }

    public void loadAOPDefine2Cache (Class<?> clazz, AOPCache aopCache) {
        className = clazz.getName();
        list = getAdvisorBeanList(clazz);
        aopCache.cacheAOPDefine(this);
    }

    private List<AdvisorBean> getAdvisorBeanList (Class<?> clazz) {
        List<AdvisorBean> advisorList = new ArrayList<>();
        List<Method>      methodList  = getAdvisorMethod(clazz);
        for (Method m : methodList) {
            AdvisorBean advisorBean = generateAdvisorBean(m);
            advisorList.add(advisorBean);
        }
        return advisorList;
    }

    private AdvisorBean generateAdvisorBean (Method m) {
        Class<?>[] parameterTypes = m.getParameterTypes();
        Advisor    advisor        = m.getAnnotation(Advisor.class);
        checkAdvisorValid(advisor);
        AdvisorBean ab = new AdvisorBean();
        ab.setMethodName(m.getName());
        ab.setMethodArguments(parameterTypes);
        PointCut[] cutPositions = advisor.cutPosition();
        String[]   cutMethods   = advisor.cutMethod();
        Class<?>[] cutClasses   = advisor.value();
        for (int i = 0; i < cutPositions.length; i++) {
            if (cutPositions[i] == PointCut.Before) {
                ab.setBeforeClass(cutClasses[i]);
                ab.setBeforeMethod(cutMethods[i]);
                ab.setBeforeArguments(parameterTypes);
            }
            if (cutPositions[i] == PointCut.After) {
                ab.setAfterClass(cutClasses[i]);
                ab.setAfterMethod(cutMethods[i]);
                ab.setAfterArguments(parameterTypes);
            }
        }
        return ab;
    }


    private void checkAdvisorValid (Advisor advisor) {
        PointCut[] cutPosition = advisor.cutPosition();
        String[]   cutMethod   = advisor.cutMethod();
        Class<?>[] cutClasses  = advisor.value();
        if (cutPosition.length != cutMethod.length || cutPosition.length != cutClasses.length) {
            throw new ControllerException("所声明切面类型和方法不匹配");
        }
        if (cutPosition.length > 2 || cutMethod.length > 2 || cutClasses.length > 2) {
            throw new ControllerException("所声明切面类不能超过2个(Before,After)");
        }
        if (cutPosition.length == 2 && cutPosition[0] == cutPosition[1]) {
            throw new ControllerException("所声明切面类型不能同为" + cutPosition[1]);
        }
    }

    private List<Method> getAdvisorMethod (Class<?> clazz) {
        List<Method> list    = new ArrayList<>();
        Method[]     methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Advisor.class)) {
                list.add(m);
            }
        }
        return list;
    }

}
