package service.cloud.client.start.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

public interface InterfaceRibbonService {
    String zipkinMethod(String name);

    String doServiceRequestHi(String name);

    String doServiceRequestHalfFailed(Boolean success);

    String doServiceRequestHalfFailed2(Boolean success);
}
