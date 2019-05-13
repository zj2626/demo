package com.kdniao.logisticsfront.common.biz.service.impl.mybatis;

import com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.mapper.UcAreaDOMapper;
import com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.UcAreaDO;
import com.kdniao.logisticsfront.common.biz.service.impl.mybatis.dal.model.UcAreaDOExample;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        UcAreaDOMapper ucAreaDOMapper = (UcAreaDOMapper) applicationContext.getBean("areaDOMapper");
        select(ucAreaDOMapper);
    }

    public static void select(UcAreaDOMapper ucAreaDOMapper) {
        UcAreaDOExample example = new UcAreaDOExample();
        UcAreaDOExample.Criteria criteria = example.createCriteria();
        criteria.andAreaCodeEqualTo("630102");

        List<UcAreaDO> list = ucAreaDOMapper.selectByExample(example);
        System.out.println(list.size());
        System.out.println(list);
    }
}
