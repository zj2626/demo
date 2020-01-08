package com.demo.common.service.spring.learn2.aop.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
//@Scope("prototype")
public class DemoServiceImpl implements DemoService {

    @MyLogs("queryById-method")
    @Override
    public void queryById(String id, String name, String v1, String v2, Integer age, String v3, String v4, Integer v5) {
        System.out.println("doSomeThing in " + age + " by " + name + "( by " + id + " ) >>> " + this.hashCode());
    }
}
