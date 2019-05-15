package com.kdniao.logisticsfront.common.biz.service.impl.design.structural.bridge;

/**
 * 抽象消息类
 *
 * 功能: 发送信息方式有多种,包含系统内短消息以及邮件消息;消息类型也分为多种,包含普通和加急类; 要求发送的时候可以多种组合选择发送.
 */
public abstract class AbstractMessage {
    //持有一个实现部分的对象
    MessageImplementor messageImplementor;

    /**
     * 构造方法，传入实现部分的对象
     *
     * @param messageImplementor 实现部分的对象
     */
    public AbstractMessage(MessageImplementor messageImplementor) {
        this.messageImplementor = messageImplementor;
    }

    /**
     * 发送消息，委派给实现部分的方法
     *
     * @param message 要发送消息的内容
     * @param toUser  消息的接受者
     */
    public void sendMessage(String message, String toUser) {
        this.messageImplementor.send(message, toUser);
    }
}