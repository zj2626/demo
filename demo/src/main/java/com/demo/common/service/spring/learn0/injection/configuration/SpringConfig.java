package com.demo.common.service.spring.learn0.injection.configuration;

import com.demo.common.service.spring.learn0.injection.bean.DemoAnnotationDao;
import com.demo.common.service.spring.learn0.injection.bean.DemoJavaBasedService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value="com.demo.common.service.spring.**.bean")
public class SpringConfig {

    @Bean
    public DemoJavaBasedService getDemoJavaBasedService(DemoAnnotationDao dao){
        DemoJavaBasedService service = new DemoJavaBasedService();
        service.setBase(999);
        service.setDao(dao);
        return service;
    }
}
