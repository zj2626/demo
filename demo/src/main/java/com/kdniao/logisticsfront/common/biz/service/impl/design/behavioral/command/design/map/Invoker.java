package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.command.design.map;

// 调用者
public class Invoker {
    // 一个命令对象
    private Command command;

    public Invoker(Command command) {
        this.command = command;
    }

    public void excuteCommmand(){
        command.excute();
    }

    public void undoCommand(){
        command.unDo();
    }
}
