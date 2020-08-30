package com.github.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan({"com.github.demo.mapper"})
@EnableFeignClients("com.github.demo.feignapi")
@ComponentScan("com.github.demo")
@EnableConfigurationProperties
@PropertySource("classpath:application.properties")
public class OauthResourceApplication {

    public static void main(String[] args) {
        final SpringApplicationBuilder applicationBuilder =
                new SpringApplicationBuilder(OauthResourceApplication.class).web(WebApplicationType.SERVLET);
        applicationBuilder.run(args);
    }

}
