package hello.service.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * ********************
 * install command: clean install -Dprofiles.active=home -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * path: /demo/spring-h-boot-parent
 * ********************
 * run command:     spring-boot:run -Dprofiles.active=home
 * path: /demo/spring-h-boot-parent/spring-h-boot-start
 * ********************
 */
@SpringBootApplication(scanBasePackages = "hello")
@ImportResource({"classpath:dubbo-provider.xml"})
//@Import(ServiceConfiguration.class)
public class SpringHBootStartApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SpringHBootStartApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringHBootStartApplication.class);
    }
}
