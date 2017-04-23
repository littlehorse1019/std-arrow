package com.std.framework.annotation;


import com.std.framework.view.interceptor.BaseInterceptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Interceptor注解
 * 拦截器配置 用于注解在方法和Class之上，表示方法拦截或类全局拦截
 * 可以通过{@link Clear}注解取消
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE, ElementType.METHOD})
public @interface Interceptor {

    Class<? extends BaseInterceptor>[] value ();
}
