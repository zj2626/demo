package com.demo.common.service.spring.learn0.injection.bean;

import org.springframework.stereotype.Repository;

@Repository
public class DemoAnnotationDao {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
