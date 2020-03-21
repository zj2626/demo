package service.cloud.client3.start.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

    @RequestMapping("/error/{code}")
    public String error(@PathVariable String code) {
        return "/error/" + code;
    }
}

