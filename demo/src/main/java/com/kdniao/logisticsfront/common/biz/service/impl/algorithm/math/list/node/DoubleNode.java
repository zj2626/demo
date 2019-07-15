package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.node;

/**
 * 双向节点
 *
 * @param <E>
 */
public class DoubleNode<E> {
    private E data;
    private DoubleNode<E> next;

    public DoubleNode() {
    }

    public DoubleNode(E data, DoubleNode next) {
        this.data = data;
        this.next = next;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public boolean hasNext() {
        return null != next;
    }


    public DoubleNode<E> next() {
        return next;
    }

    public DoubleNode<E> getNext() {
        return next;
    }

    public void setNext(DoubleNode<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", next=" + next +
                '}';
    }
}
