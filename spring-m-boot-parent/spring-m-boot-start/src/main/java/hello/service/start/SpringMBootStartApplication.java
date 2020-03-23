package hello.service.start;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * ********************
 * install command: clean install -D profiles.active=test -D maven.javadoc.skip=true -D maven.test.skip=true
 * path: /demo/spring-m-boot-parent
 * ********************
 * run command:     spring-boot:run -D profiles.active=test
 * path: /demo/spring-m-boot-parent/spring-h-boot-start
 * ********************
 */
@SpringBootApplication(scanBasePackages = "hello")
@EnableDubbo(scanBasePackages = "hello.service")
@EnableConfigurationProperties
//@ImportResource({"classpath:dubbo-consumer.xml"})
//@EnableCaching
public class SpringMBootStartApplication extends SpringBootServletInitializer {
    
    public static void main(String[] args) {
        SpringApplication.run(SpringMBootStartApplication.class, args);
    }
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringMBootStartApplication.class);
    }
}
