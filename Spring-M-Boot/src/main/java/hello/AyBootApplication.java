package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * install command: clean install -Dmaven.javadoc.skip=true -Dmaven.test.skip=true
 * run command:     spring-boot:run -Dprofiles.active=home
 */
@SpringBootApplication
@ImportResource({"classpath:dubbo-consumer.xml", "classpath:transaction.xml"})
@EnableTransactionManagement
public class AyBootApplication {

    /*
    * @EnableTransactionManagement 默认开启事务 -> DataSourceTransactionManager 管理@Transactional方法
    *
    * */
    public static void main(String[] args) {
        SpringApplication.run(AyBootApplication.class, args);
    }

}
