package com.demo.common.service.algorithm.math.list.node;

public class Node<E> {
    private E data;
    private Node<E> next;

    public Node() {
    }

    public Node(E data, Node next) {
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


    public Node<E> next() {
        return next;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "<" + data.toString() + ">";
    }
}
