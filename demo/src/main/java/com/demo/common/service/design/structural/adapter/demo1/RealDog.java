package com.demo.common.service.design.structural.adapter.demo1;

/**
 * Dog的实现类，具体的Dog
 */
public class RealDog implements Dog {
    @Override
    public void shout() {
        System.out.println("狗汪汪叫！");
    }

    @Override
    public void run() {
        System.out.println("狗跑！");
    }

}
