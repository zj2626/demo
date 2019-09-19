package com.demo.common.service.design.behavioral.observer;

// 描述了事件的一些基本的信息:时间+地点+被观察对象
public class WakeUpEvent {
    private long time;
    private String location;
    private Child child;

    public WakeUpEvent(long time, String location, Child child) {
        this.time = time;
        this.location = location;
        this.child = child;
    }

    public long getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public Child getChild() {
        return child;
    }

}
