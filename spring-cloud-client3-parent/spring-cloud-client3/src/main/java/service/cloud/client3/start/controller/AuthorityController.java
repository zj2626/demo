package service.cloud.client3.start.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthorityController {

    @RequestMapping("/error/{code}")
    public String error(@PathVariable String code) {
        return "/error/" + code;
    }

    @PostMapping("/doLogin")
    public String myLogin(String username, String password) {
        System.out.println("doLogin " + username);
        System.out.println("doLogin " + password);

        // 这里直接跳转页面了 一般前后端分离则返回json字符串
        return "redirect:/home";
    }

    @PreAuthorize("hasAnyAuthority('alter')")
    @GetMapping("/aka")
    public String aka() {
        System.out.println("aka");
        return "success";
    }
}

