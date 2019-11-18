package hello.service.proxy.impl;

import hello.service.proxy.PolicServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl {

    @Autowired
    private OperationServiceImpl operationService;

    public Object doSome() {
        String param = "this is param of method;";
        String logParam = "this is param of log method;";

        return new PolicServiceProxy(operationService, param, logParam);
    }
}
