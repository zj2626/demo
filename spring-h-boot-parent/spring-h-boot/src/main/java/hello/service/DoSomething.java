package hello.service;

import hello.data.service.TestcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoSomething {
    private static final Logger logger = LoggerFactory.getLogger(DoSomething.class);
    private static final Logger logger2 = LoggerFactory.getLogger("sm.test");
    private static final Logger logger3 = LoggerFactory.getLogger("sm.err");
    private static final Logger logger4 = LoggerFactory.getLogger("sm.web");

    @Autowired
    private TestcDao testcDao;

    public DoSomething() {
        System.out.println("构造造 DoSomething");
    }

    public void testMybatisLog(String codes) {
        String[] codeList = codes.split(",");
        for (String code : codeList) {
            testcDao.select(Integer.valueOf(code));
        }
    }
}
