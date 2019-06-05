package com.kdniao.logisticsfront.common.biz.service.impl.company.ng;

import com.kdniao.common.test.BaseTest;
import com.kdniao.logisticsgw.common.service.facade.agent.AgentRouteSubmitFacade;
import com.kdniao.logisticsgw.common.service.model.agent.AgentRoute;
import com.kdniao.logisticsgw.common.service.model.delivery.Waybill;
import com.kdniao.logisticsgw.common.service.model.delivery.WaybillKey;
import com.kdniao.logisticsgw.common.service.order.RouteQueryOrder;
import com.kdniao.logisticsgw.common.service.result.AgentRouteSubmitBaseResult;
import com.kdniao.logisticsgw.common.service.result.RouteQueryResult;
import com.kdniao.merchant.common.service.model.setting.CustomerGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.Date;

public class AgentRouteSubmitFacadeTest extends BaseTest {

    @Autowired
    private AgentRouteSubmitFacade agentRouteSubmitFacade;

    @Test
    public void test() {
        AgentRoute agentRoute = new AgentRoute();
        agentRoute.setShipperCode("YD");
        agentRoute.setWaybillCode("1111111");
        agentRoute.setAgentPointCode("11111111111");
        agentRoute.setScanDate(new Date());
        AgentRouteSubmitBaseResult submitRoute = agentRouteSubmitFacade.submitRoute(agentRoute);
        System.out.println(submitRoute);
    }

    @Test
    public void testQueryRoute() {
        RouteQueryOrder order = new RouteQueryOrder();
        Waybill waybill = new Waybill();
        waybill.setWaybillKey(new WaybillKey("482933299731", "ZTO"));
        order.setWaybill(waybill);
        order.setPartnerId("1264635");
        order.setPartnerGroup(CustomerGroup.G998);
        RouteQueryResult result = agentRouteSubmitFacade.route(order);
        System.out.println(result.isSuccess());
        if (!result.isSuccess()) {
            System.out.println(result.getErrorContext().fetchCurrentError());
        }
        System.out.println(result.getRoute());

    }
}
