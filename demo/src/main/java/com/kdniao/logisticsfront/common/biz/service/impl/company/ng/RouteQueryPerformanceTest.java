package com.kdniao.logisticsfront.common.biz.service.impl.company.ng;

import com.kdniao.common.test.BaseTest;
import com.kdniao.logisticsgw.common.service.facade.route.RouteQueryRouteFacade;
import com.kdniao.logisticsgw.common.service.model.delivery.Waybill;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import com.kdniao.logisticsgw.common.service.order.RouteQueryOrder;
import com.kdniao.logisticsgw.common.service.result.RouteQueryResult;
import com.kdniao.merchant.common.service.model.setting.CustomerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class RouteQueryPerformanceTest extends BaseTest {
    @Autowired
    private RouteQueryRouteFacade routeQueryRouteFacade;
    private int count=0,js=0;
    private long tm = 0;
    
    @Test(threadPoolSize = 15, invocationCount = 10000, timeOut = 3600000)
    public void testMethod() {
        try{
        long l = System.currentTimeMillis();
        RouteQueryOrder order = new RouteQueryOrder();
        Waybill waybill = new Waybill();
        waybill.setWaybillKey(new WaybillKey("518293800829", "UC"));
        order.setWaybill(waybill);
        order.setPartnerId("1264635");
        order.setPartnerGroup(CustomerGroup.G998);
        RouteQueryResult result = this.routeQueryRouteFacade.route(order, false);
        System.out.println("耗时："+(System.currentTimeMillis()-l));
        }catch(Exception ex){
            count+=1;
            System.out.println(count);
        }
//        System.out.println(result.isSuccess());
//        if (!result.isSuccess()) {
//            System.out.println(result.getErrorContext().fetchCurrentError());
//        }
//        System.out.println(result.getRoute());
    }
//    @Test(threadPoolSize =10, invocationCount = 10)
    public void testTmMethod() {
        count+=1;
        System.out.println("#########################线程数："+count);
        long start = System.currentTimeMillis();
        while (true) {
            System.out.println("1");
            try{
                long l = System.currentTimeMillis();
                RouteQueryOrder order = new RouteQueryOrder();
                Waybill waybill = new Waybill();
                waybill.setWaybillKey(new WaybillKey("518293800829", "UC"));
                order.setWaybill(waybill);
                order.setPartnerId("1264635");
                order.setPartnerGroup(CustomerGroup.G998);
                RouteQueryResult result = this.routeQueryRouteFacade.route(order, false);
                System.out.println("耗时："+(System.currentTimeMillis()-l));
                }catch(Exception ex){
                    count+=1;
                    System.out.println("出错了："+count);
                }
            if ((System.currentTimeMillis() - start) > 3000 * 100) {
                break;
            }
        }
        
    }
}
