package service.cloud.client.start.controller.api;

import org.springframework.web.bind.annotation.GetMapping;

public interface ControllerApi {
    // http://localhost:8090/hi?name=zj2626
    // http://localhost:8190/actuator/bus-refresh
    @GetMapping("/hi")
    String hi(String name);

    @GetMapping("/half")
    String half(Boolean success);

    @GetMapping("/ribbon/zipkin")
    String zipkinMethod(String name);

    @GetMapping("/ribbon/zipkin3")
    String zipkinMethod3(String name);

    @GetMapping("/ribbon/requestHi")
    String ribbonRequestHi(String name);

    @GetMapping("/ribbon/requestHalfFailed")
    String requestHalfFailed(Boolean success);

    @GetMapping("/ribbon/requestHalfFailed2")
    String requestHalfFailed2(Boolean success);

    @GetMapping("/feign/fun")
    String fun(String name);

    @GetMapping("/feign/requestHi")
    String requestHi(String name);

    @GetMapping("/feign/test/hystrix")
    String testHystrix(Boolean success);
}
