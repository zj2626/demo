package com.kdniao.logisticsfront.common.biz.service.impl.date;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TestJavaTime {
    private static final String dtf = "yyyy-MM-dd";
    private static final String ttf = "HH:mm:ss";
    private static final String stf = "yyyy-MM-dd HH:mm:ss";
    private static final String dateStr = "2019-01-21";
    private static final String timeStr = "11:11:11";
    private static final String dateTimeStr = "2019-01-21 11:11:11";
    private static final String datetTimeStr = "2019-07-01T11:22:33";
    
    @Test
    public void testTime() {
        System.out.println(TimeUnit.DAYS.toHours(3));
        System.out.println(TimeUnit.HOURS.toSeconds(1));
        System.out.println(TimeUnit.MINUTES.convert(2, TimeUnit.DAYS));
        System.out.println(TimeUnit.DAYS.toMinutes(2));
        System.out.println(TimeUnit.MINUTES.toHours(120));
        System.out.println("##############################");
        System.out.println(TimeUnit.MINUTES.toNanos(1));
        System.out.println(TimeUnit.SECONDS.toNanos(1));
        System.out.println(TimeUnit.MILLISECONDS.toNanos(1));
        System.out.println(TimeUnit.MICROSECONDS.toNanos(1));
        System.out.println(TimeUnit.NANOSECONDS.toNanos(1));
    }
    
    // #################################################
    //          下面都是线程安全的 best !!!!!!
    // #################################################
    
    /**
     * 当前时间
     */
    @Test
    public void testNow() {
        // LocalDate 当天
        LocalDate nowDate = LocalDate.now();
        System.out.println(nowDate);
        System.out.println(nowDate.getYear());
        System.out.println(nowDate.getMonthValue());
        System.out.println(nowDate.getDayOfYear());
        System.out.println(nowDate.getDayOfMonth());
        System.out.println(nowDate.getDayOfWeek().getValue());
        System.out.println();
        
        // LocalTime 当小时
        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime);
        System.out.println(nowTime.getHour());
        System.out.println(nowTime.getMinute());
        System.out.println(nowTime.getSecond());
        System.out.println();
        
        // LocalDateTime 当天完整时间
        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println(nowDateTime);
        
        System.out.println("\n##################");
        LocalDate znow = LocalDate.now(ZoneId.systemDefault());
        System.out.println(znow);
        System.out.println();
    }
    
    /**
     * 指定时间
     */
    @Test
    public void testPointTime() {
        // 2019年的第210天
        LocalDate date2 = LocalDate.ofYearDay(2019, 210);
        System.out.println(date2);
        
        // 中午
        LocalTime noon = LocalTime.NOON;
        System.out.println("NOON: " + noon);
        
        // 某天的第1234秒
        LocalTime time = LocalTime.ofSecondOfDay(1234);
        System.out.println(time);
        
        // 2019年5月12日
        LocalDate date = LocalDate.of(2019, 5, 12);
        System.out.println(date);
        
        // 2019年6月......
        LocalDateTime dateTime2 = LocalDateTime.of(2019, 6, 30, 14, 56, 12);
        System.out.println(dateTime2);
        
        // LocalDate 和 LocalTime 拼接成的 LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(date2, time);
        System.out.println(dateTime);
    }
    
    /**
     * 时间各种转化
     */
    @Test
    public void testTransform() {
        System.out.println("\n字符串 转化为 LocalDate");
        LocalDate date = LocalDate.parse(dateStr);
        System.out.println(date);
        
        System.out.println("\n字符串 转化为 LocalTime");
        LocalTime time2 = LocalTime.parse(timeStr);
        System.out.println(time2);
        
        System.out.println("\n字符串 转化为 LocalDateTime");
        LocalDateTime dateTime3 = LocalDateTime.parse(datetTimeStr);
        System.out.println(dateTime3);
        
        System.out.println("\nLocalDateTime 转化为 字符串");
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern(stf);
        String dateTimeStr1 = dtf1.format(dateTime3);
        System.out.println(dateTimeStr1);
        
        System.out.println("\nLocalDateTime 转化为 字符串");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(ttf);
        String dateTimeStr2 = dtf2.format(dateTime3);
        System.out.println(dateTimeStr2);
        
        System.out.println("\nLocalDateTime 转化为 字符串");
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern(dtf);
        String dateTimeStr3 = dtf3.format(dateTime3);
        System.out.println(dateTimeStr3);
        
        System.out.println("\n#####################");
        Date utilDate = new Date();
        
        System.out.println("\njava.util.Date 转化为 LocalDateTime");
        ZoneId zoneId = ZoneId.systemDefault(); // 默认时区
        Instant instant = utilDate.toInstant();
        System.out.println("UtilDate: " + utilDate);
        // 方法一
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        System.out.println("LocalDateTime: " + localDateTime);
        // 方法二
        LocalDateTime localDateTime2 = LocalDateTime.ofInstant(utilDate.toInstant(), zoneId);
        System.out.println("LocalDateTime: " + localDateTime2);
        
        System.out.println("\nLocalDateTime 转化为 java.util.Date");
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Date utilDate2 = Date.from(zonedDateTime.toInstant());
        System.out.println("LocalDateTime: " + localDateTime);
        System.out.println("ZonedDateTime: " + zonedDateTime);
        System.out.println("UtilDate: " + utilDate2);
    }
    
    /**
     * 时间的增减变化操作
     */
    @Test
    public void testChange() {
        LocalDateTime now = LocalDateTime.now();
        
        LocalDateTime dateTime = LocalDateTime.parse(datetTimeStr);
        System.out.println(now.isEqual(dateTime));
        System.out.println(now.isAfter(dateTime));
        System.out.println(now.isBefore(dateTime));
        // 先比较月 月相同比较天
        System.out.println(now.compareTo(dateTime) + "\n");
    
        System.out.println("当前时间:" + now);
        System.out.println("对比时间:" + dateTime);
        // 前n个年代(n*10年)
        System.out.println("\t\t" + now.minus(1, ChronoUnit.DECADES));
        // 前n个月
        System.out.println("\t\t" + now.minus(1, ChronoUnit.MONTHS));
        // 前n个天
        System.out.println("\t\t" + now.minus(1, ChronoUnit.DAYS));
        // 前n个秒
        System.out.println("\t\t" + now.minus(1, ChronoUnit.SECONDS));
        // 后n个分
        System.out.println("\t\t" + now.plus(1, ChronoUnit.MINUTES));
        // 后n个周
        System.out.println("\t\t" + now.plus(1, ChronoUnit.WEEKS));
        // 后n个年
        System.out.println("\t\t" + now.plus(1, ChronoUnit.YEARS));
        // 其他时间增减, 一看就知道啥意义
        System.out.println("\t\t" + now.minusHours(1));
        System.out.println("\t\t" + now.plusMonths(1));
        System.out.println("\n当前时间:" + now);
        // 替换时间 替换参数包含的部分, 参数为LocalDateTime则全部替换, LocalDate则替换年月日, LocalTime则替换时分秒
        System.out.println("\t\t" + now.with(dateTime));
        // 时间所在月的第1天
        System.out.println("\t\t" + now.withDayOfMonth(1));
        // 时间所在天的第1小时
        System.out.println("\t\t" + now.withHour(1));
        // 时间所在天的第1分钟
        System.out.println("\t\t" + now.withMinute(1));
        // 时间所在天的第1秒
        System.out.println("\t\t" + now.withSecond(1));
        // 时间所在月最后一天
        int maxDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("\t\t" + now.withDayOfMonth(maxDay));
    }
}
