package com.demo.common.service.spring.learn1.lifecycle.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoLifecycleService {
    @Autowired
    private DemoLifecycleDao lifecycleDao;

    public void getDaoInfo() {
        System.out.println("注解注入:           " + lifecycleDao);
    }
}
