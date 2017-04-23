package com.std.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Proxy动态代理类
 * 用于注解在AOP动态代理上,配合{@link Advisor},{@link com.std.framework.core.proxy.ProxyGenerator}
 * 来使用，起到字节码加载动态代理的作用
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface Proxy {
}
