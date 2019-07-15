package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.interfaces;

public interface StackInterface<E> {

    // 入栈
    public void push(E e);

    // 出栈
    public E pop();

    // 获取栈顶元素
    public E peek();
}
