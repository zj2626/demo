package service.cloud.client.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * ********************
 * install command: clean install -Dprofiles.active=home -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * path: /demo/spring-cloud-client
 * ********************
 * run command:     spring-boot:run -Dprofiles.active=home
 * path: /demo/spring-cloud-client
 * ********************
 */
@EnableEurekaClient
@SpringBootApplication
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientApplication.class, args);
    }
}
