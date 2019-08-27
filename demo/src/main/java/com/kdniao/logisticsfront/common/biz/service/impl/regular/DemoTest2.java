package com.kdniao.logisticsfront.common.biz.service.impl.regular;

import org.junit.Test;

import java.util.regex.Matcher;

/**
 * 正则表达式
 * regular expression
 * <p>
 * 正则表达式可以用来搜索、编辑或处理文本
 */
public class DemoTest2 {
    long start = 0L;
    long end = 0L;
    Matcher matcher;
    int size = 2000000;
    String tmp = "hello-world hello (abc ea)";
    
    @Test
    public void test9() {
        matcher = Print.match("[oa b]", tmp, true);
        Print.out(matcher, end - start);
        
        matcher = Print.match("[oa boooooo]", tmp, true);
        Print.out(matcher, end - start);
        
        matcher = Print.match("o[(r)(\\-)]", tmp, true);
        Print.out(matcher, end - start);
        
        matcher = Print.match("[^oa b]", tmp, true);
        Print.out(matcher, end - start);
        
        /*相邻的两个字符; 匹配的字符串需要包含 la或者lo或者ld或者ea或者eo或者ed*/
        matcher = Print.match("[le][aod]", tmp, true);
        Print.out(matcher, end - start);
        
        /*相邻的两个字符; 第一个不是l第二个是a或者o或者d*/
        matcher = Print.match("[^l][aod]", tmp, true);
        Print.out(matcher, end - start);
        
        /*和上一个同理*/
        matcher = Print.match("[heo word heo (abc)][aod]", tmp, true);
        Print.out(matcher, end - start);
        
        /*匹配范围*/
        matcher = Print.match("[a-e]", tmp, true);
        Print.out(matcher, end - start);
        
        matcher = Print.match("[^a-e]", tmp, true);
        Print.out(matcher, end - start);
        
        /*匹配范围或者匹配o*/
        matcher = Print.match("[a-eo]", tmp, true);
        Print.out(matcher, end - start);
    }
    
    @Test
    public void test10() {
        tmp = "hello world hello (abc (llo) lloo) o\tlloo ";
        /*匹配llo在右边界的字符串: 空格以及符号为边界*/
        matcher = Print.match("llo\\b", tmp, true);
        Print.out(matcher, end - start);
        
        /*匹配llo在左边界的字符串: 空格以及符号为边界*/
        matcher = Print.match("\\bllo", tmp, true);
        Print.out(matcher, end - start);
    }
    
    @Test
    public void test11() {
        tmp = "hello\t world\n hello\r (abc\f)";
        /*
         * \cx 匹配control + 控制字符
         *
         * \cI 匹配 control + I，等价于 \t，   c
         * \cJ匹配 control + J，等价于 \n,    换行
         * \cM匹配 control + M，等价于 \r     回车(不换行)
         * \cL匹配 controL + M，等价于 \f     换页符
         * \cK匹配 control + K，等价于 \v     垂直制表符
         *  */
        
        /*** 匹配\t ***/
        matcher = Print.match("\\cI", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\t", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\\t", tmp, true);
        Print.out(matcher, end - start);
    
        /*** 匹配\n ***/
        matcher = Print.match("\\cJ", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\n", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\\n", tmp, true);
        Print.out(matcher, end - start);
    
        /*** 匹配\r ***/
        matcher = Print.match("\\cM", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\r", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\\r", tmp, true);
        Print.out(matcher, end - start);
    
        /*** 匹配\f ***/
        matcher = Print.match("\\cL", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\f", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\\f", tmp, true);
        Print.out(matcher, end - start);
        
        /*** 匹配\v ***/
        matcher = Print.match("\\cK", tmp, true);
        Print.out(matcher, end - start);
        /*同理*/
        matcher = Print.match("\\v", tmp, true);
        Print.out(matcher, end - start);
    }
    
    @Test
    public void test12() {
        tmp = "hello\t world\n hello\r (abc\f)";
        
        /*** \s ***/
        matcher = Print.match("\\s", tmp, true);
        Print.out(matcher, end - start);
        
        /*** \S ***/
        matcher = Print.match("\\S", tmp, true);
        Print.out(matcher, end - start);
    }
    
    @Test
    public void test13() {
        tmp = "hel1lo wor2ld hel3lo 4(abc)5";
    
        matcher = Print.match("\\d", tmp, true);
        Print.out(matcher);
    
        matcher = Print.match("\\D", tmp, true);
        Print.out(matcher);
    }
    
    @Test
    public void test14() {
        tmp = "hello\t world\n hello\r abc\f ~`!@#$%^&*()_-+=\\|[]{};:'\",.<>?/)";
    
        matcher = Print.match("\\w", tmp, true);
        Print.out(matcher, end - start);
    
        matcher = Print.match("\\W", tmp, true);
        Print.out(matcher, end - start);
    }
    
    @Test
    public void test15() {
        tmp = "hello world hello aAbBcC";
        
        /*十六进制*/
        matcher = Print.match("\\x41", tmp, true);
        Print.out(matcher, end - start);
        
        matcher = Print.match("\\x42", tmp, true);
        Print.out(matcher, end - start);
        
        matcher = Print.match("\\x61", tmp, true);
        Print.out(matcher, end - start);
        
        matcher = Print.match("\\x62", tmp, true);
        Print.out(matcher, end - start);
    }
    
    @Test
    public void test16() {
        tmp = "hello world hello aAbBcC aaaaaabbbbbbcccccc";
        
        /*匹配两个连续的字符*/
        matcher = Print.match("(.)\\1", tmp, true);
        Print.out(matcher, end - start);
    
        matcher = Print.match("(bb)\\1", tmp, true);
        Print.out(matcher, end - start);
    
        matcher = Print.match("(aa)\\1", tmp, true);
        Print.out(matcher, end - start);
    }
}
