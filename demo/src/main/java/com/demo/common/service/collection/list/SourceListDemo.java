package com.demo.common.service.collection.list;

import com.demo.common.service.collection.list.source.ArrayListDemo;
import org.junit.Test;

public class SourceListDemo {
    @Test
    public void addTest() {
        ArrayListDemo<String> list = new ArrayListDemo<>();
        list.add("cc");
        list.add("aaa");
        System.out.println(list);
        System.out.println("================");
        // System.out.println(list.get());
    }
}
