/**
 * Kdniao.com Inc.
 * Copyright (c) 2014-2017 All Rights Reserved.
 */
package hello.database.dao;

import hello.database.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PersonDao {
    private static final Logger logger = LoggerFactory.getLogger(PersonDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    public int insert(Person msg) {
        return jdbcTemplate.update("insert into person(id, name, score, course) values (?, ?, ?, ?)",
                0, msg.getName(), msg.getScore(), msg.getCourse());
    }

    public int update(Person msg) {
        return jdbcTemplate.update("update person set name = ? where id = ?", msg.getName(), msg.getId());
    }

    public int delete(Person msg) {
        return jdbcTemplate.update("delete from person where id = ?", msg.getId());
    }

    public Person select(Integer msg) {
        return jdbcTemplate.queryForObject("select id, name, score, course from person where id = ? ", Person.class, msg);
    }
}