package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * install command: clean install -Dprofiles.active=home -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * run command:     spring-boot:run -Dprofiles.active=home
 */
@SpringBootApplication
@ImportResource({"classpath:dubbo-*.xml", "classpath:transaction.xml"})
//@PropertySource(value = "classpath:application-*.properties", encoding = "utf-8")
@EnableTransactionManagement
public class AyBootApplication extends SpringBootServletInitializer {

    /*
     * @EnableTransactionManagement 默认开启事务 -> DataSourceTransactionManager 管理@Transactional方法
     *
     * */
    public static void main(String[] args) {
        SpringApplication.run(AyBootApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AyBootApplication.class);
    }
}
