package service.cloud.client3.start.controller.api;

import org.springframework.web.bind.annotation.PostMapping;

public interface ControllerApi {

    @PostMapping("/fun1")
    public String fun(String name);
}
