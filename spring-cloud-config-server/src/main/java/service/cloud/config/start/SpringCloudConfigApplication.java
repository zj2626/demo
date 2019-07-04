package service.cloud.config.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * Config Server
 * <p>
 * spring.cloud.config.server.git.uri：配置git仓库地址
 * spring.cloud.config.server.git.searchPaths：配置仓库路径
 * spring.cloud.config.label：配置仓库的分支
 * spring.cloud.config.server.git.username：访问git仓库的用户名
 * spring.cloud.config.server.git.password：访问git仓库的用户密码
 *
 * 在github上的配置文件的命名规则: {label}/{name}-{profiles}.{type}
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigApplication.class, args);
    }
}
