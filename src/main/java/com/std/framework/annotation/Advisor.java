package com.std.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Advisor注解
 * 用于注解在Service层中的方法上，动态代理方法增强
 * value表示增强方法的class数组
 * cutPosition表示切入点  {@link PointCut}
 * cutMethod表示切入方法名称
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.METHOD)
public @interface Advisor {

    Class<?>[] value ();

    PointCut[] cutPosition ();

    String[] cutMethod ();
}
