package com.demo.common.service.design.behavioral.chain.design.map;

import org.junit.Test;

public class Client {
    @Test
    public void test1() {
        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);
        AbstractLogger fileLogger = new FileLogger(AbstractLogger.DEBUG);
        errorLogger.setNextLogger(consoleLogger);
        consoleLogger.setNextLogger(fileLogger);

        errorLogger.logWrite(AbstractLogger.DEBUG, "this is a debug log");
        System.out.println("______________________________________");
        errorLogger.logWrite(AbstractLogger.INFO, "this is a log");
        System.out.println("______________________________________");
        errorLogger.logWrite(AbstractLogger.ERROR, "this is a error log");
    }
}
/**
 * 状态模式是让各个状态对象自己知道其下一个处理的对象是谁，即在编译时便设定好了的；
 * <p>
 * 职责链模式中的各个对象并不指定其下一个处理的对象到底是谁，只有在客户端才设定。
 * <p>
 * 命令模式只将请求转发给一个特定对象。命令模式把一个申请特定操作的请求封装到一个对象中，并给该对象一个众所周知的公共接口，使客户端不用了解实际执行的操作就能产生请求，也可以使你改变操作而丝毫不影响客户端程序
 */