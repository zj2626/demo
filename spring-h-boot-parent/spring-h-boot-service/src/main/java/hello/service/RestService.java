package hello.service;

import hello.service.model.TestcModel;

import java.util.List;

public interface RestService {
    void update(TestcModel model);

    void create(TestcModel model);

    void updateBatch(List<TestcModel> models);

    void createBatch(List<TestcModel> models);

    List<TestcModel> findAll(Integer size);

    TestcModel findOne(Integer id);

    void delOne(Integer id);

    void delBatch(String[] ids);
}
