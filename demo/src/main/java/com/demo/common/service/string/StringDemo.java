package com.demo.common.service.string;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class StringDemo {

    @Test
    public void test() {
        String[] result = StringUtils.split("aa .b b. cc. d d ", ".");
        System.out.println(Arrays.toString(result));

        System.out.println(StringUtils.getFilename("D:/data/log/apis-fengdong-impl/apis-funfun-impl.txt"));

        System.out.println(StringUtils.getFilenameExtension("D:/data/log/apis-fengdong-impl/apis-funfun-impl.txt"));

        System.out.println(StringUtils.replace("aa.bb.cc.dd", ".", "|"));
        System.out.println("*****************");
        System.out.println(">" + Arrays.toString(StringUtils.trimArrayElements(result)) + "<");
        System.out.println(">" + StringUtils.trimWhitespace(" a a . b b . ") + "<");
        System.out.println(">" + StringUtils.trimAllWhitespace(" a a . b b . ") + "<");
        System.out.println(">" + StringUtils.trimLeadingWhitespace(" a a . b b . ") + "<");
        System.out.println(">" + StringUtils.trimTrailingWhitespace(" a a . b b . ") + "<");
        System.out.println(">" + StringUtils.trimLeadingCharacter(" a a . b b . ", '.') + "<");
        System.out.println(">" + StringUtils.trimTrailingCharacter(" a a . b b . ", '.') + "<");
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
}
