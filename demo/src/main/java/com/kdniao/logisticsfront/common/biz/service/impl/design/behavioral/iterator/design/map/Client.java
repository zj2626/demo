package com.kdniao.logisticsfront.common.biz.service.impl.design.behavioral.iterator.design.map;

import org.junit.Test;


public class Client {

    @Test
    public void test1() {
        AggregateList aggregateList = new ConcreteAggregateList();
        aggregateList.add("a");
        aggregateList.add("b");
        aggregateList.add("fuck");
        aggregateList.add("ccccccccc");
        aggregateList.add("Ed");

        System.out.println(aggregateList.size());
        System.out.println(aggregateList.get(2));

        System.out.println("#############");

        Iterator iterator = aggregateList.iterator();
        while (!iterator.isDone()){
            System.out.println(iterator.next());
        }
    }

}
