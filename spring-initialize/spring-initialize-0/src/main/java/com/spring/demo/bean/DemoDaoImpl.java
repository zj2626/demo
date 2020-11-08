package com.spring.demo.bean;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class DemoDaoImpl implements DemoDao {
    private Integer id;


    public DemoDaoImpl() {
        System.out.println("DemoDao Constructor " + id);
    }

    @PostConstruct
    public void initPostConstruct() {
        id = 2;
        System.out.println("DemoDao PostConstruct" + id);
    }

    @Override
    public void doQuery() {
        System.out.println("DemoDao query sql " + id);
    }

    @Override
    public String toString() {
        return "[TO_STRING] DemoDaoImpl{" +
                "id=" + id +
                '}';
    }
}
