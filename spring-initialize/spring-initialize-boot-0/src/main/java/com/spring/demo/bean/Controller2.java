package com.spring.demo.bean;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/index2") // 代替 @RequestMapping("/index2")
public class Controller2 implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("Controller2 info");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index.html");

        return modelAndView;
    }
}
