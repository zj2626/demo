package com.demo.common.service.regex;

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
    long start = 0L;
    long end = 0L;
    Matcher matcher;
    int size = 200;
    
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
        
        pattern = Pattern.compile("^abc*123abc*$");
        matcher = pattern.matcher(tmp);
        Print.out(matcher);
    }
    
    @Test
    public void test() {
        String tmp = "abcccc123abccc";
        matcher = Print.match("abc*", tmp, true);
        Print.out(matcher);
    }
    
    @Test
    public void test2() {
        String tmp = "abcccc123abccc";
        matcher = Print.match("abc+", tmp, true);
        Print.out(matcher);
    }
    
    @Test
    public void test3() {
        String tmp = "abcccc123abccc";
        matcher = Print.match("abc{3,5}", tmp, true);
        Print.out(matcher);
        
        tmp = "abcc123abccccccccc";
        matcher = Print.match("abc{3,5}", tmp, true);
        Print.out(matcher);
    }
    
    @Test
    public void test4() {
        String tmp = "hello\t world\n hello\r abc\f ~`!@#$%^&*()_-+=\\|[]{};:'\",.<>?/)";
        matcher = Print.match(".", tmp, true);
        Print.out(matcher);
    }
    
    @Test
    public void test5() {
        String tmp = "abcccc123abccc";
        
        matcher = Print.match("abc*", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("abc*?", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("abc+", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("abc+?", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("abc?", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("abc??", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("abc{2}", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("abc{2,5}", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("abc{2,5}?", tmp, true);
        Print.out(matcher);
    }
    
    @Test
    public void test6() {
//        String tmp = "abccmoba123abccfps123abccact";
        String tmp = "abccd1233abccd";
        
        matcher = Print.match("abc", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("(abc)", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("123|abc+", tmp, true);
        Print.out(matcher);
    
        matcher = Print.match("123+|abc+", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("(123|abc)+", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("(123)+|(abc)+", tmp, true);
        Print.out(matcher);
    }
    
    @Test
    public void test7() {
        String tmp = "abccmoba123abccfps123abccact";
        
        start = Print.time();
        for (int i = 0; i < size; i++) {
            matcher = Print.match("(abccmK|abccact|abccfps)", tmp, false);
        }
        end = Print.time();
        Print.out(matcher, end - start);
        
        start = Print.time();
        for (int i = 0; i < size; i++) {
            matcher = Print.match("abcc(mK|act|fps)", tmp, false);
        }
        end = Print.time();
        Print.out(matcher, end - start);
        
        // 更好
        start = Print.time();
        for (int i = 0; i < size; i++) {
            matcher = Print.match("abcc(?:mK|act|fps)", tmp, false);
        }
        end = Print.time();
        Print.out(matcher, end - start);
    }
    
    @Test
    public void test8() {    // TODO what is this ???
        String tmp = "abccc";
    
        matcher = Print.match("a(bc*)", tmp, true);
        Print.out(matcher);
    
        matcher = Print.match("a(?:bc*)", tmp, true);
        Print.out(matcher);
    
        System.out.println("###################");
        
        tmp = "abc acd aef gbc";
    
        matcher = Print.match("a(bc|cd)", tmp, true);
        Print.out(matcher);
        
        matcher = Print.match("a(?:bc|cd)", tmp, true);
        Print.out(matcher);
        
        // 前瞻
        matcher = Print.match("a(?=bc|cd)", tmp, true);
        Print.out(matcher);
    
        // 后顾
        matcher = Print.match("(?<=a)(bc|cd)", tmp, true);
        Print.out(matcher);
    
        // 负前瞻
        matcher = Print.match("a(?!bc|cd)", tmp, true);
        Print.out(matcher);
    
        // 负后顾
        matcher = Print.match("(?<!a)(bc|cd)", tmp, true);
        Print.out(matcher);
    }
}
