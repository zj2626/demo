/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package hello.data.service;

import com.alibaba.fastjson.JSON;
import hello.data.mapper.TestcMapper;
import hello.data.model.Testc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TestcDao {
    private static final Logger logger = LoggerFactory.getLogger(TestcDao.class);

    private TestcMapper testcMapper;

    public void setTestcMapper(TestcMapper testcMapper) {
        this.testcMapper = testcMapper;
    }


    public void select(Integer id){
        Testc testc = testcMapper.selectByPrimaryKey(id);
        logger.info("<TestcDao>" + id + " -> " + (testc != null ? JSON.toJSONString(testc) : ""));
    }
}