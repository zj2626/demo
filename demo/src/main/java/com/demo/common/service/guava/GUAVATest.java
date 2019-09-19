package com.demo.common.service.guava;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.List;

public class GUAVATest {
    public static void main(String args[]) {
        List<String> strings = Lists.newArrayList("a", "b", "c", "d", "d");
        String aa = "";
        String bb = null;
        String cc = "string";
        System.out.println("<" + aa + "****" + bb + ">");
        System.out.println("<" + Strings.emptyToNull(aa) + "****" + Strings.nullToEmpty(bb) + ">");
        System.out.println("<" + Strings.isNullOrEmpty(aa) + "****" + Strings.isNullOrEmpty(bb) + "****" + Strings.isNullOrEmpty(cc) + ">");

        System.out.println("<" + Strings.repeat(cc, 3) + ">");

        System.out.println("<" + Strings.padEnd(cc, 0, 'e') + ">");
        System.out.println("<" + Strings.padEnd(cc, 6, 'e') + ">");
        System.out.println("<" + Strings.padEnd(cc, 8, 'e') + ">");
        System.out.println("<" + Strings.padStart(cc, 8, 's') + ">");

        System.out.println(Strings.commonPrefix("abcvsafewfe", "abesafffe"));
        System.out.println(Strings.commonSuffix("abcvsafewfe", "abesafffe"));

        Joiner joiner = Joiner.on("-").skipNulls();
        String result = joiner.join(strings);
        System.out.println(result);

        Iterable<String> split = Splitter.on("-").omitEmptyStrings().trimResults().split(result);
        System.out.println(Lists.newArrayList(split));
    }
}
