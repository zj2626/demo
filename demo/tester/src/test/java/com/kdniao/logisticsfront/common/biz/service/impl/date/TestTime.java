package com.kdniao.logisticsfront.common.biz.service.impl.logisticsfront.common.biz.service.impl.date;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestTime {
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
}
