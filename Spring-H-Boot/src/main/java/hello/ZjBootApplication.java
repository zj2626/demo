package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;

/**
 * install command: clean install -Dprofiles.active=home -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * run command:     spring-boot:run -Dprofiles.active=home
 */
@SpringBootApplication
@ImportResource({"classpath:dubbo-provider.xml"})
public class ZjBootApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ZjBootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ZjBootApplication.class);
    }
}
