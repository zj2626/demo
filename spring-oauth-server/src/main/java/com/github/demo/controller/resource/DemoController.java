package com.github.demo.controller.resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/demo/")
public class DemoController {

    @RequestMapping("dashboard")
    @ResponseBody
    public String dashboard() {
        return "demo !!!!!!!!!!!!!!";
    }
}