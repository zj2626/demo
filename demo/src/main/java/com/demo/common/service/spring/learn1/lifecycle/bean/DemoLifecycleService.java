package com.demo.common.service.spring.learn1.lifecycle.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class DemoLifecycleService implements InitializingBean {
    @Autowired
    private DemoLifecycleDao lifecycleDao;

    {
        System.out.println("非静态代码块: " + lifecycleDao);
    }

    public DemoLifecycleService() {
        System.out.println("构造方法: " + lifecycleDao);
    }

    /*
        1. spring为bean提供了两种初始化bean的方式，实现InitializingBean接口，实现afterPropertiesSet方法，或者在配置文件中通过init-method指定，两种方式可以同时使用
        2. 实现InitializingBean接口是直接调用afterPropertiesSet方法，比通过反射调用init-method指定的方法效率相对来说要高点。但是init-method方式消除了对spring的依赖
        3. 先调用afterPropertiesSet，再执行 init-method 方法，如果调用afterPropertiesSet方法时出错，则不调用init-method指定的方法。
        4. 关于PostConstruct: BeanPostProcessor的postProcessBeforeInitialization是在Bean生命周期中afterPropertiesSet和init-method之前被调用的
        5. Spring bean的初始化执行顺序：构造方法 --> @PostConstruct注解的方法 --> afterPropertiesSet方法 --> init-method指定的方法。
     */
    @PostConstruct
    public void postConstruct() {
        System.out.println("postConstruct: " + lifecycleDao);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet: " + lifecycleDao);
    }

    public void getDaoInfo() {
        System.out.println("注解注入:           " + lifecycleDao);
    }
}
