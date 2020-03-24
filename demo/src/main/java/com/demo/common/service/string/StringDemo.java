package com.demo.common.service.string;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class StringDemo {
    String[] result = StringUtils.split("aa .b b. cc. d d ", ".");

    @Test
    public void test() {
        System.out.println(Arrays.toString(result));

        System.out.println(StringUtils.getFilename("D:/data/log/apis-fengdong-impl/apis-funfun-impl.txt"));

        System.out.println(StringUtils.getFilenameExtension("D:/data/log/apis-fengdong-impl/apis-funfun-impl.txt"));

        System.out.println(StringUtils.replace("aa.bb.cc.dd", ".", "|"));

        System.out.println("************************************************");
        System.out.println(StringUtils.isEmpty(null));
        System.out.println(StringUtils.isEmpty(""));
        System.out.println(StringUtils.isEmpty(" "));
        System.out.println("*****************");
        System.out.println(StringUtils.hasLength(null));
        System.out.println(StringUtils.hasLength(""));
        System.out.println(StringUtils.hasLength(" "));
        System.out.println("*****************");
        System.out.println(StringUtils.hasText(null));
        System.out.println(StringUtils.hasText(""));
        System.out.println(StringUtils.hasText(" "));
        System.out.println("*****************");
    }

    @Test
    public void test2() {
        System.out.println(">" + Arrays.toString(StringUtils.trimArrayElements(result)) + "<");
        // 去除两边空格
        System.out.println(">" + StringUtils.trimWhitespace(" a a . b b . ") + "<");
        // 去除所有空格
        System.out.println(">" + StringUtils.trimAllWhitespace(" a a . b b . ") + "<");
        System.out.println("*****************");
        System.out.println(">" + StringUtils.trimLeadingWhitespace(" a a . b b . ") + "<");
        System.out.println(">" + StringUtils.trimTrailingWhitespace(" a a . b b . ") + "<");
        System.out.println("*****************");
        System.out.println(">" + StringUtils.trimLeadingCharacter(" a a . b b . ", '.') + "<");
        System.out.println(">" + StringUtils.trimTrailingCharacter(" a a . b b . ", '.') + "<");
    }

    @Test
    public void test3() {
        System.out.println(Arrays.toString(result));
        // 数组使用指定字符串连接
        String anyAuthorities = StringUtils.arrayToDelimitedString(result, "ROLE_");
        System.out.println(anyAuthorities);
    }

}
