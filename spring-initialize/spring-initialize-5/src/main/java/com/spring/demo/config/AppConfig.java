package com.spring.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan(value = "com.spring.demo")
public class AppConfig {

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver(){
        InternalResourceViewResolver resourceViewResolver = new InternalResourceViewResolver();
        //        resourceViewResolver.setViewClass(JstlView.class);
        resourceViewResolver.setPrefix("/");
        resourceViewResolver.setSuffix(".jsp");
        return resourceViewResolver;
    }

    /*
    模拟springboot文件上传配置 实际的springboot中配置的是 StandardMultipartResolver 也不需要引入任何包(内置)
     */
    @Bean("multipartResolver")
    public CommonsMultipartResolver commonsMultipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        return resolver;
    }
}
