package service.cloud.config.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Config Client
 * <p>
 * spring.cloud.config.label 指明远程仓库的分支
 * spring.cloud.config.profile
 * * dev开发环境配置文件
 * * test测试环境
 * * pro正式环境
 * spring.cloud.config.uri= http://localhost:8888/ 指明配置服务中心的网址。
 */
@SpringBootApplication
public class SpringCloudConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigApplication.class, args);
    }
}
