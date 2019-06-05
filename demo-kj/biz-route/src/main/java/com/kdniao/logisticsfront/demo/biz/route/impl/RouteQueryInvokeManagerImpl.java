/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package com.kdniao.logisticsfront.demo.biz.route.impl;

import com.kdniao.demo.common.util.Print;
import com.kdniao.logisticsfront.biz.shared.route.impl.AbstractRouteQueryManagerImpl;
import com.kdniao.logisticsfront.common.util.http.ExterfaceInvokeHttpSender;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import com.kdniao.logisticsgw.common.service.model.enums.AcceptStatus;
import com.kdniao.logisticsgw.common.service.model.route.Route;
import com.kdniao.logisticsgw.common.service.model.route.Track;
import com.kdniao.logisticsgw.common.service.order.RouteQueryInvokeOrder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author zhangj
 * @version $Id: RouteQueryInvokeManagerImpl.java, v 0.1 2018年10月23日 下午1:46:44 zhangj Exp $
 */
public class RouteQueryInvokeManagerImpl extends AbstractRouteQueryManagerImpl {
    private ExterfaceInvokeHttpSender routeQueryHttpSender;
    private static final String dateFormats = "yyyy-MM-dd HH:mm:ss";

    public void setRouteQueryHttpSender(ExterfaceInvokeHttpSender routeQueryHttpSender) {
        this.routeQueryHttpSender = routeQueryHttpSender;
    }

    public static void main(String[] args) throws Exception {
        RouteQueryInvokeManagerImpl invoker = new RouteQueryInvokeManagerImpl();
        WaybillKey waybillKey = new WaybillKey("123456789", "DEMO");
        RouteQueryInvokeOrder order = new RouteQueryInvokeOrder();
        order.setWaybillKey(waybillKey);
        Route route = invoker.doInvoke(order);
        Print.out(route.toString(), null);
    }

    @Override
    protected Route doInvoke(RouteQueryInvokeOrder order) {
        Print.out("轨迹查询demo ", order.getWaybillKey().getWaybillCode());

        WaybillKey waybillKey = order.getWaybillKey();
        Route route = new Route();
        route.setWaybillKey(waybillKey);

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        long start = System.currentTimeMillis();

        Map<String, String> map = new HashMap();
        map.put("code", waybillKey.getWaybillCode());

        List<Track> trackList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Track track = new Track();
            track.setAcceptTime(new Date());
            String info = "这是轨迹记录；test";
            track.setAcceptStation(info);
            track.setLocation("深圳市");
            track.setAcceptStatus(AcceptStatus.S3);
            trackList.add(track);
        }

        long end = System.currentTimeMillis();

        route.setTrackList(trackList);
        return route;
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
            Print.out("判断day2 - day1 : ", (day2 - day1));
            return day2 - day1;
        }
    }

    private static Date stringDateFormat(String strDate, String format, Locale locale) {
        if (org.apache.commons.lang.StringUtils.isEmpty(strDate)) {
            return null;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
            try {
                Date result = sdf.parse(strDate);
                Calendar c = Calendar.getInstance();
                c.setTime(result);
                c.add(Calendar.HOUR_OF_DAY, 0);// 今天+0

                return c.getTime();
            } catch (ParseException var4) {
                var4.printStackTrace();
            }
        }
        return null;
    }
}
