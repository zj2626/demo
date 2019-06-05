package com.kdniao.logisticsfront.common.biz.service.impl.company.ng;

import com.kdniao.common.test.BaseTest;
import com.kdniao.logisticsgw.common.service.facade.electronicwaybill.ApplyCustomerIdRouteFacade;
import com.kdniao.logisticsgw.common.service.model.order.ApplyCustomerIdOrder;
import com.kdniao.logisticsgw.common.service.result.ApplyCustomerIdResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class ApplyCustomerIdFacadeTest extends BaseTest {
	@Autowired
	private ApplyCustomerIdRouteFacade applyCustomerIdRouteFacade;

	@Test
	public void test(){
		ApplyCustomerIdOrder order=new ApplyCustomerIdOrder();
		order.setPatnerId("1255357");
		order.setShipperCode("UC");
		order.setStationCode("17452");
		order.setStationName("深圳保税区网点");
		order.setMobile("13168010550");
		order.setName("快递快递鸟测试");
		order.setCompany("快递快递鸟测试");
		order.setAddress("福田保税区华宝一号");
		order.setProvinceName("广东省");
		order.setCityName("深圳市");
		order.setExpAreaName("福田区");
		ApplyCustomerIdResult result =applyCustomerIdRouteFacade.apply(order);
		System.out.println(result);
		System.out.println("success");
	}
	
}
