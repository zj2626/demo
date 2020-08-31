package com.github.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/unity/")
public class UnityController {

    @RequestMapping("dashboard")
    public String dashboard() {
        return "unity/dashboard";
    }

}