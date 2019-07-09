package service.cloud.client.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * ********************
 * install command: clean install -Dprofiles.active=test -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * path: /demo/spring-cloud-client
 * ********************
 * run command:     spring-boot:run -Dprofiles.active=test
 * path: /demo/spring-cloud-client
 * ********************
 */
@EnableEurekaClient
@SpringBootApplication
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientApplication.class, args);
    }

    @Bean
//    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
