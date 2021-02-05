package service.cloud.client3.start.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/user").setViewName("view");
        registry.addViewController("/user/add").setViewName("add");
        registry.addViewController("/admin/delete").setViewName("delete");
        registry.addViewController("/db").setViewName("db");
        registry.addViewController("/other").setViewName("other");
        registry.addViewController("/other/a").setViewName("other");
        registry.addViewController("/other/a/b").setViewName("other");
    }
}