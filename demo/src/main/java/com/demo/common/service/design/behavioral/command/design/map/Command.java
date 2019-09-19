package com.demo.common.service.design.behavioral.command.design.map;

// 抽象命令类
public interface Command {
    //执行操作
    public void excute();

    //撤销操作
    public void unDo();
}
