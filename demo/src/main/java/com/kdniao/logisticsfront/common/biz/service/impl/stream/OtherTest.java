package com.kdniao.logisticsfront.common.biz.service.impl.stream;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OtherTest {
    public static void main(String args[]) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            System.out.println(format.parse("9999-01-01 00:00:00"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String a = "0123456789";

        System.out.println(a.substring(5));
        System.out.println(a.substring(0, 5));
        System.out.println(a.substring(3, 5));
        System.out.println(a.substring(0, a.length() - 1));
        // System.out.println(a.substring(15));
        System.out.println("************************");

        List<String> list = new ArrayList<>();
        list.add("aadwHDSH");
        list.add("aadwHDSH");
        list.add("Kdf48ds5");
        list.add("fgje3@23JFR");
        list.add("4f8ew4");
        list.add("ZJF#@*FH");
        list.add("fdgj3");
        list.add("DGD4823");
        list.add("4f8w2");
        list.add("hhhhhHHHHHH");
        list.add("zdDdDdDd");

//        Collections.sort(list, (o1, o2) -> o1.compareTo(o2));
//        System.out.println("#######################");

        list = list.stream().map((String::intern)).collect(Collectors.toList());
        list.forEach(System.out::println);
        System.out.println("#######################");

        list = list.stream().map(String::toLowerCase).collect(Collectors.toList());
        list.forEach(System.out::println);


        String[] array = {"a", "b", "c"};
        for(Integer i : Lists.newArrayList(1,2,3)){
            Stream.of(array).map(item -> Strings.padEnd(item, i, '@')).forEach(System.out::println);
        }


        String[] array2 = {"a", "b", "c"};
        for(int i = 1; i<4; i++){
            int finalI = i;
            Stream.of(array).map(item -> Strings.padEnd(item, finalI, '@')).forEach(System.out::println);
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
        Function<Integer, String[]> fun = String[]::new;
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun1 = String[]::new;
        strs = fun1.apply(20);
        System.out.println(strs.length);
    }
}
