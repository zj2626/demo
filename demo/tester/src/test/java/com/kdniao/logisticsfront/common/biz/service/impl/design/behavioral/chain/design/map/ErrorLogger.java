package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.chain.design.map;

public class ErrorLogger extends AbstractLogger {
    public ErrorLogger(int level) {
        super(level);
    }

    @Override
    public void write(String message) {
        System.out.println("错误日志 -- " + message);
    }
}
