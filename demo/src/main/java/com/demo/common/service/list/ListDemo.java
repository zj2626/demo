package com.demo.common.service.list;

import com.demo.common.service.list.abs.ListInfo;
import org.junit.Test;

public class ListDemo extends ListInfo {
    @Test
    public void test() {
        System.out.println(listA.isEmpty());
    }
}
