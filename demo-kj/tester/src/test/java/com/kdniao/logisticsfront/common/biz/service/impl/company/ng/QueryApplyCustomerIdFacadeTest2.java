package com.kdniao.logisticsfront.common.biz.service.impl.company.ng;

import com.kdniao.common.test.BaseTest;
import com.kdniao.logisticsgw.common.service.facade.electronicwaybill.QueryApplyCustomerIdResultFacade;
import com.kdniao.logisticsgw.common.service.model.order.QueryApplyCustomerIdResultOrder;
import com.kdniao.logisticsgw.common.service.result.ApplyCustomerIdQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

public class QueryApplyCustomerIdFacadeTest2 extends BaseTest {
	@Autowired
	private QueryApplyCustomerIdResultFacade queryApplyCustomerIdResultFacade;

	@Test
	public void test(){
	    QueryApplyCustomerIdResultOrder queryApplyCustomerIdResultOrder = new QueryApplyCustomerIdResultOrder();
	    queryApplyCustomerIdResultOrder.setShipperCode("ZTO");
	    queryApplyCustomerIdResultOrder.getApplyCodes().add(222);
	    ApplyCustomerIdQueryResult query = queryApplyCustomerIdResultFacade.query(queryApplyCustomerIdResultOrder);
		System.out.println(query.isSuccess());
		System.out.println(query.getQueryApplyCustomerIdResult().getReason());
	}
	
}
