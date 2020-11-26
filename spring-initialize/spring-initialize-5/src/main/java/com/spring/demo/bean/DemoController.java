package com.spring.demo.bean;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class DemoController {
    private static Map<String, Object> map;

    static {
        map = new HashMap<>();
        map.put("success", true);
        map.put("code", 200);
        map.put("msg", "yes");
    }

    public DemoController() {
        System.out.println("DemoController Constructor");
    }


    @RequestMapping("/product")
    public String product() {
        System.out.println("product");
        System.out.println(JSON.toJSONString(map));
        return "index";
    }

    @PostMapping("/upload/img")
    @ResponseBody
    public String upload(@RequestPart("file") MultipartFile upload) {
        System.out.println("upload " + upload.getOriginalFilename());
        System.out.println(JSON.toJSONString(map));

        // FileCopyUtils.copy(upload.getInputStream(), outputStream);
        return map.toString();
    }

    @RequestMapping("/productJson")
    @ResponseBody
    public String productJson() {
        System.out.println("productJson");
        System.out.println(JSON.toJSONString(map));
        return map.toString();
    }
}
