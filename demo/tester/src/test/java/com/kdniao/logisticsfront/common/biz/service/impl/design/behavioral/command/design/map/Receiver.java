package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.command.design.map;

// 接收者
public class Receiver {
    public void action(){
        System.out.println("执行 执行命令");
    }

    public void unAction(){
        System.out.println("执行 撤销命令");
    }
}
