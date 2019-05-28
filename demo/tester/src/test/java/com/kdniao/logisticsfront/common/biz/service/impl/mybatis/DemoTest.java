package com.kdniao.logisticsfront.common.biz.service.impl.mybatis;

import com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.mapper.ShipperEntranceSettingMapper;
import com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.ShipperEntranceSettingExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DemoTest {
    protected String accept = "*/*";
    protected String charsetName = "UTF-8";
    protected String connection = "Keep-Alive";
    protected String userAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)";

    public static ApplicationContext applicationContext;

    static {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }

    @Test
    public void test1() {
        try {
//        UcAreaDOMapper ucAreaDOMapper = (UcAreaDOMapper) applicationContext.getBean("areaDOMapper");
//        System.out.println("list.size " + ucAreaDOMapper.selectByExample(new UcAreaDOExample()).size());

            ShipperEntranceSettingMapper shipperEntranceSettingMapper = (ShipperEntranceSettingMapper) applicationContext.getBean("shipperEntranceSettingMapper");

            String[] requestTypes = new String[]{"1001", "1007", "1008", "1800"};

            String cusomerId = "";
            String stippleGuid = "";

            for (String requestType : requestTypes) {
                ShipperEntranceSettingExample example = new ShipperEntranceSettingExample();
                ShipperEntranceSettingExample.Criteria criteria = example.createCriteria();
                criteria.andCustomerIdEqualTo(cusomerId);

                List<com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.ShipperEntranceSetting> shipperEntranceSettings = shipperEntranceSettingMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(shipperEntranceSettings)) {
                    com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.ShipperEntranceSetting setting = new com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.ShipperEntranceSetting();
                    setting.setId(UUID.randomUUID().toString());
                    setting.setRequestType(requestType);
                    setting.setCustomerId(cusomerId);
                    setting.setCustomerName(null);
                    setting.setUnpickup(true);
                    setting.setSiteUnpickup(true);
                    setting.setUnionUnpickup(true);
                    setting.setUserName("settlementcenter");
                    setting.setStatus(1);
                    setting.setCrateTime(new Date());

                    shipperEntranceSettingMapper.insert(setting);
                } else {
                    com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.ShipperEntranceSetting setting = shipperEntranceSettings.get(0);
                    setting.setRequestType(requestType);
                    setting.setCustomerId(cusomerId);
                    setting.setUnpickup(true);
                    setting.setSiteUnpickup(true);
                    setting.setUnionUnpickup(true);
                    setting.setUserName("settlementcenter");
                    setting.setStatus(1);

                    shipperEntranceSettingMapper.updateByExample(setting, example);
                }
            }

            ExterfaceInvokeHttpSender exterfaceInvokeHttpSender = (ExterfaceInvokeHttpSender) applicationContext.getBean("exterfaceInvokeHttpSender");

            String requestData = "{\"OnlineBooking\":\"1\",\"RouteRegistration\":\"1\",\"Electronic\":\"1\",\"BillUpload\":\"1\",\"Ids\":[{\"Id\":\"" + stippleGuid + "\"}]}";

            exterfaceInvokeHttpSender.setHostname("http://localhost:8087/out/distribute/updateWitoutToken");
            String result = exterfaceInvokeHttpSender.send(requestData);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
