package com.demo.common.service.spring.learn0.injection.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value="com.demo.common.service.spring.**.bean")
public class SpringConfig {
}
