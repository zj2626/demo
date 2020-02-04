/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package hello.database.dao;

import com.alibaba.fastjson.JSON;
import hello.database.mapper.TestcMapper;
import hello.database.model.Testc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestcDao {
    private static final Logger logger = LoggerFactory.getLogger(TestcDao.class);
    private static final Logger logger2 = LoggerFactory.getLogger("sm.test");
    private static final Logger logger3 = LoggerFactory.getLogger("sm.err");
    private static final Logger logger4 = LoggerFactory.getLogger("sm.web");

    @Autowired
    private TestcMapper testcMapper;

    @Cacheable("books") // ?????? 没效果
    public Testc select(Integer id){
        logger.info("[TestcDao Before]" + id + " -> ");
        logger.debug("[TestcDao Debug ]" + id + " -> ");
        Testc testc = testcMapper.selectByPrimaryKey(id);
        logger.info("[TestcDao After ]" + id + " -> " + (testc != null ? JSON.toJSONString(testc) : ""));
        return testc;
    }

    public int insert(Testc record){
        return testcMapper.insert(record);
    }

    public int insertBatch(List<Testc> records){
        return testcMapper.insertBatch(records);
    }

    public int delete(Integer id){
        return testcMapper.deleteByPrimaryKey(id);
    }

    public int update(Testc record){
        return testcMapper.updateByPrimaryKey(record);
    }
}