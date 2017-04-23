package com.std.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义ActionService注解
 * 用于注解在Controller层中的方法上，表示该方法暴漏给CoreAction进行代理调用
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface ActionService {
}
