package service.cloud.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * ********************
 * install command: clean install -Dprofiles.active=test -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * path: /demo/spring-cloud-server
 * ********************
 * run command:     spring-boot:run -Dprofiles.active=test
 * path: /demo/spring-cloud-server
 * ********************
 *
 * ********************
 * 高可用的服务注册中心 启动
 * java -jar spring-cloud-service-0.1.jar --spring.profiles.active=peer1
 * java -jar spring-cloud-service-0.1.jar --spring.profiles.active=peer2
 * ********************
 *
 *
 * <p>
 * Eureka Server
 * <p>
 * 在微服务架构中，需要几个基础的服务治理组件，包括服务注册与发现、服务消费、负载均衡、断路器、智能路由、配置管理等
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }

}
