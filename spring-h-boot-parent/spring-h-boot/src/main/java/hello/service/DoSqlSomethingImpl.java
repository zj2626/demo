package hello.service;

import hello.database.model.Testc;
import hello.database.dao.TestcDao;
import hello.service.model.TestcModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@ConditionalOnProperty(value = "api.filter.enable", matchIfMissing = true)
public class DoSqlSomethingImpl implements DoSqlSomething {
    @Autowired
    private TestcDao testcDao;

    @Override
    public void testMybatisLog(String codes) {
        String[] codeList = codes.split(",");
        for (String code : codeList) {
            testcDao.select(Integer.valueOf(code));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBatch(String msg) {
        for (int i = 0; i < 5; i++) {
            TestcModel model = new TestcModel();
            model.setName(msg + "|" + UUID.randomUUID().toString());
            model.setAge(765);
            testcDao.insert(Testc.modelToTestC(model));

            // 0 3 6 9
            if (i == 3 && new Random().nextInt(10) % 3 == 0) {
                throw new RuntimeException("系统异常~~~~~~~");
            }
        }
    }
}
