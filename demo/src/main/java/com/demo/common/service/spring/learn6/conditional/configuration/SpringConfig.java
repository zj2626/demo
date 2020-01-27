package com.demo.common.service.spring.learn6.conditional.configuration;

import com.demo.common.service.spring.learn6.conditional.bean.DemoDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.demo.common.service.spring.learn6.conditional.bean")
public class SpringConfig {

    @Bean("DemoDao")
    @Conditional({WindowsCondition.class})
    public DemoDao getDemoDao(){
        DemoDao dao = new DemoDao();
        dao.setId(10);
        return dao;
    }

    @Bean("DemoDao")
    @Conditional({LinuxCondition.class})
    public DemoDao getDemoDao2(){
        DemoDao dao = new DemoDao();
        dao.setId(20);
        return dao;
    }
}
