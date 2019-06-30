package hello.control;

import hello.data.model.Testc;

import java.util.List;

public interface RestService {
    void update(Testc model);

    void create(Testc model);

    List<Testc> findAll();

    Testc findOne(Integer id);

    void delOne(Integer id);
}
