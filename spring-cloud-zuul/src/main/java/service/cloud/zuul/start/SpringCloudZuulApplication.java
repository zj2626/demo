package service.cloud.zuul.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Zuul
 * <p>
 * 智能路由:
 * 以/api-a/开头的请求将转发给spring-cloud-ribbon01服务
 * 以/api-b/开头的请求将转发给spring-cloud-feign01服务
 *
 * 服务过滤&安全验证:
 * 继承抽象类ZuulFilter
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class SpringCloudZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuulApplication.class, args);
    }
}
