package hello.service.impl;

import hello.data.model.Testc;
import hello.data.service.TestcDao;
import hello.service.RestService;
import hello.service.model.TestcModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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

    // 模拟查询所有的
    @Override
    public List<TestcModel> findAll(Integer size) {
        List<TestcModel> list = new ArrayList<>();
        int n = 4000;
        for (int i = n; i < n + size; i++) {
            Testc testc = testcDao.select(i);
            if (null != testc) {
                list.add(testc.toModel());
            }
        }

        if(CollectionUtils.isEmpty(list)){
            throw new RuntimeException("empty data !");
        }

        return list;
    }

    @Override
    public TestcModel findOne(Integer id) {
        return testcDao.select(id).toModel();
    }

    @Override
    public void create(TestcModel model) {
        testcDao.insert(Testc.modelToTestC(model));
    }

    @Override
    public void delOne(Integer id) {
        testcDao.delete(id);
    }

    @Override
    public void updateBatch(List<TestcModel> models) {
        System.out.println(models);
    }

    @Override
    public void createBatch(List<TestcModel> models) {
        List<Testc> testcs = new ArrayList<>();
        models.forEach(model -> testcs.add(Testc.modelToTestC(model)));
        testcDao.insertBatch(testcs);
    }

    @Override
    public void delBatch(String[] ids) {
        System.out.println(ids);
    }
}
