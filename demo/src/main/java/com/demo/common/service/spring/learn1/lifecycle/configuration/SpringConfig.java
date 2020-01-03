package com.demo.common.service.spring.learn1.lifecycle.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value="com.demo.common.service.spring.**.bean")
public class SpringConfig {
}
