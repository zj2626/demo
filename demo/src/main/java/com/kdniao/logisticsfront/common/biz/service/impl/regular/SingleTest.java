package com.kdniao.logisticsfront.common.biz.service.impl.regular;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleTest {
    
    @Test
    public void demoTest() {
        String tmp = "abc张123三efg";
        Pattern pattern = Pattern.compile("\\d");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
}
