package hello.service.proxy.impl;

import hello.service.proxy.PolicyService;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements PolicyService {
    @Override
    public Object execute(Object param) {
        System.out.println(param);
        return null ;
    }
}
