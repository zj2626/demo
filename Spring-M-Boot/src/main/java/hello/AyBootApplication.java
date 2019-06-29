package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * install command: clean install -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * run command:     spring-boot:run -Dprofiles.active=home
 */
@SpringBootApplication
@ImportResource({"classpath:dubbo-consumer.xml"})
public class AyBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(AyBootApplication.class, args);
    }

}
