package com.demo.common.service.list;

import com.alibaba.fastjson.JSON;
import com.demo.common.service.list.source.ArrayListDemo;
import org.junit.Test;

public class SourceListDemo {
    @Test
    public void addTest() {
        ArrayListDemo<String> list = new ArrayListDemo<>();
        list.add("cc");
        list.add("aaa");
        System.out.println(list);
    }
}
