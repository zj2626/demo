package service.zj.zjspringboot.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class DemoController {

    @RequestMapping("/getfun")
    @GetMapping
    public String demoGetRequest(String name){
        System.out.println("demoGetRequest > " + name);
        System.out.println("GET");

        return name;
    }

    @RequestMapping(value = "/postfun")
    @PostMapping
    public String demoPostRequest(String name){
        System.out.println("demoPostRequest > " + name);
        System.out.println("POST");

        return name;
    }
}
