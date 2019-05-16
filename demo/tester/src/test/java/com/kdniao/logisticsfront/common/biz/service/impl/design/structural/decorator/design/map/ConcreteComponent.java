package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.decorator.design.map;

//具体实现类，也是被装饰类，他本身是个具有一些功能的完整的类。
public class ConcreteComponent implements Component {
    @Override
    public void operation() {
        System.out.println("operation");
    }
}
