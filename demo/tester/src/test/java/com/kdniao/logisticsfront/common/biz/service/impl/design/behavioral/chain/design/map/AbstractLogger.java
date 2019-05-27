package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.chain.design.map;

public abstract class AbstractLogger {
    public static Integer DEBUG = 1;
    public static Integer INFO = 2;
    public static Integer ERROR = 3;

    protected int level;
    private AbstractLogger nextLogger;

    public AbstractLogger(int level) {
        this.level = level;
    }

    public void setNextLogger(AbstractLogger nextLogger) {
        this.nextLogger = nextLogger;
    }

    public void logWrite(Integer level, String message) {
        try {
            if (level >= this.level) {
                write(message);
            }
        } finally {
            if (null != nextLogger) {
                nextLogger.logWrite(level, message);
            }
        }

    }

    public abstract void write(String message);
}
