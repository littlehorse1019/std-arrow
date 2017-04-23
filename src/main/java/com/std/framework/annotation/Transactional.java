package com.std.framework.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义Transactional注解
 * 用于注解在需要JDBC事物的方法上，表示对整个方法进行数据库事物控制
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.METHOD})
public @interface Transactional {

}
