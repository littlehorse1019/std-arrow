package com.std.framework.annotation;


import com.std.framework.view.interceptor.CoreInterceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Interceptor {
    Class<? extends CoreInterceptor>[] value();
}