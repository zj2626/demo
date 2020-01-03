package com.demo.common.service.spring.learn0.injection.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(value="com.demo.common.service.spring.**.bean")
@ImportResource(value="classpath:framework/spring-injection-byType.xml")
public class SpringConfig2 {
}
