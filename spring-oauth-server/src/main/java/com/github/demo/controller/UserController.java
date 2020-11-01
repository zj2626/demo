package com.github.demo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shengzhao Li
 */
@RestController
@RequestMapping("/user/")
public class UserController {

    /**
     * @return View page
     */
    @PostMapping("info")
    public String info() {
        return "info";
    }
}