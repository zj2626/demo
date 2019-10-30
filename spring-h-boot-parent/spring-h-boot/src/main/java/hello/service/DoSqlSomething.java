package hello.service;

import hello.data.service.TestcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "api.filter.enable", matchIfMissing = true)
public class DoSqlSomething {
    @Autowired
    private TestcDao testcDao;

    public void testMybatisLog(String codes) {
        String[] codeList = codes.split(",");
        for (String code : codeList) {
            testcDao.select(Integer.valueOf(code));
        }
    }
}
