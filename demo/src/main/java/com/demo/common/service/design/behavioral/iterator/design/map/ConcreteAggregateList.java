package com.demo.common.service.design.behavioral.iterator.design.map;

// 具体容器角色: 就是抽象容器的具体实现类，比如List接口的有序列表实现ArrayList，List接口的链表实现LinkList，Set接口的哈希列表的实现HashSet等
public class ConcreteAggregateList implements AggregateList {
    private Object[] list;
    private Iterator iterator;
    private int index = 0;
    private int total = 10;

    public ConcreteAggregateList(){
        list = new Object[total];
    }

    @Override
    public Iterator iterator() {
        return new ConcreteIterator(this);
    }

    @Override
    public int size() {
        return index;
    }

    @Override
    public Object get(int index) {
        return list[index];
    }

    @Override
    public void add(Object obj) {
        list[index] = obj;
        index++;
    }
}
