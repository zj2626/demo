package com.demo.common.service.design.structural.flyweight.design.map;

/**
 * 抽象享元类
 */
public interface Flyweight {
    void operation(UnsharedConcreteFlyweight unsharedConcreteFlyweight);
}
