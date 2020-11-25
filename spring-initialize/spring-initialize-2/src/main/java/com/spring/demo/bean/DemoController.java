package com.spring.demo.bean;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DemoController {
    private static Map<String, Object> map;

    static {
        map = new HashMap<>();
        map.put("success", true);
        map.put("code", 200);
        map.put("msg", "yes");
    }

    public DemoController() {
        System.out.println("DemoController Constructor");
    }

    @RequestMapping("/product")
    public String product() {
        System.out.println("product");
        System.out.println(JSON.toJSONString(map));
        return "index";
    }

    @RequestMapping("/productJson")
    @ResponseBody
    public String productJson() {
        System.out.println("productJson");
        System.out.println(JSON.toJSONString(map));
        return map.toString();
    }
}
