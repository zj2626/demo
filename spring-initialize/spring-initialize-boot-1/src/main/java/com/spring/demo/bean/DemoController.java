package com.spring.demo.bean;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DemoController {
    private static List<LoginMessage> userList = new ArrayList<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/login")
    @SendTo("/subscribeData/userList")
    public List<LoginMessage> login(LoginMessage message) {
        System.out.println("登录提醒: " + JSON.toJSONString(message));
        userList.add(message);
        return userList;
    }

    @MessageMapping("/sendMsg")
    public String sendMsg(HelloMessage message) {
        System.out.println("发送内容: " + JSON.toJSONString(message));
        simpMessagingTemplate.convertAndSendToUser(message.getSendTo(), "/receiveMsg", message.getMsg());
        return "success";
    }

    @RequestMapping("/productJson")
    @ResponseBody
    public String productJson() {
        System.out.println("productJson");
        return "";
    }
}
