package service.cloud.zuul.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Zuul
 * <p>
 * 导入依赖:
 * *<dependency>
 * *   <groupId>org.springframework.cloud</groupId>
 * *   <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
 * *</dependency>
 * --------------------------
 * <p>
 * 启动类注解:
 * - @EnableZuulProxy
 * - @EnableEurekaClient
 * --------------------------
 * <p>
 * 智能路由:
 * 以/api-c1/开头的请求将转发给spring-cloud-client01服务
 * 以/api-c2/开头的请求将转发给spring-cloud-client02服务
 * --------------------------
 * <p>
 * 服务过滤&安全验证:
 * 继承抽象类ZuulFilter
 * --------------------------
 * <p>
 * application配置:
 * *zuul:
 * * routes:
 * *   api-c1:
 * *     path: /api-c1/**
 * *     serviceId: spring-cloud-client01
 * *   api-c2:
 * *     path: /api-c2/**
 * *     serviceId: spring-cloud-client02
 */
//@EnableZuulProxy
//@EnableEurekaClient
@EnableEurekaClient
@SpringBootApplication
public class SpringCloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulApplication.class, args);
    }
}
