package hello.service;

import hello.service.model.TestcModel;

import java.util.List;

public interface RestService {
    void update(TestcModel model);

    void create(TestcModel model);

    List<TestcModel> findAll();

    TestcModel findOne(Integer id);

    void delOne(Integer id);
}
