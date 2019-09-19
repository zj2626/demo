package com.demo.common.service.design.behavioral.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个小孩在睡觉，当小孩醒过来之后，爸爸要feed，爷爷要哄哄抱抱，小狗汪汪叫。
 * 在这里这个睡觉的小孩就是被观察的对象，后面三个对象就是观察者，
 * 小孩的状态发生改变的时候，就相当于一个事件被触发了，观察者(或者应该叫做监听者)会做出相应的动作
 */
public class Child implements Runnable {
    private List<WakeUpListener> wakeUpListeners = new ArrayList<>();

    public void addListener(WakeUpListener wakeUpListener) {
        wakeUpListeners.add(wakeUpListener);
    }

    public void delListener(WakeUpListener wakeUpListener) {
        wakeUpListeners.remove(wakeUpListener);
    }

    private void wakeUp() {
        for (WakeUpListener wakeUpListener : wakeUpListeners) {
            wakeUpListener.performAction(new WakeUpEvent(System.currentTimeMillis(), "这里", this));
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.wakeUp();
    }

}
