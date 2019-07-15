package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.interfaces;

public interface QueueInterface<E> {
    // 入队
    public void enqueue(E e);

    // 出队
    public E dequeue();

    // 获取队首元素
    public E peek();
}
