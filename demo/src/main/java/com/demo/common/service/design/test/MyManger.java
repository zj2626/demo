package com.demo.common.service.design.test;

public class MyManger extends CommandManager {

    @Override
    protected Command createCommand() {
        System.out.println("MyManger createCommand");
        return null;
    }
}
