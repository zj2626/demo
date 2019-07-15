package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list;

import com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.interfaces.QueueInterface;

/**
 * 线性表:
 * 队列: 先进先出 FIFO
 * 1.顺序队列: 可以使用循环数组解决出队后存储空间浪费问题
 * 2.链式队列
 * <p>
 * *.双端队列(Deque)
 */
public class MyQueue<E> implements QueueInterface<E> {
    @Override
    public void enqueue(E e) {

    }

    @Override
    public E dequeue() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}
