package service.cloud.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * ********************
 * install command: clean install -D profiles.active=test -D maven.javadoc.skip=true -D maven.test.skip=true
 * path: /demo/spring-cloud-server
 * ********************
 * run command:     spring-boot:run -D profiles.active=test
 * path: /demo/spring-cloud-server
 * ********************
 */
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudServer2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServer2Application.class, args);
    }

}
