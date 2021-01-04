package com.spring.demo.bean;

import com.alibaba.fastjson.JSON;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

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

    @MessageMapping("/bilibili")
    @SendTo("/topic_zj/acfun")
    public Greeting bilibili(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @RequestMapping("/productJson")
    @ResponseBody
    public String productJson() {
        System.out.println("productJson");
        System.out.println(JSON.toJSONString(map));
        return map.toString();
    }
}
