package com.demo.common.service.spring.learn2.aop.bean;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface MyLogs {
    String value() default "default";
}
