package service.cloud.client2.start.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface ControllerApi {

    @GetMapping("/fun1")
    public String fun(@RequestParam String name);

    @GetMapping("/fun2")
    public String fun2(@RequestParam Boolean success);

    @GetMapping("/hi")
    String hi(@RequestParam(value = "name", required = false) String name);

    @GetMapping("/half")
        // 没有@RequestParam就变POST了 ????? 为啥
    String doHalfFailed(@RequestParam Boolean success);

    @GetMapping("/zipkin2")
    String zipkinMethod2(String name);

    @GetMapping("/zipkin4")
    String zipkinMethod4(String name);
}
