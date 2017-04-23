package com.std.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Global注解
 *
 * 用于注解在拦截器自定义的类上，表示全局拦截器
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE})
public @interface Global {

    int order () default 1;
}
