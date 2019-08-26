package com.kdniao.logisticsfront.common.biz.service.impl.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Print {
    static Matcher match(String expression, String str, boolean ifPrint) {
        if (ifPrint) {
            Print.out(expression, str);
        }
        
        Pattern pattern = Pattern.compile(expression);
        return pattern.matcher(str);
    }
    
    public static long time() {
        return System.currentTimeMillis();
    }
    
    public static void out(String expression, String str) {
        System.out.printf("字符串:%30s\t长度: %3d\n", str, str.length());
        System.out.printf("匹配表达式:%20s\n", expression);
    }
    
    public static void out(Matcher matcher) {
        out(matcher, -1L);
    }
    
    public static void out(Matcher matcher, long time) {
        int num = 0;
        while (matcher.find()) {
            num++;
            System.out.printf("匹配到的第%2d个子字符串的位置是从%2d到%2d, 匹配的子字符串为:%5s",
                    num, matcher.start(), matcher.end(), matcher.group());
            System.out.print("|--\n");
        }
        System.out.printf("匹配成功次数:%-24s 执行时长:%d\n", num, time);
        System.out.println("############################################################\n");
    }
}