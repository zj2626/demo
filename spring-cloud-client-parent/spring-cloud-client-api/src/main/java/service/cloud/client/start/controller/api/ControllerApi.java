package service.cloud.client.start.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface ControllerApi {
    // http://localhost:8090/hi?name=zj2626
    // http://localhost:8190/actuator/bus-refresh
    @PostMapping("/hi")
    String hi(String name);

    @PostMapping("/half")
    String half(Boolean success);

    @PostMapping("/ribbon/zipkin")
    String zipkinMethod(String name);

    @PostMapping("/ribbon/requestHi")
    String ribbonRequestHi(String name);

    @PostMapping("/ribbon/requestHalfFailed")
    String requestHalfFailed(Boolean success);

    @PostMapping("/ribbon/requestHalfFailed2")
    String requestHalfFailed2(Boolean success);

    @PostMapping("/feign/fun")
    String fun(String name);

    @PostMapping("/feign/feignRequest")
    String feignRequest(String name);

    @PostMapping("/feign/requestHi")
    String requestHi(String name);

    @PostMapping("/feign/test/hystrix")
    String testHystrix(Boolean success);
}
