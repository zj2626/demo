package com.demo.common.service.spring.learn0.injection.bean;

import org.springframework.stereotype.Repository;

@Repository("demoDao")
public class DemoDao {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
