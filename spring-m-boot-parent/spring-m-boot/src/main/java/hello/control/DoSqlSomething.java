package hello.control;

import hello.database.dao.PersonDao;
import hello.database.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoSqlSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoSqlSomething.class);

    @Autowired
    private PersonDao dao;

    public Integer doSql(Person msg) {
        try{
            System.out.println(dao.select(msg.getId()));
        }catch(Exception e){
            logger.error("查询异常~ ",  e);
        }

        return dao.insert(msg);
    }
}
