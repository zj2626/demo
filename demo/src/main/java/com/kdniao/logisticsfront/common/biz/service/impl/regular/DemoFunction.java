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
        System.out.println(matcher.replaceFirst("XXX"));
    }
    
    @Test
    public void appendReplacement() {
        tmp = "hel1lo wor2ld hel3lo 4(abc)5";
        matcher = Print.match("\\d", tmp, true);
        int num = 0;
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            num++;
            matcher.appendReplacement(sb, "XXX" + num);
        }
        matcher.appendTail(sb);
        System.out.println(num);
        System.out.println(tmp);
        System.out.println(sb);
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
    
    /*判断是否json字符串*/
    @Test
    public void testIfJson() {
        String json = " { \"orderMasterData\" : [ { \"consumeOrderNum\" : \"#B984\" , \"id\" : \"mbkoevospbsjv\"," +
                "\"kitchenCoId\":\"m6olhg2putpo8\"," +
                "\"kitchenCoName\":\"麦当劳1号厨房公司\",\"kitchenId\":\"m6ocah27t14dk\",\"kitchenName\":\"麦当劳厨房1号\"," +
                "\"merchantId\":\"m6na43n12ft37\",\"merchantName\":\"义泰昌\",\"orderStatus\":\"waitProduce\",\"receiveTime\":1566476698000," +
                "\"storeId\":\"madjb30qnu6nf\",\"storeName\":\"麦当劳车公庙1号店\",\"skuId\":\"mcrjuplspqdou\",\"skuName\":\"肥高套餐620adadsa\"," +
                "\"orderId\":\"mbblo1beqb3ti\",\"createTime\":1566476698000,\"createUser\":\"system\",\"updateTime\":1566476698000," +
                "\"updateUser\":\"system\",\"orderNo\":\"1908220000202956\",\"planDeliverTime\":1566468544000," +
                "\"locatedAddrId\":\"m0dtva277g11g\",\"produceOrderNum\":\"#B984-1\" } ] , \"storeName\": [ \"海松大厦A座404\" , " +
                "\"海松大厦A座404\"," +
                "\"海松大厦A座404\"] , \"orderMaster\" :  { \"id\":\"mbblo1beqb3ti\",\"actualAmount\":0.80,\"address\":\"海松大厦A座404\"," +
                "\"addressMask\":\"海松大厦A座**4\",\"amount\":1.00,\"createTime\":1566467644000,\"createUser\":\"m8sq6okjeu7eq\"," +
                "\"deliverFee\":0.00,\"deliverType\":0,\"discountAmount\":0.20,\"isPaid\":0,\"kitchenCoId\":\"m6olhg2putpo8\"," +
                "\"kitchenCoName\":\"麦当劳1号厨房公司\",\"kitchenId\":\"m6ocah27t14dk\",\"kitchenName\":\"麦当劳厨房1号\",\"linkMan\":\"肥高\"," +
                "\"linkManMask\":\"肥*\",\"linkPhone\":\"15361663472\",\"linkPhoneMask\":\"153****3472\",\"linkSex\":2," +
                "\"merchantId\":\"m6na43n12ft37\",\"merchantName\":\"义泰昌\",\"orderChannel\":\"app\",\"orderNo\":\"1908220000202956\"," +
                "\"orderRemrak\":\"\",\"orderStatus\":\"cancel\",\"packingFee\":0.00,\"planDeliverTime\":1566468544000," +
                "\"refundStatus\":\"notRefund\",\"storeAddress\":\"福田车公庙1102\",\"storeId\":\"madjb30qnu6nf\",\"storeName\":\"麦当劳车公庙1号店\"," +
                "\"storePhone\":\"13423432342\",\"updateTime\":1566467885000,\"updateUser\":\"system\",\"userId\":\"m8sq6okjeu7eq\"," +
                "\"userName\":\"m8sq6okjeu7eq\",\"locatedAddrId\":\"m0dtva277g11g\" } } ";
        json = "[\"aaa\",\"bbb\"]";
        json = "[\"{ab}c\",\"def\"]";
        json = "[123,456]";
        json = "[ {\"consumeOrderNum\":\"#B984\"}]";
        
        // 判断是否json字符串
//        String expression = "[\\[{] *?\".* *?[}:\\]]";
        // 判断是否数组
//        String expression = "\\[ *?\".* *?]";
        String expression = "\\[ *?(?!\\{).* *?]";
        
        System.out.println(Pattern.matches(expression, json.trim()));
    }
    
    /**
     * 替换json字符串的所有key
     */
    @Test
    public void test() {
        String json = " { \"orderMasterData\" : [ { \"consumeOrderNum\" : \"#B984\" , \"id\" : \"mbkoevospbsjv\"," +
                "\"kitchenCoId\":\"m6olhg2putpo8\"," +
                "\"kitchenCoName\":\"麦当劳1号厨房公司\",\"kitchenId\":\"m6ocah27t14dk\",\"kitchenName\":\"麦当劳厨房1号\"," +
                "\"merchantId\":\"m6na43n12ft37\",\"merchantName\":\"义泰昌\",\"orderStatus\":\"waitProduce\",\"receiveTime\":1566476698000," +
                "\"storeId\":\"madjb30qnu6nf\",\"storeName\":\"麦当劳车公庙1号店\",\"skuId\":\"mcrjuplspqdou\",\"skuName\":\"肥高套餐620adadsa\"," +
                "\"orderId\":\"mbblo1beqb3ti\",\"createTime\":1566476698000,\"createUser\":\"system\",\"updateTime\":1566476698000," +
                "\"updateUser\":\"system\",\"orderNo\":\"1908220000202956\",\"planDeliverTime\":1566468544000," +
                "\"locatedAddrId\":\"m0dtva277g11g\",\"produceOrderNum\":\"#B984-1\" } ] , \"storeName\": [ \"海松大厦A座404\" , " +
                "\"海松大厦A座404\"," +
                "\"海松大厦A座404\"] , \"orderMaster\" :  { \"id\":\"mbblo1beqb3ti\",\"actualAmount\":0.80,\"address\":\"海松大厦A座404\"," +
                "\"addressMask\":\"海松大厦A座**4\",\"amount\":1.00,\"createTime\":1566467644000,\"createUser\":\"m8sq6okjeu7eq\"," +
                "\"deliverFee\":0.00,\"deliverType\":0,\"discountAmount\":0.20,\"isPaid\":0,\"kitchenCoId\":\"m6olhg2putpo8\"," +
                "\"kitchenCoName\":\"麦当劳1号厨房公司\",\"kitchenId\":\"m6ocah27t14dk\",\"kitchenName\":\"麦当劳厨房1号\",\"linkMan\":\"肥高\"," +
                "\"linkManMask\":\"肥*\",\"linkPhone\":\"15361663472\",\"linkPhoneMask\":\"153****3472\",\"linkSex\":2," +
                "\"merchantId\":\"m6na43n12ft37\",\"merchantName\":\"义泰昌\",\"orderChannel\":\"app\",\"orderNo\":\"1908220000202956\"," +
                "\"orderRemrak\":\"\",\"orderStatus\":\"cancel\",\"packingFee\":0.00,\"planDeliverTime\":1566468544000," +
                "\"refundStatus\":\"notRefund\",\"storeAddress\":\"福田车公庙1102\",\"storeId\":\"madjb30qnu6nf\",\"storeName\":\"麦当劳车公庙1号店\"," +
                "\"storePhone\":\"13423432342\",\"updateTime\":1566467885000,\"updateUser\":\"system\",\"userId\":\"m8sq6okjeu7eq\"," +
                "\"userName\":\"m8sq6okjeu7eq\",\"locatedAddrId\":\"m0dtva277g11g\" } } ";
        json = "[\"aaa\",\"bbb\"]";
        
        matcher = Print.match("\".*?\" *[:,}\\]]", json, false);
        StringBuffer sb = new StringBuffer();
        int count = 0;
        while (matcher.find()) {
            count++;
            System.out.printf("匹配到的第%2d个子字符串的位置是从%2d到%2d, 匹配的子字符串为:%5s\n",
                    count, matcher.start(), matcher.end(), matcher.group());
            String key = json.substring(matcher.start(), matcher.end());
            if (key.contains(":")) {
                /* 要替换成的字符串*/
                String replacement = key.replaceAll("\"", "").replace(":", "");
                
                String matchingStr = "\"" + replacement.trim() + "\":";
                matcher.appendReplacement(sb, matchingStr);
            }
        }
        matcher.appendTail(sb);
        
        System.out.println("\n替换前字符串: \n" + json);
        System.out.println("匹配成功次数: " + count);
        ;
        System.out.println("替换后字符串: \n" + sb.toString());
    }
    
    /*两个连续相同的字符正则*/
    @Test
    public void same() {
        /*全部匹配 整个序列都匹配*/
        tmp = "hel1lo  wwwor2ld  heel3lo  4(abc)5";
        String regx = "^*(.)\\1{1}";
//        String regx = "^*( )\\1{1}";
        
        StringBuffer sb = new StringBuffer();
        Matcher matcher = Print.match(regx, tmp, false);
        while (matcher.find()) {
            System.out.println("###");
            String key = tmp.substring(matcher.start(), matcher.end());
            System.out.println(key);
            matcher.appendReplacement(sb, key + "*");
        }
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }
}
