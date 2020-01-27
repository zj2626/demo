package com.demo.common.service.spring.learn6.conditional.configuration;

import com.demo.common.service.spring.learn6.conditional.bean.DemoDao;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.demo.common.service.spring.learn6.conditional.bean")
public class SpringConfig2 {

    @Bean
    @Conditional({LinuxCondition.class}) //@Conditional要求是 linux系统才会注入bean到容器,so这里不会把DemoDao注入bean容器中
    public DemoDao demoDao() {
        DemoDao dao = new DemoDao();
        dao.setId(10);
        dao.setMsg("主要");
        return dao;
    }

    @Bean("demoDao")
    @ConditionalOnMissingBean({DemoDao.class}) // 当检查到容器中没有对应类的实例时,注入当前bean
    public DemoDao demoDao2() {
        DemoDao dao = new DemoDao();
        dao.setId(20);
        dao.setMsg("备用");
        return dao;
    }
}
