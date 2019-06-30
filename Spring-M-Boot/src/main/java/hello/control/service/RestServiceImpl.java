package hello.control.service;

import hello.control.RestService;
import hello.data.model.Testc;
import hello.data.service.AreaCodeDao;
import hello.data.service.TestcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestServiceImpl implements RestService {
    @Autowired
    private TestcDao testcDao;

    @Autowired
    private AreaCodeDao areaCodeDao;

    @Override
    public void update(Testc model) {
        testcDao.update(model);
    }

    @Override
    public void create(Testc model) {
        testcDao.insert(model);
    }

    // 模拟查询所有的
    @Override
    public List<Testc> findAll() {
        List<Testc> list = new ArrayList<>();
        for (int i = 4100; i < 4120; i++) {
            Testc testc = testcDao.select(i);
            if (null != testc) {
                list.add(testc);
            }
        }

        return list;
    }

    @Override
    public Testc findOne(Integer id) {
        return testcDao.select(id);
    }

    @Override
    public void delOne(Integer id) {
        testcDao.delete(id);
    }
}
