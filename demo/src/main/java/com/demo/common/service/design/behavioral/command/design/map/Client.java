package com.demo.common.service.design.behavioral.command.design.map;

import org.junit.Test;

// 客户类
public class Client {
    @Test
    public void test1() {
        Receiver receiver = new Receiver();

        Command command = new ConcreteCommand(receiver);

        Invoker invoker = new Invoker(command);

        invoker.excuteCommmand();
    }

    // Client -> Invoker.excuteCommmand -> Command.excute -> Receiver.action

    /**
     * 命令模式将发出请求的对象和执行请求的对象进行解耦;
     *             Invoker     Receiver
     */
}
