package hello.control;

import hello.database.dao.PersonDao;
import hello.database.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoSqlSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoSqlSomething.class);

    @Autowired
    private PersonDao dao;

    @Transactional(rollbackFor = Exception.class)
    public Integer doSql(Person msg) {
        for (int i = 0; i < 3; i++) {
            Person person = dao.select(msg.getId() + i);
            System.out.println("查询操作" + i + ": " + person);
        }

        for (int i = 0; i < 5; i++) {
            dao.insert(msg);
        }
        return 1;
    }
}
