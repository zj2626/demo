package com.kdniao.logisticsfront.common.biz.service.impl.company.ng;

import com.alibaba.fastjson.JSON;
import com.kdniao.common.test.BaseTest;
import com.kdniao.logisticsgw.common.service.facade.electronicwaybill.WaybillQuantityQueryRouteFacade;
import com.kdniao.logisticsgw.common.service.order.WaybillQuantityQuery;
import com.kdniao.logisticsgw.common.service.result.WaybillQuantityQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class WaybillQuantityQueryFacadeTest extends BaseTest {
	@Autowired
	private WaybillQuantityQueryRouteFacade waybillQuantityQueryRouteFacade;
	
	@Test
	public void test(){
	    WaybillQuantityQuery order=new WaybillQuantityQuery();
        order.setShipperCode("UC");
        order.setCustomerName("80519357");
        order.setStationCode("菏泽东明三部Q1");
        order.setStationName("菏泽东明三部Q1");
        WaybillQuantityQueryResult result=waybillQuantityQueryRouteFacade.query(order);
        System.out.println(JSON.toJSONString(result));
        System.out.println("success");
	}
}
