package com.demo.common.service.spring.learn0.injection.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
/*
Scope默认singleton, 如果scope为singleton的对象A依赖一个prototype的对象B,则对象B的scope相当于失效(每次拿到的对象B都是同一个,成为了singleton)
解决方法:在依赖的对象B的get方法上加注解 @Lookup
 */
public class DemoService {
    private DemoResource resource;
    private DemoDao demoOneDao;
    private DemoDao demoTwoDao;
    private DemoDao demoThreeDao;
    private DemoDao demoFourDao;
    @Autowired
    // @Qualifier("demoDao")
    private DemoDao demoFiveDao;
    @Autowired
    private DemoAnnotationDao annoDao;

    public void getDaoInfo(){
        System.out.println("setter注入:         " + demoOneDao);
        System.out.println("构造方法注入:       " + demoTwoDao);
        System.out.println("setter注入[自动装配:byName]:   " + demoThreeDao);
        System.out.println("setter注入[自动装配:byType]:   " + demoFourDao);
        System.out.println("注解注入:           " + demoFiveDao);
        System.out.println("注解注入:           " + annoDao);
        System.out.println("注解注入:           " + resource);
    }

    public DemoService() {
    }

    // 通过<constructor-arg ref="demoDao01"/>注入
    public DemoService(DemoDao dao) {
        this.demoTwoDao = dao;
    }

    // 通过<property name="demoOneDao" ref="demoDao01"/>注入
    public void setDemoOneDao(DemoDao dao) {
        this.demoOneDao = dao;
    }

    /**
     * 通过default-autowire="byType"注入, 如果不设置或者设置为[default-autowire="no"]则不会注入
     * 查找所有的set方法，将符合符合参数类型的bean注入
     * @param dao
     */
    public void setaAaA(DemoDao dao) {
        this.demoTwoDao = dao;
    }

    /**
     * 通过default-autowire="byName"注入, 如果不设置或者设置为[default-autowire="no"]则不会注入
     * 被注入bean的id名必须与set方法相匹配
     * @param dao
     */
    public void setDemoDao01(DemoDao dao) {
        this.demoThreeDao = dao;
    }

    /**
     * 通过default-autowire="byType"注入
     * @param dao
     */
    public void setDemoFourDao(DemoDao dao) {
        this.demoFourDao = dao;
    }

    /**
     * 通过default-autowire="byType"注入
     * @param resource
     */
    public void setResource(DemoResource resource) {
        this.resource = resource;
    }
}
