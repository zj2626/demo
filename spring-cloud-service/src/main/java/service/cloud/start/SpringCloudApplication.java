package service.cloud.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * ********************
 * install command: clean install -Dprofiles.active=home -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * path: /demo/spring-cloud-server
 * ********************
 * run command:     spring-boot:run -Dprofiles.active=home
 * path: /demo/spring-cloud-server
 * ********************
 */
@EnableEurekaServer
@SpringBootApplication
public class SpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }

}
