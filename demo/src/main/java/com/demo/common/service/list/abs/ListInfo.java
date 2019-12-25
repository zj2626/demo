package com.demo.common.service.list.abs;

import com.alibaba.fastjson.JSON;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListInfo {
    protected List<String> listA = new ArrayList<>();
    protected List<String> listB = new ArrayList<>();
    protected List<String> listC = Collections.singletonList(null);
    protected List<String> listD = Collections.emptyList();
    protected List<String> listE = null;
    protected String[] arrayE = {"-aka-", "eee", "FFF", "CCC"};

    @Before
    public void before() {
        listA.add("cc");
        listA.add("aaa");
        listA.add("FFFF");
        listA.add("eee");
        listA.add("BBB");

        listB.add("GGGGG");
        listB.add("aaa");
        listB.add("CCC");
        listB.add("FFFF");
        listB.add("gggg");

        System.out.println(JSON.toJSONString(listA));
        System.out.println(JSON.toJSONString(listB));
        System.out.println(JSON.toJSONString(listC));
        System.out.println(JSON.toJSONString(listD));
        System.out.println(JSON.toJSONString(listE));
    }
}
