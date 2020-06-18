package com.demo.common.service.basic.bean;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ParamDefaultValue {
    String value();
}
