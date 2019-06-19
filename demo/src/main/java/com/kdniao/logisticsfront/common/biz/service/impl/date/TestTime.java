package com.kdniao.logisticsfront.common.biz.service.impl.date;

import org.joda.time.DateTime;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TestTime {
    private static final String dtf = "yyyy-MM-dd";
    private static final String ttf = "HH:mm:ss";
    private static final String stf = "yyyy-MM-dd HH:mm:ss";
    private static final String dateStr = "2019-01-21";
    private static final String timeStr = "11:11:11";
    private static final String dateTimeStr = "2019-01-21 11:11:11";
    private static final String datetTimeStr = "2019-01-21T11:11:11";

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

    @Test
    public void testTime2() {
        DateTime today = new DateTime();
        String dtf_date = today.toString(stf);
        System.out.println(dtf_date);
    }

    // 下面都是线程安全的 best !!!!!!
    @Test
    public void testTime3() {
        LocalDate nowDate = LocalDate.now();
        System.out.println(nowDate);
        System.out.println(nowDate.getYear());
        System.out.println(nowDate.getMonthValue());
        System.out.println(nowDate.getDayOfYear());
        System.out.println(nowDate.getDayOfMonth());
        System.out.println(nowDate.getDayOfWeek().getValue());
        System.out.println();

        LocalDate znow = LocalDate.now(ZoneId.systemDefault());
        System.out.println(znow);
        System.out.println();

        LocalDate date = LocalDate.of(2019, 5, 12);
        System.out.println(date);
        LocalDate date2 = LocalDate.ofYearDay(2019, 210);
        System.out.println(date2);
        // ##############
        LocalDate date3 = LocalDate.parse(dateStr);
        System.out.println(date3);
        System.out.println();
        System.out.println();

        LocalTime noon = LocalTime.NOON;
        System.out.println(noon);
        LocalTime nowTime = LocalTime.now();
        System.out.println(nowTime);
        System.out.println(nowTime.getHour());
        System.out.println(nowTime.getMinute());
        System.out.println(nowTime.getSecond());
        System.out.println();

        LocalTime time = LocalTime.ofSecondOfDay(1234);
        System.out.println(time);
        // ##############
        LocalTime time2 = LocalTime.parse(timeStr);
        System.out.println(time2);
        System.out.println();
        System.out.println();

        LocalDateTime nowDateTime = LocalDateTime.now();
        System.out.println(nowDateTime);
        LocalDateTime dateTime = LocalDateTime.of(nowDate, nowTime);
        System.out.println(dateTime);
        LocalDateTime dateTime2 = LocalDateTime.of(2019, 6, 30, 12, 12, 12);
        System.out.println(dateTime2);
        System.out.println();
        // ##############
        LocalDateTime dateTime3 = LocalDateTime.parse(datetTimeStr);
        System.out.println(dateTime3);
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern(stf);
        String dateTimeStr1 = dtf1.format(dateTime3);
        System.out.println(dateTimeStr1);
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(ttf);
        String dateTimeStr2 = dtf2.format(dateTime3);
        System.out.println(dateTimeStr2);
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern(dtf);
        String dateTimeStr3 = dtf3.format(dateTime3);
        System.out.println(dateTimeStr3);
        DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern(stf);
        String dateTimeStr4 = dtf4.format(LocalDateTime.parse(datetTimeStr));
        System.out.println(dateTimeStr4);
    }
}
