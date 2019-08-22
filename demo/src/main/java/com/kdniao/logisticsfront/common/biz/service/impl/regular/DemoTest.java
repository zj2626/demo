package com.kdniao.logisticsfront.common.biz.service.impl.regular;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 * regular expression
 * <p>
 * 正则表达式可以用来搜索、编辑或处理文本
 */
public class DemoTest {
    @Test
    public void demo() {
        String tmp = "abcccc123abccc";
        // 查询字符串中是否包含"abc"
        Pattern pattern = Pattern.compile(".*abc.*");
        Matcher matcher = pattern.matcher(tmp);
        System.out.println(matcher.matches());
    }
    
    @Test
    public void begin() {
        String tmp = "abcccc123abccc";
        
        Pattern pattern = Pattern.compile("^abc");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        pattern = Pattern.compile("bccc$");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    @Test
    public void test() {
        String tmp = "abcccc123abccc";
        Pattern pattern = Pattern.compile("abc*");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    @Test
    public void test2() {
        String tmp = "abcccc123abccc";
        Pattern pattern = Pattern.compile("abc+");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    @Test
    public void test3() {
        Pattern pattern = Pattern.compile("abc{3,5}");
        
        String tmp = "abcccc123abccc";
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
        
        tmp = "abcc123abccccccccc";
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    @Test
    public void test4() {
        Pattern pattern = Pattern.compile(".");
        
        String tmp = "a.1\r`\n-]=e\tf";
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    @Test
    public void test5() {
        String tmp = "abcccc123abccc";
    
        Print.out("*");
        Pattern pattern = Pattern.compile("abc*");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("*?");
        pattern = Pattern.compile("abc*?");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("+");
        pattern = Pattern.compile("abc+");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("+?");
        pattern = Pattern.compile("abc+?");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("?");
        pattern = Pattern.compile("abc?");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("??");
        pattern = Pattern.compile("abc??");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("{2,5}");
        pattern = Pattern.compile("abc{2,5}");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("{2,5}?");
        pattern = Pattern.compile("abc{2,5}?");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    @Test
    public void test6() {
        String tmp = "abcccmoba123abcfps";
    
        Print.out("(abcc)");
        Pattern pattern = Pattern.compile("(abcc)");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("(abc|abcc)");
        pattern = Pattern.compile("(abc|abcc)");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    @Test
    public void test7() {
        String tmp = "abccmoba123abccfps";
    
        Print.out("(abccmoba|abccfps)");
        Pattern pattern = Pattern.compile("(abccmoba|abccfps)");
        Matcher matcher = pattern.matcher(tmp);
        Print.out(matcher);
    
        Print.out("abcc(moba|fps)");
        pattern = Pattern.compile("abcc(moba|fps)");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
        
        // 更好
        Print.out("abcc(?:moba|fps)");
        pattern = Pattern.compile("abcc(?:moba|fps)");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    private static class Print {
        public static void out(String expression) {
            System.err.printf("匹配表达式:%5s\n", expression);
        }
        
        public static void out(Matcher matcher) {
            int num = 0;
            while (matcher.find()) {
                num++;
                System.out.printf("匹配到的第%d个子字符串的位置是从%2d到%2d, 匹配的子字符串为:%-5s\n",
                        num, matcher.start(), matcher.end(), matcher.group());
            }
            System.out.printf("匹配成功次数:%-2s\n\n", num);
        }
    }
    
}
