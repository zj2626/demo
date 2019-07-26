package hello.service.controller;

import com.alibaba.fastjson.JSON;
import hello.service.controller.entity.ValueEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Value("${httpUrl}")
    private String url;


    @Value("${ventity.name}")
    private String name;

    @Autowired
    private ValueEntity valueEntity;

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

    @GetMapping(value = "/testvalue")
    public String testvalue() {
        System.out.println(name);
        String msg = JSON.toJSONString(valueEntity);
        System.out.println(msg);
        return msg;
    }
}
