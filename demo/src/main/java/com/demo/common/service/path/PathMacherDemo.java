package com.demo.common.service.path;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class PathMacherDemo {
    public static String[] ignoreUrl =
            ("/swagger-ui.html,/swagger-resources/**,/swagger/**,/**/springfox-swagger-ui/**," +
                    "/**/v2/api-docs," +
                    "/v2/api-docs" +
                    "/**/*.js,/**/*.css,/**/*.ico,/**/*.png," +
                    "/login,/doLogin,/signup,/api/**")
                    .split(",", 99);

    @Test
    public void test(){
        String path = "/webjars/springfox-swagger-ui/fonts/titillium-web-v6-latin-700.woff2";

        PathMatcher pathMatcher = new AntPathMatcher();
        for (String ig : ignoreUrl) {
            if (pathMatcher.match(ig, path)) {
                System.out.println("Done");
            }
        }
    }
}
