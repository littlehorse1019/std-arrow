package com.std.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 自定义Job注解
 * 用于注解在Job定时作业调度的类上
 * Name表示作业名称，Main表示作业主方法入口，Cron为作业执行时间表达式， Times表示总执行次数限制
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface Job {

    String Name ();

    String Main ();

    String Cron ();

    long Times () default 0L;
}
