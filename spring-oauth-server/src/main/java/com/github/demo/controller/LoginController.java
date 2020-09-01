package com.github.demo.controller;

import com.github.demo.service.UserService;
import com.github.demo.dto.LoginDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shengzhao Li
 */
@RestController
@RequestMapping("/u1/")
public class LoginController {
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    /**
     * @return View page
     */
    @PostMapping("/login")
    public String login(LoginDto loginDto) {
        LOG.info("u1 login {}", loginDto);
        return "success";
    }
}