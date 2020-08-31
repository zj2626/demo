package com.github.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shengzhao Li
 */
@RestController
@RequestMapping("/demo/")
public class DemoController {

    @RequestMapping("dashboard")
    @ResponseBody
    public String dashboard() {
        return "demo !!!!!!!!!!!!!!";
    }
}