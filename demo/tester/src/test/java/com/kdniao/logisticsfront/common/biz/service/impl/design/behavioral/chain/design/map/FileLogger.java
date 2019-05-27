package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.chain.design.map;

public class FileLogger extends AbstractLogger {
    public FileLogger(int level) {
        super(level);
    }

    @Override
    public void write(String message) {
        System.out.println("日志写入文件 -- "+ message);
    }
}
