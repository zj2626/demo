package com.monkeyk.sos.web.controller.resource;

import com.monkeyk.sos.service.UserService;
import com.monkeyk.sos.service.dto.UserJsonDto;
import org.springframework.beans.factory.annotation.Autowired;
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