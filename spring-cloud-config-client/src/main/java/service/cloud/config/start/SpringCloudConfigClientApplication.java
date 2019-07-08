package service.cloud.config.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

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
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudConfigClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigClientApplication.class, args);
    }
}
