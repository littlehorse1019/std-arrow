package com.std.framework.controller;

/**
 * @author Luox �������ڻ�ȡIOCƴװ�õ���
 */
public class BeanFactory {

    public static Object getMVCBean (String beanName) throws Exception {
        return BeansHolder.getBeanResource(beanName);
    }

}
