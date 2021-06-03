package com.demo.common.service.tools;

import org.junit.Test;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.io.File;

public class PathMatherDemo {
    String requestPath = "/com/abc/def/gh/service";

    /**
     * AntPathMatcher默认路径分隔符为 “/”
     * 越精确的模式越会被优先匹配到
     */
    @Test
    public void testPath() {
        System.out.println(File.separator);
        System.out.println(System.getProperty("file.separator"));

        PathMatcher pathMatcher = new AntPathMatcher("/");
        System.out.println(pathMatcher.match("/com/abc/**", requestPath));
        System.out.println(pathMatcher.match("/com/abc/def/**/service", requestPath));
        System.out.println(pathMatcher.match("/com/abc/**/service", requestPath));
        System.out.println(pathMatcher.match("/**/g*/service", requestPath));
        System.out.println(pathMatcher.match("/**/**service", requestPath));
        System.out.println(pathMatcher.match("/**/*service", requestPath));
        System.out.println();

        System.out.println(pathMatcher.match("/**/service", requestPath));
        System.out.println(pathMatcher.match("**/service", requestPath)); // false
        System.out.println();

        System.out.println(pathMatcher.match("/com/abc/*/*/service", requestPath));
        System.out.println(pathMatcher.match("/com/abc/*/service", requestPath)); // false
    }


    @Test
    public void testRequest() {
//        RequestMatcher pathMatcher = new AntPathRequestMatcher("/com/abc/**/service", "POST");
        // 底层用的也是 AntPathMatcher, 封装了些其他功能
    }
}
