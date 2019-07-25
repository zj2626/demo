package hello.service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${httpUrl}")
    private String url;


    @Value("${vEntity.name}")
    private String name;

    @GetMapping("/getfun")
    public String demoGetRequest(String name) {
        System.out.println("demoGetRequest > " + name);
        System.out.println("GET");

        return name + " > " + url;
    }

    @PostMapping(value = "/postfun")
    public String demoPostRequest(String name) {
        System.out.println("demoPostRequest > " + name);
        System.out.println("POST");

        return name;
    }

    @PostMapping(value = "/testvalue")
    public String testvalue() {
        System.out.println(name);
        return null;
    }
}
