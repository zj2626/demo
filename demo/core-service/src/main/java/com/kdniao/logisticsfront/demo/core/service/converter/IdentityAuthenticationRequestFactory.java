package com.kdniao.logisticsfront.demo.core.service.converter;

import com.kdniao.logisticsfront.demo.core.model.message.IdentityAddresses;
import com.kdniao.logisticsfront.demo.core.model.message.IdentityAuthenticationRequest;
import com.kdniao.logisticsfront.demo.core.model.message.IdentityContactp;
import com.kdniao.logisticsgw.common.service.order.IdentityAuthenticationOrder;
import org.testng.collections.Lists;

import java.util.List;

public class IdentityAuthenticationRequestFactory {

    public IdentityAuthenticationRequest create(IdentityAuthenticationOrder order) {
        IdentityAuthenticationRequest request = new IdentityAuthenticationRequest();
        request.setName(order.getName());
        String gender = order.getGender();
        if(!("1".equals(gender) || "2".equals(gender))){
            gender = null;
        }
        request.setGender(gender);
        request.setTypeOfId(order.getTypeOfId());
        request.setNumberOfId(order.getNumberOfId());
        request.setCodeOfWeixin(order.getCodeOfWeixin());
        request.setTypeOfUser(order.getTypeOfUser());
        request.setCollectMethod(order.getCollectMethod());
        request.setUserImage(order.getUserImage());
        request.setType(order.getType());
        request.setStoreName(order.getStoreName());
        request.setBellowStationCode(order.getBellowStationName());
        request.setBellowStationName(order.getBellowStationName());
        request.setBellowStationAddr(order.getBellowStationAddr());

        List<IdentityContactp> identityContactps
                = Lists.newArrayList(new IdentityContactp(order.getMobile(), order.getPhone()));
        request.setContact(identityContactps);

        List<IdentityAddresses> identityAddresses
                = Lists.newArrayList(
                        new IdentityAddresses(
                                order.getAddressType(), order.getAddress(),
                                order.getProvince(), order.getCity(), order.getCounty(),
                                order.getProvinceCode(), order.getCityCode(), order.getCountyCode()));
        request.setAddresses(identityAddresses);

        return request;
    }
}
