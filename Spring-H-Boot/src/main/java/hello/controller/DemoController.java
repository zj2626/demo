package hello.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${httpUrl}")
    private String url;

    @RequestMapping("/getfun")
    @GetMapping
    public String demoGetRequest(String name){
        System.out.println("demoGetRequest > " + name);
        System.out.println("GET");

        return name + " > " + url;
    }

    @RequestMapping(value = "/postfun")
    @PostMapping
    public String demoPostRequest(String name){
        System.out.println("demoPostRequest > " + name);
        System.out.println("POST");

        return name;
    }
}
