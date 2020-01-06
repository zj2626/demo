package com.demo.common.service.spring.learn2.aop.bean;

import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public void queryById() {
        System.out.println("doSomeThing");
    }
}
