package service.cloud.client2.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * ********************
 * install command: clean install -D profiles.active=test -D maven.javadoc.skip=true -D maven.test.skip=true
 * path: /demo/spring-cloud-client
 * ********************
 * run command:     spring-boot:run -D profiles.active=test
 * path: /demo/spring-cloud-client
 * ********************
 */
@EnableEurekaClient
@SpringBootApplication
public class SpringCloudClient2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClient2Application.class, args);
    }

    @Bean
    @LoadBalanced
        // 开启负载均衡
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
