package service.cloud.client.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * ********************
 * install command: clean install -D profiles.active=test -D maven.javadoc.skip=true -D maven.test.skip=true
 * path: /demo/spring-cloud-client
 * ********************
 * run command:     spring-boot:run -D profiles.active=test
 * path: /demo/spring-cloud-client
 * ********************
 * <p>
 * Ribbon Client
 *
 * Edgware版本之后的@EnableDiscoveryClient是可选的(且可以取代@EnableEurekaServer),只需要引入spring-cloud-starter-netflix为前缀的包就可以自动注入
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudClientApplication.class, args);
    }

    @Bean
//    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

/**
 * Config Client
 * <p>
 * spring.cloud.config.label 指明远程仓库的分支
 * spring.cloud.config.profile
 * * dev开发环境配置文件
 * * test测试环境
 * * pro正式环境
 * spring.cloud.config.uri= http://localhost:18092/ 指明配置服务中心的网址。
 */
