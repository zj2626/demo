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
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }

}
