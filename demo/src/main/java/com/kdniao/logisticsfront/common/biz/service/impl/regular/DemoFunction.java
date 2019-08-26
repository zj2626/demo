package com.kdniao.logisticsfront.common.biz.service.impl.regular;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoFunction {
    long start = 0L;
    long end = 0L;
    Matcher matcher;
    int size = 2000000;
    String tmp = "hello world hello (abc ea)";
    
    @Test
    public void match() {
        /*包含匹配*/
        tmp = "hel1lo wor2ld hel3lo 4(abc)5";
        matcher = Print.match("hel1lo wor2ld", tmp, true);
        Print.out(matcher);
    }
    
    @Test
    public void lookingAt() {
        /*包含匹配*/
        tmp = "hel1lo wor2ld hel3lo 4(abc)5";
        System.out.println(Print.lookingAt("hel1lo wor2ld", tmp, true));
    }
    
    @Test
    public void matches() {
        /*全部匹配 整个序列都匹配*/
        tmp = "hel1lo wor2ld hel3lo 4(abc)5";
        
        System.out.println(Print.matches("hel1lo wor2ld", tmp, true));
        System.out.println(Print.matches("hel1lo wor2ld hel3lo 4\\(abc\\)5", tmp, true));
        System.out.println(Print.matches("hel1lo\\ wor2ld\\ hel3lo\\ 4\\(abc\\)5", tmp, true));
    }
    
    @Test
    public void replaceAll() {
        /*匹配替换全部*/
        tmp = "hel1lo wor2ld hel3lo 4(abc)5";
        matcher = Print.match("\\d", tmp, true);
        Print.out(matcher);
        String result = matcher.replaceAll("X");
        System.out.println(result);
    }
    
    @Test
    public void replaceFirst() {
        /*匹配替换第一个*/
        tmp = "hel1lo wor2ld hel3lo 4(abc)5";
        matcher = Print.match("\\d", tmp, true);
        Print.out(matcher);
        String result = matcher.replaceFirst("X");
        System.out.println(result);
    }
    
    @Test
    public void appendReplacement() {
    
    }
    
    @Test
    public void appendTail() {
    
    }
    
    /**
     * replacement转义
     * <p>
     * \和$是replacement中的特殊字符，前者用来转义，后者用来匹配组并使用$n来引用第n个组
     * <p>
     * 所以需要使用quoteReplacement对替换的文字进行转义( 而正则表达式的转义使用\ )
     */
    @Test
    public void quoteReplacement() {
        String REGEX = "dog";
        String INPUT = "The dog says meow All $dogs say meow.";
        String REPLACE = "$cat";
        REPLACE = "cat\\";
        
        matcher = Print.match(REGEX, INPUT, true);
        Print.out(matcher);
        String result = matcher.replaceAll(Matcher.quoteReplacement(REPLACE));
        System.out.println(result);
    }
    
    /**
     * regex转义
     * <p>
     * 和replacement类似的，如果regex中有字符需要转义，也可主动加\来转义
     * 在确定regex中不包含正则表达式的时候，可以使用Pattern.quote()来转义
     */
    @Test
    public void quoteReplacement2() {
        String REGEX = "${dog}";
        // String REGEX = "\\$\\{dog}"; // 可以改成这样就不需要Pattern.quote()方法
        String INPUT = "The ${dog} says meow All $dogs say meow.";
        String REPLACE = "cat";
        
        matcher = Print.match(Pattern.quote(REGEX), INPUT, true);
        Print.out(matcher);
        String result = matcher.replaceAll(REPLACE);
        System.out.println(result);
    }
}
