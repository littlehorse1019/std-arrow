package com.std.framework.controller;

/**
 * @author Luox 该类用于获取IOC拼装好的类
 */
public class BeanFactory {

    public static Object getMVCBean (String beanName) throws Exception {
        return BeansHolder.getBeanResource(beanName);
    }

}
