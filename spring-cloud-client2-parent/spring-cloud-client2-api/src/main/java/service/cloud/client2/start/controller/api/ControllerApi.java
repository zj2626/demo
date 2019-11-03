package service.cloud.client2.start.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ControllerApi {

    @PostMapping("/fun1")
    public String fun(@RequestParam String name);

    @PostMapping("/fun2")
    public String fun2(@RequestParam Boolean success);

    @PostMapping("/hi")
    String hi(@RequestParam(value = "name", required = false) String name);

    @PostMapping("/half")
        // 没有@RequestParam就变POST了 ????? 为啥
    String doHalfFailed(@RequestParam Boolean success);

    @PostMapping("/async")
    String asyncMethod(String name);

    @PostMapping("/feignRequest")
    String feignRequest(String name);

    @PostMapping("/zipkin2")
    String zipkinMethod2(String name);
}
