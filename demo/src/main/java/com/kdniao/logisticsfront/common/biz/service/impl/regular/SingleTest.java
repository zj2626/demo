package com.kdniao.logisticsfront.common.biz.service.impl.regular;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SingleTest {
    
    /*验证包含中文*/
    @Test
    public void demoTest() {
        String tmp = "1张）1";
        Pattern pattern = Pattern.compile("^[\\u4e00-\\u9fa5]|[（）《》—；，。“”<>！]+$");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    /*两个连续相同的字符正则*/
    @Test
    public void same() {
        String tmp = "112333455677";
        Pattern pattern = Pattern.compile("(.)\\1");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
}
