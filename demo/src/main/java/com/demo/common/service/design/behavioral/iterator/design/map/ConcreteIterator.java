package com.demo.common.service.design.behavioral.iterator.design.map;

// 具体迭代器角色
public class ConcreteIterator implements Iterator {
    private AggregateList list;
    private int index = 0;

    public ConcreteIterator(AggregateList list) {
        super();
        this.list = list;
    }

    @Override
    public Object first() {
        return this.list.get(0);
    }

    @Override
    public Object next() {
        if (index < this.list.size()) {
            Object object = this.list.get(index);
            index++;
            return object;
        } else {
            return null;
        }
    }

    @Override
    public boolean isDone() {
        return index >= this.list.size();
    }

    @Override
    public Object CurrentItem() {
        return this.list.get(index);
    }
}
