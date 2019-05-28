package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.iterator.design.map;

// 迭代器角色 :定义遍历元素所需要的方法，一般来说会有这么三个方法：取得下一个元素的方法next()，判断是否遍历结束的方法hasNext()），移出当前对象的方法remove()
public interface Iterator {
    public Object first();

    public Object next();

    public boolean isDone();

    public Object CurrentItem();
}
