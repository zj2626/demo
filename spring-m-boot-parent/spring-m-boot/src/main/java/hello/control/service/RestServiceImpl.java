package hello.control.service;

import hello.data.model.Testc;
import hello.service.RestService;
import hello.data.service.TestcDao;
import hello.service.model.TestcModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestServiceImpl implements RestService {
    @Autowired
    private TestcDao testcDao;

    @Override
    public void update(TestcModel model) {
        testcDao.update(Testc.modelToTestC(model));
    }

    @Override
    public void create(TestcModel model) {
        testcDao.insert(Testc.modelToTestC(model));
    }

    // 模拟查询所有的
    @Override
    public List<TestcModel> findAll() {
        List<TestcModel> list = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            Testc testc = testcDao.select(i);
            if (null != testc) {
                list.add(testc.toModel());
            }
        }

        return list;
    }

    @Override
    public TestcModel findOne(Integer id) {
        return testcDao.select(id).toModel();
    }

    @Override
    public void delOne(Integer id) {
        testcDao.delete(id);
    }
}
