package service.cloud.feign.start.service;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiHystric implements InterfaceHelloService{
    @Override
    public String doServiceRequestHi(String name) {
        return "fucking sorry doo";
    }

    @Override
    public String doServiceRequestHalfFailed(Boolean success) {
        return "fucking sorry doo 2";
    }
}
