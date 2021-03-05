package com.spring.demo.bean;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *controller的三种实现方式:
 * 1. 类注解: @Controller
 * 2. 实现 org.springframework.web.servlet.mvc.Controller 接口
 * 3. 实现 org.springframework.web.HttpRequestHandler     接口
 */
@Controller
@RequestMapping("/index")
public class Controller1 {

    @RequestMapping("/")
    public String info() {
        System.out.println("Controller1 info");
        return "index.html";
    }

    @RequestMapping("/log")
    @ResponseBody
    public String log() {
        System.out.println("Controller1 log");
        return "{\"aaa\":\"bbbb\"}";
    }
}
