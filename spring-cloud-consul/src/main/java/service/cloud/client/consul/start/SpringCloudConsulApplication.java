package service.cloud.client.consul.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * consul : 分布式、高可用、高扩展性的 提供服务发现和配置的工具
 * <p>
 * windows下consul启动: consul agent -dev
 * 访问路径: http://localhost:8500
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudConsulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConsulApplication.class, args);
    }
}
