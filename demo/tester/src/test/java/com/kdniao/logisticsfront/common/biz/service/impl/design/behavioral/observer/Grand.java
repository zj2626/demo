package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.observer;

// 具体观察者
public class Grand implements WakeUpListener {
    @Override
    public void performAction(WakeUpEvent wakeUpEvent) {
        System.out.println("到" + wakeUpEvent.getLocation() + " 哄" + wakeUpEvent.getChild());
    }
}
