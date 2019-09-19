package com.kdniao.logisticsfront.common.biz.service.impl.basic;

import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SubstringDemo {
    @Test
    public void testSubstring() {
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
        System.out.println(a.substring(0, a.length()));
        // System.out.println(a.substring(15));
        System.out.println("************************");
    }
}
