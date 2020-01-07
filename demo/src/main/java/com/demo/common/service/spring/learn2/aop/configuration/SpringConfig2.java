package com.demo.common.service.spring.learn2.aop.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(value = {"com.demo.common.service.spring.learn2.aop"},
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = AopConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SpringConfig.class)
        })
public class SpringConfig2 {
}
