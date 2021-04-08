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
    protected List<String> listF = Collections.singletonList("FFFF");

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

        System.out.println("listA " + JSON.toJSONString(listA));
        System.out.println("listB " +JSON.toJSONString(listB));
        System.out.println("listC " +JSON.toJSONString(listC));
        System.out.println("listD " +JSON.toJSONString(listD));
        System.out.println("listE " +JSON.toJSONString(listE));
        System.out.println("arrayE " +JSON.toJSONString(arrayE));
        System.out.println("listF " +JSON.toJSONString(listF));
        System.out.println("=====================================================");
    }
}
