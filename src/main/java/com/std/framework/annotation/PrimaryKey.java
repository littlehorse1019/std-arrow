package com.std.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Primary注解
 * 用于注解在{@link Entity}的主键上，作为主键标识
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.FIELD)
public @interface PrimaryKey {

    public boolean isSeq () default true;
}
