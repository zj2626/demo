package com.kdniao.logisticsfront.common.biz.service.impl.spring.mvc;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoController {

    public ModelAndView doMyRequest(HttpServletRequest request, HttpServletResponse response){
        System.out.println("AAA");

        ModelAndView modelAndView = new ModelAndView("main");
        modelAndView.addObject("attributeName", "attributeValue");
        return modelAndView;
    }
}
