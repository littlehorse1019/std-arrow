package com.std.framework.core.proxy;

import com.std.framework.controller.aop.AdvisorBean;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luox AOP Bean Ù–‘…Ë÷√≥–‘ÿ¿‡
 */
public class ProxyCfgBean {

    private String            iocBeanId       = "";
    private String            className       = "";
    private List<AdvisorBean> advisorBeanList = new ArrayList<AdvisorBean>();

    public String getIocBeanId () {
        return iocBeanId;
    }

    public void setIocBeanId (String iocBeanId) {
        this.iocBeanId = iocBeanId;
    }

    public String getClassName () {
        return className;
    }

    public void setClassName (String className) {
        this.className = className;
    }

    public List<AdvisorBean> getAdvisorBeanList () {
        return advisorBeanList;
    }

    public void setAdvisorBeanList (List<AdvisorBean> advisorBeanList) {
        this.advisorBeanList = advisorBeanList;
    }

    public void addAdvisorBean (AdvisorBean advisorBean) {
        advisorBeanList.add(advisorBean);
    }

}
