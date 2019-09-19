package com.demo.common.service.design.behavioral.command.design.map;

// 具体命令类
public class ConcreteCommand implements Command {
    // 一个接受者
    private Receiver receiver;

    public ConcreteCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public void excute() {
        // 接受者绑定执行动作
        receiver.action();
    }

    @Override
    public void unDo() {
        receiver.unAction();
    }
}
