package com.demo.common.service.spring.learn0.injection.configuration;

import com.demo.common.service.spring.learn0.injection.bean.DemoAnnotationDao;
import com.demo.common.service.spring.learn0.injection.bean.DemoJavaBasedService;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan(value="com.demo.common.service.spring.**.bean")
@ImportResource(value="classpath:framework/spring-injection-byType.xml")
public class SpringConfig2 {

    @Bean
    @Profile("dev")
    public DemoJavaBasedService getDemoJavaBasedServiceDev(DemoAnnotationDao dao){
        DemoJavaBasedService service = new DemoJavaBasedService();
        service.setBase(888);
        service.setDao(dao);
        return service;
    }

    @Bean
    @Profile("prod")
    public DemoJavaBasedService getDemoJavaBasedServiceProd(DemoAnnotationDao dao){
        DemoJavaBasedService service = new DemoJavaBasedService();
        service.setBase(666);
        service.setDao(dao);
        return service;
    }
}
