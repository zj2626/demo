package com.demo.common.service.design.behavioral.observer;

// 具体观察者
public class Dad implements WakeUpListener {
    @Override
    public void performAction(WakeUpEvent wakeUpEvent) {
        System.out.println("到" + wakeUpEvent.getLocation() + " 喂" + wakeUpEvent.getChild());
    }
}
