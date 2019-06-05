package com.kdniao.logisticsfront.common.biz.service.impl.company.ng;

import com.kdniao.common.test.BaseTest;
import com.kdniao.logisticsgw.common.service.facade.identityauthentication.IdentityAuthenticationAuthFacade;
import com.kdniao.logisticsgw.common.service.order.IdentityAuthenticationOrder;
import com.kdniao.logisticsgw.common.service.result.IdentityAuthenticationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.UUID;

public class IdentityAuthenticationAuthFacadeTest extends BaseTest{

    @Autowired
    private IdentityAuthenticationAuthFacade identityAuthenticationAuthFacade;

    @Test
    public void testNormal() {
        IdentityAuthenticationOrder order = new IdentityAuthenticationOrder();
        order.setShipperCode("ZTO");
        order.setAddress("祖冲之路2305号");
        order.setCity("深圳");
        order.setCounty("福田区");
        order.setMobile("13590292968");
        order.setPhone("0755-222222");
        order.setName("王文");
        //510500199511242086 230921197908137898  142602198408053152
        order.setNumberOfId("22022119871123148X");//430725198902070959 222405198404221399 22022119871123148X 610422198001252649 362322199101260418 210202198606028016
        //authentication.setPhone("11111111");
        order.setProvince("广东");
        order.setTypeOfId("01");
        IdentityAuthenticationResult result = identityAuthenticationAuthFacade.authen(order);
        System.out.println(result);
        System.out.println(result.getAuthenFeedback());
        System.out.println(result.getErrorContext());
       
    }
    public static void main(String[] ss){
        System.out.println(UUID.randomUUID().toString());
    }
}
