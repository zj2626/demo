package hello.service.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

/**
 * ********************
 * install command: clean install -Dprofiles.active=test -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * path: /demo/spring-m-boot-parent
 * ********************
 * run command:     spring-boot:run -Dprofiles.active=test
 * path: /demo/spring-m-boot-parent/spring-h-boot-start
 * ********************
 */
@SpringBootApplication(scanBasePackages = "hello")
@ImportResource({"classpath:dubbo-consumer.xml"})
@EnableCaching
public class AyBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(AyBootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AyBootApplication.class);
    }
}
