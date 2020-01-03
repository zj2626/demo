package com.demo.common.service.spring.learn1.lifecycle.bean;

import org.springframework.stereotype.Repository;

@Repository
public class DemoLifecycleDao {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
