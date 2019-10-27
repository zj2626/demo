package service.cloud.gateway.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Gateway
 * <p>
 * 导入依赖:
 * *<dependency>
 * *   <groupId>org.springframework.cloud</groupId>
 * *   <artifactId>spring-cloud-starter-gateway</artifactId>
 * *</dependency>
 */
@EnableEurekaClient
@SpringBootApplication
public class SpringCloudGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGatewayApplication.class, args);
    }
}
