package com.spring.demo.bean;

import com.alibaba.fastjson.JSON;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @MessageMapping("/login")
    @SendTo("/subscribeData/userList")
    public Greeting login(HelloMessage message) {
        System.out.println("登录提醒: " + JSON.toJSONString(message));
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    @MessageMapping("/sendMsg")
    public String sendMsg(HelloMessage message) {
        System.out.println("发送内容: " + JSON.toJSONString(message));
        return "success";
    }

    @RequestMapping("/productJson")
    @ResponseBody
    public String productJson() {
        System.out.println("productJson");
        System.out.println(JSON.toJSONString(map));
        return map.toString();
    }
}
