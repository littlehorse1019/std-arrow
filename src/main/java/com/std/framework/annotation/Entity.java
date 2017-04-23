package com.std.framework.annotation;


import com.std.framework.model.orm.MapRule;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义Entity注解
 * 用于注解在DO类上，自动进行ORM关系映射
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface Entity {

    public String showSql () default "false";

    public Class<? extends MapRule> value ();
}
