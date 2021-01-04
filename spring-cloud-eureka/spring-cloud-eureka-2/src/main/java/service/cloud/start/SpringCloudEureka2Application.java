package service.cloud.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * ********************
 * install command: clean install -D maven.javadoc.skip=true -D maven.test.skip=true
 * path: /demo/spring-cloud-server
 * ********************
 * run command:     spring-boot:run -Dprofiles.active=test
 * path: /demo/spring-cloud-server
 * ********************
 * <p>
 * ********************
 * 高可用的服务注册中心 启动
 * java -jar spring-cloud-eureka-0.1.jar --spring.profiles.active=test
 * ********************
 *
 *
 * <p>
 * Eureka Server
 * <p>
 * 在微服务架构中，需要几个基础的服务治理组件，包括服务注册与发现、服务消费、负载均衡、断路器、智能路由、配置管理等
 */
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudEureka2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEureka2Application.class, args);
    }

}