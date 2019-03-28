package com.kdniao.logisticsfront.common.biz.service.impl.regular;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoTest {
    public static void main(String[] args) {
        String tmp = "This order was placed for QT3000! OK?";
        System.out.println(tmp + "\n");

        String regEx = ".*s pla.*";
        boolean isMatch = Pattern.matches(regEx, tmp);
        System.out.println(isMatch + "\n");

        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(tmp);
        boolean rs = matcher.matches();
        System.out.println(rs + "\n");

        regEx = "(\\D*)(\\d+)(.*)";
        Pattern r = Pattern.compile(regEx);
        Matcher m = r.matcher(tmp);
        if (m.find()) {
            System.out.println("Found value: " + m.group(0));
            System.out.println("Found value: " + m.group(1));
            System.out.println("Found value: " + m.group(2));
            System.out.println("Found value: " + m.group(3) + "\n");
        }
    }
}
