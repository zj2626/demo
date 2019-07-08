package service.cloud.ribbon.start.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public interface InterfaceHelloService {
    String doServiceRequestHi(String name);

    @HystrixCommand
    String doServiceRequestHalfFailed(Boolean success);

    @HystrixCommand(fallbackMethod = "onError")
    String doServiceRequestHalfFailed2(Boolean success);
}
