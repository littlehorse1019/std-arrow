package com.std.framework.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Clear注解
 * 用于清除{@link Interceptor}注解标注的静态代理,注解于方法之上
 * 常用于{@link Interceptor}标注在class上的时候，通过Clear清除其中不需要全局拦截或全类拦截的方法之上
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface Clear {

}
