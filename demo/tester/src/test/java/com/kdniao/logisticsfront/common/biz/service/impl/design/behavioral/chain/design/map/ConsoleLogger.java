package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.chain.design.map;

public class ConsoleLogger extends AbstractLogger {
    public ConsoleLogger(int level) {
        super(level);
    }

    @Override
    public void write(String message) {
        System.out.println("日志打印到控制台 -- " + message);
    }
}
