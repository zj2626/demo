package hello.service.start;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ********************
 * install command: clean install -D profiles.active=test -D maven.javadoc.skip=true -D maven.test.skip=true
 * path: /demo/spring-h-boot-parent
 * ********************
 * run command:     spring-boot:run -D profiles.active=test
 * path: /demo/spring-h-boot-parent/spring-h-boot-start
 * ********************
 */
@SpringBootApplication(scanBasePackages = "hello")
@ImportResource({"classpath:transaction.xml"}) // , "classpath:dubbo-provider.xml"
@MapperScan("hello.database.mapper")
@EnableTransactionManagement
@EnableJms //启动消息队列
@EnableDubbo(scanBasePackages = "hello.service")
@EnableConfigurationProperties
//@Import(ServiceConfiguration.class)
public class SpringHBootStartApplication extends SpringBootServletInitializer {
    
    /*
     * @EnableTransactionManagement 默认开启事务 -> DataSourceTransactionManager 管理@Transactional方法
     *
     * */
    public static void main(String[] args) {
        SpringApplication.run(SpringHBootStartApplication.class, args);
    }
    
    
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SpringHBootStartApplication.class);
    }
}
