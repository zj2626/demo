package com.kdniao.logisticsfront.common.biz.service.impl.algorithm.math.list.node;

/**
 * 双向节点
 *
 * @param <E>
 */
public class DoublyNode<E> {
    private E data;
    private DoublyNode<E> next;
    private DoublyNode<E> prev;

    public DoublyNode() {
    }

    public DoublyNode(E data, DoublyNode next, DoublyNode prev) {
        this.data = data;
        this.next = next;
        this.prev = prev;
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


    public DoublyNode<E> next() {
        return next;
    }

    public DoublyNode<E> prev() {
        return prev;
    }

    public DoublyNode<E> getNext() {
        return next;
    }

    public void setNext(DoublyNode<E> next) {
        this.next = next;
    }

    public DoublyNode<E> getPrev() {
        return prev;
    }

    public void setPrev(DoublyNode<E> prev) {
        this.prev = prev;
    }

    @Override
    public String toString() {
        return "<" + data.toString() + ">";
    }
}
