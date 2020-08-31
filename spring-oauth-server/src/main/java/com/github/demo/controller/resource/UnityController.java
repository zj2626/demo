//package com.github.demo.controller.resource;
//
//import com.github.demo.service.UserService;
//import com.github.demo.service.dto.UserJsonDto;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
///**
// * @author Shengzhao Li
// */
//@Controller
//@RequestMapping("/unity/")
//public class UnityController {
//
//
//    @Autowired
//    private UserService userService;
//
//
//    @RequestMapping("dashboard")
//    public String dashboard() {
//        return "unity/dashboard";
//    }
//
//    @RequestMapping("user_info")
//    @ResponseBody
//    public UserJsonDto userInfo() {
//        return userService.loadCurrentUserJsonDto();
//    }
//
//}